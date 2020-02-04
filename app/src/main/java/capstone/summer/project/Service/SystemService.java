package capstone.summer.project.Service;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;

import capstone.summer.project.model.Sample;
import capstone.summer.project.model.System;
import capstone.summer.project.utils.ConstantManager;

public class SystemService {
    static final String TAG = "SystemService";
    static final ObjectMapper om = new ObjectMapper().configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true);
    static final String URL_API = ConstantManager.HOST+"api/" + ConstantManager.API.GET_SYSTEMS;

    public static void getSystemByID(Context context, int  id, final VolleyCallback callback) {

        final String URL = URL_API+"/"+id;
        RequestQueue requestQueue = VolleyManager.getInstance(context).getRequestQueue();
        StringRequest objectRequest = new StringRequest(
                Request.Method.GET,
                URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            System system= om.readValue(response,System.class);
                            callback.onSuccess(system);
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
    public static void getAllSystem(Context context, final VolleyCallback callback) {

        final String URL = URL_API;
        RequestQueue requestQueue = VolleyManager.getInstance(context).getRequestQueue();
        StringRequest objectRequest = new StringRequest(
                Request.Method.GET,
                URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            List<System> systems= om.readValue(response,new TypeReference<List<System>>(){});
                            callback.onSuccess(systems);
                        } catch (Exception ex) {
                            ex.printStackTrace();
                            Log.e(TAG, "onResponse: " + response);
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "onErrorResponse: " + error.toString());
                callback.onFail(error.toString());
            }
        });
        objectRequest.setRetryPolicy(new DefaultRetryPolicy(
                10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(objectRequest);
    }
}
