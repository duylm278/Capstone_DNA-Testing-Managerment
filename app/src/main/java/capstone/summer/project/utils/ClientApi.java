package capstone.summer.project.utils;

import capstone.summer.project.serviceRepository.CustomerService;

public class ClientApi extends BaseApi {
    public CustomerService getCustomerService() {return this.getService(CustomerService.class,Config.BASE_URL);}
}
