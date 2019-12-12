package com.boosal.smartlibrary.net.Authenicator;

import android.support.annotation.Nullable;

import com.google.gson.Gson;
import com.boosal.smartlibrary.EventBus.ReLoginEvent;
import com.boosal.smartlibrary.base.baseApp.BaseApplication;
import com.boosal.smartlibrary.net.ErrorEnum;
import com.boosal.smartlibrary.net.ServiceGenerator;
import com.boosal.smartlibrary.net.entity.responseBody.ResponseError;
import com.boosal.smartlibrary.net.entity.responseBody.User.authenticate.AuthResponse;
import com.boosal.smartlibrary.net.service.User.authenticate.AuthenticateService;
import com.boosal.smartlibrary.utils.Preferences;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;

import okhttp3.Authenticator;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.Route;

public class TokenAuthenticator implements Authenticator {

    @Nullable
    @Override
    public Request authenticate(Route route, Response response) throws IOException {
        String errorStr = response.body().string();
        ResponseError error = new Gson().fromJson(errorStr, ResponseError.class);
        ErrorEnum errorEnum=ErrorEnum.UNKNOWN_EXCEPTION;
        try {
            errorEnum=ErrorEnum.valueOf(error.getError_code());
        }catch (Exception enumE){

        }
        switch (errorEnum){
            case TOKEN_EXPIRE:
                AuthResponse authResponse= ServiceGenerator.createServiceFrom(AuthenticateService.class).call_refreshToken().execute().body();
                if(authResponse!=null){
                    BaseApplication.setToken(authResponse.getToken());
                    String token = authResponse.getToken();
                    Preferences.putString(Preferences.PreKey.TOKEN, token);
                    return response.request().newBuilder()
                            .header("Authorization",authResponse.getToken())
                            .build();
                }
                break;
            default:
                EventBus.getDefault().post(new ReLoginEvent());
                break;
        }
        return null;
    }
}
