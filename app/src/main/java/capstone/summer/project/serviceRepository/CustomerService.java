package capstone.summer.project.serviceRepository;

import capstone.summer.project.utils.Config;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface CustomerService {
    @GET(Config.API.GET_CUSTOMERS)
    Call<ResponseBody> getCustomers(@Query("phone")String phone, @Query("password")String password);

    @POST(Config.API.CREATE_CUSTOMER)
    @Headers({"Content-Type: application/json;charset=UTF-8"})
    Call<ResponseBody> createCustomer(@Body RequestBody customerObject);
}
