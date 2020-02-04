package capstone.summer.project.Service;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;

import capstone.summer.project.model.PurifyProcess;
import capstone.summer.project.model.Result;
import capstone.summer.project.utils.ConstantManager;

public class ResultService {
    static final String TAG = "ResultService";
    static final ObjectMapper om = new ObjectMapper().configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true);
    static final String URL_API = ConstantManager.HOST+"api/" + ConstantManager.API.GET_RESULT;
    static final Gson gson = new Gson();

    public static void ResultByID(Context context, String  id, final VolleyCallback callback) {

        final String URL = URL_API+"/BySampleId/"+id;
        RequestQueue requestQueue = VolleyManager.getInstance((Context) context).getRequestQueue();
        StringRequest objectRequest = new StringRequest(
                Request.Method.GET,
                URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            Result result = om.readValue(response, Result.class);
                            callback.onSuccess(result);
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
}
