package capstone.summer.project.Service;

import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import capstone.summer.project.model.Case;
import capstone.summer.project.model.Login;
import capstone.summer.project.model.User;
import capstone.summer.project.utils.ConstantManager;

public class UserService {
    static final String TAG = "UserService";
    static final ObjectMapper om = new ObjectMapper().configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true);
    static final String URL_API = ConstantManager.HOST+"api/" + ConstantManager.API.GET_USERS;
    static final Gson gson = new Gson();

    public static void Login(Context context, String username, String password, final VolleyCallback callback) {
        System.setProperty("http.keepAlive","false");
        final String URL = ConstantManager.HOST + "Login";
        final RequestQueue requestQueue = VolleyManager.getInstance(context).getRequestQueue();
        final JSONObject json = new JSONObject();
        try {

            json.put("Username", username);
            json.put("Password", password);
        } catch (Exception e) {
            Log.e(TAG, "Error in send body: " + e.getMessage());
        }

        StringRequest objectRequest = new StringRequest(
                Request.Method.POST,
                URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            Login login = om.readValue(response, Login.class);
                            callback.onSuccess(login);
                        } catch (Exception ex) {
                            ex.printStackTrace();
                            Log.e(TAG, "onResponse: " + ex.getMessage());
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                callback.onFail(error);
                Log.e(TAG, "onErrorResponse: " + error.toString());
            }
        }) {
            @Override
            public byte[] getBody() throws AuthFailureError {
                return json.toString().getBytes();
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("Content-Type", "application/json");
                return headers;
            }
        };
        requestQueue.add(objectRequest);
    }
    public static void getUserByID(Context context, int  id, final VolleyCallback callback) {

        final String URL = URL_API+"/"+id;
        RequestQueue requestQueue = VolleyManager.getInstance(context).getRequestQueue();
        StringRequest objectRequest = new StringRequest(
                Request.Method.GET,
                URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                           User user= om.readValue(response,User.class);
                            callback.onSuccess(user);
                        } catch (Exception ex) {
                            ex.printStackTrace();
                            Log.e(TAG, "onResponse: " + response);
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "onErrorResponse: " + error.toString());
            }
        });
        requestQueue.add(objectRequest);
    }
    public static void Update(Context context, User user, final VolleyCallback callback) {

        final String URL = URL_API + "/"+user.getID();
        RequestQueue requestQueue = VolleyManager.getInstance(context).getRequestQueue();
        final JSONObject json = new JSONObject();
        try {
            json.put("ID", user.getID());
            json.put("Username", user.getUserName());
            json.put("Password", user.getPassword());
            json.put("Address", user.getAddress());
            json.put("Sex", true);
            json.put("Phone", user.getPhone());
            json.put("DoB", user.getDoB());
            json.put("RoleID", user.getRole());
            json.put("Name", user.getName());
            json.put("Position", user.getPosition());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        StringRequest objectRequest = new StringRequest(
                Request.Method.PUT,
                URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {

                            callback.onSuccess(response);
                        } catch (Exception ex) {
                            ex.printStackTrace();
                            Log.e(TAG, "onResponse: " + ex.getMessage());
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        callback.onFail(error);
                    }
                }) {
            @Override
            public byte[] getBody() throws AuthFailureError {
                return json.toString().getBytes();
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json");
//                headers.put("Authorization", token);
                return headers;
            }
        };
        requestQueue.add(objectRequest);
    }
}
