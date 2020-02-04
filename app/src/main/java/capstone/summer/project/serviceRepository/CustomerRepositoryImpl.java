package capstone.summer.project.serviceRepository;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.lang.reflect.Type;

import capstone.summer.project.model.Customer;
import capstone.summer.project.utils.CallBackData;
import capstone.summer.project.utils.ClientApi;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CustomerRepositoryImpl implements CustomerRepository {
    @Override
    public void getCustomer(Context context, final CallBackData<Customer> callBackData, String phone, String password) {
        ClientApi clientApi = new ClientApi();
        Call<ResponseBody> serviceCall = clientApi.getCustomerService().getCustomers(phone,password);
        serviceCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response != null && response.body() != null){
                    if(response.code() == 200){
                        try{
                            String result = response.body().string();
                            Type type = new TypeToken<Customer>(){}.getType();
                            Gson gson = new Gson();
                            Customer cus = gson.fromJson(result,type);
                            callBackData.onSuccess(cus);
                        } catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }

    @Override
    public void createCustomer(Context context,final CallBackData<Boolean> callBackData, Customer customer) {
        ClientApi clientApi = new ClientApi();
        JSONObject customerJsonObject =  new JSONObject();
        try{
            customerJsonObject.put("CPhone",customer.getCPhone());
            customerJsonObject.put("CPassword",customer.getCPassword());
            customerJsonObject.put("CName",customer.getCName());
            customerJsonObject.put("CEmail",customer.getCEmail());
            customerJsonObject.put("RoleID",customer.getRoleID());
        } catch (Exception e){
            e.printStackTrace();
        }
        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),customerJsonObject.toString());
        Call<ResponseBody> serviceCall = clientApi.getCustomerService().createCustomer(body);
        serviceCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response != null && response.body() != null){
                    if(response.code() == 200){
                        try{
                            String result = response.body().string();
                            Type type = new TypeToken<Boolean>(){}.getType();
                            Gson gson = new Gson();
                            boolean customerResult = gson.fromJson(result,type);
                            callBackData.onSuccess(customerResult);
                        } catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                }

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                callBackData.onFail("Số điện thoại đã được đăng ký");
            }
        });
    }
}

