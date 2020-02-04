package capstone.summer.project.Service;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;

import java.util.List;

import capstone.summer.project.model.ElectrophoresisLog;
import capstone.summer.project.model.ExtractLog;
import capstone.summer.project.utils.ConstantManager;

public class ElectrophoresisLogService {
    static final String TAG = "ElectrophoresisLogService";
    static final ObjectMapper om = new ObjectMapper().configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true);
    static final String URL_API = ConstantManager.HOST+"api/" + ConstantManager.API.GET_ELECTROPHORESISLOG+"/ElectrophoresisProcess";
    static final Gson gson = new Gson();

    public static void getElectrophoresisLogByID(Context context, int  id, final VolleyCallback callback) {

        final String URL = URL_API+"/"+id;
        RequestQueue requestQueue = VolleyManager.getInstance(context).getRequestQueue();
        StringRequest objectRequest = new StringRequest(
                Request.Method.GET,
                URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            List<ElectrophoresisLog> electrophoresisLogs= om.readValue(response,new TypeReference<List<ElectrophoresisLog>>(){});
                            callback.onSuccess(electrophoresisLogs);
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
}
