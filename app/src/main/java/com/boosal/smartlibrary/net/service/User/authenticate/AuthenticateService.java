package com.boosal.smartlibrary.net.service.User.authenticate;

import com.boosal.smartlibrary.net.UrlStore;
import com.boosal.smartlibrary.net.entity.responseBody.User.authenticate.AuthResponse;


import retrofit2.Call;

import retrofit2.http.PUT;

public interface AuthenticateService {

    @PUT(UrlStore.Refresh_Token)
    Call<AuthResponse> call_refreshToken();

}
