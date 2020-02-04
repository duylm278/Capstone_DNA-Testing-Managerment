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

import capstone.summer.project.model.ElectrophoresisProcess;
import capstone.summer.project.model.PurifyProcess;
import capstone.summer.project.utils.ConstantManager;

public class PurifyProcessService {
    static final String TAG = "PurifyProcessService";
    static final ObjectMapper om = new ObjectMapper().configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true);
    static final String URL_API = ConstantManager.HOST+"api/" + ConstantManager.API.GET_PURIFYPROCESS;
    static final Gson gson = new Gson();

    public static void getPurifyProcessByID(Context context, String  id, final VolleyCallback callback) {

        final String URL = URL_API+"/BySampleId/"+id;
        RequestQueue requestQueue = VolleyManager.getInstance((Context) context).getRequestQueue();
        StringRequest objectRequest = new StringRequest(
                Request.Method.GET,
                URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            PurifyProcess purifyProcess = om.readValue(response, PurifyProcess.class);
                            callback.onSuccess(purifyProcess);
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
