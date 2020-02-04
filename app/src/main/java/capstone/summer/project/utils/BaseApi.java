package capstone.summer.project.utils;

import java.sql.Time;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class BaseApi {
    private Retrofit retrofitAdapter;

    public BaseApi(){};

    private Retrofit getRetrofitAdapter() {return retrofitAdapter;}

    public<T> T getService(Class<T> cls, String url){
        if(getRetrofitAdapter() == null){
            OkHttpClient client = new OkHttpClient.Builder()
                    .connectTimeout(80, TimeUnit.SECONDS)
                    .readTimeout(80, TimeUnit.SECONDS).build();
            retrofitAdapter = new Retrofit.Builder().baseUrl(url).addConverterFactory(GsonConverterFactory.create()).client(client).build();
        }

        return getRetrofitAdapter().create(cls);
    }
}
