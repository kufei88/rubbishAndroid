package com.boosal.smartlibrary.net.serviceApi.User;

import com.boosal.smartlibrary.net.ServiceGenerator;
import com.boosal.smartlibrary.net.service.User.authenticate.AuthenticateService;


/**
 * Created by boosal on 2019/8/24.
 */

public class AuthenticateServiceApi {
    private AuthenticateService authenticateService;

    public AuthenticateServiceApi() {
        this.authenticateService = ServiceGenerator.createServiceFrom(AuthenticateService.class);
    }

}
