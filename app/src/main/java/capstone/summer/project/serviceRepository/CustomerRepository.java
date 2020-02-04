package capstone.summer.project.serviceRepository;

import android.content.Context;

import capstone.summer.project.model.Customer;
import capstone.summer.project.utils.CallBackData;

public interface CustomerRepository {
    void getCustomer(Context context, CallBackData<Customer> callBackData, String phone, String password);

    void createCustomer(Context context,CallBackData<Boolean> callBackData,Customer customer);
}
