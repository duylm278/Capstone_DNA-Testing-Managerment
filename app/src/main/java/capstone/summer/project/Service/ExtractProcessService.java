package capstone.summer.project.Service;

import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import capstone.summer.project.model.ExtractProcess;
import capstone.summer.project.utils.ConstantManager;

public class ExtractProcessService {
    static final String TAG = "ExtractProcessService";
    static final ObjectMapper om = new ObjectMapper().configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true);
    static final String URL_API = ConstantManager.HOST+"api/" + ConstantManager.API.GET_EXTRACTPROCESS;
    static final Gson gson = new Gson();

    public static void getExtractProcessByID(Context context, String  id, final VolleyCallback callback) {

        final String URL = URL_API+"/BySampleId/"+id;
        RequestQueue requestQueue = VolleyManager.getInstance(context).getRequestQueue();
        StringRequest objectRequest = new StringRequest(
                Request.Method.GET,
                URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            ExtractProcess extractProcess = om.readValue(response, ExtractProcess.class);
                            callback.onSuccess(extractProcess);
                        } catch (Exception ex) {
                            ex.printStackTrace();
                            Log.e(TAG, "onResponse: " + response);
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                callback.onFail(error);
                Log.e(TAG, "onErrorResponse: " + error.toString());
            }
        });
        requestQueue.add(objectRequest);
    }

    public static void UpdateExtract(Context context, ExtractProcess extractProcess,int userID, final VolleyCallback callback) {

        final String URL = URL_API + "/"+extractProcess.getID() +"/" +userID;
        RequestQueue requestQueue = VolleyManager.getInstance(context).getRequestQueue();
        final JSONObject json = new JSONObject();
        try {
            json.put("ID", extractProcess.getID());
            json.put("GDGSignal", extractProcess.getGDGSignal());
            json.put("StartDate", extractProcess.getStartDate());
            json.put("EndDate", extractProcess.getEndDate());
            json.put("Amount", extractProcess.getAmount());
            json.put("Note", extractProcess.getNote());
            json.put("Status", extractProcess.getStatus());
            json.put("StepPosition", extractProcess.getStepPosition());
            json.put("UserID", extractProcess.getUserID());
            json.put("KitID", extractProcess.getKitID());
            json.put("SamplesID", extractProcess.getSamplesID());
            json.put("SystemIDADN", extractProcess.getSystemIDADN());
            json.put("SystemIDExtract", extractProcess.getSystemIDExtract());
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
