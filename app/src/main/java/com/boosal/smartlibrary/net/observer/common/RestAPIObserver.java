package com.boosal.smartlibrary.net.observer.common;

import android.content.Context;
import android.util.Log;

import com.boosal.smartlibrary.net.exception.ApiException;
import com.boosal.smartlibrary.net.exception.OtherException;
import com.boosal.smartlibrary.net.exception.UnauthException;
import com.boosal.smartlibrary.net.exception.base.BaseException;
import com.boosal.smartlibrary.net.exception.exceptionPreHandle.ExceptionHandle;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

import static android.content.ContentValues.TAG;


public abstract class RestAPIObserver<T> implements Observer<T> {

    public RestAPIObserver() {
        super();
    }

    public RestAPIObserver(Context context, boolean isShowProgress) {
        super();
    }

    @Override
    public void onSubscribe(Disposable d) {
        onStart();
    }

    @Override
    public void onNext(T t) {
        _onSuccess(t);
    }

    @Override
    public void onComplete() {
        onFinish();
    }

    @Override
    public void onError(Throwable e) {
        onFinish();
        BaseException baseException = ExceptionHandle.handleException(e);
        Log.d(TAG, "onError: "+e);
        if (baseException instanceof ApiException) {
            switch (((ApiException) baseException).getHttpCode()) {
                case 400:
                    _onApiError((ApiException) baseException);
                    break;
                case 401:
                    break;
                case 500:
                    _onApiError((ApiException) baseException);
                    break;
                case 404:
                    _onApiError((ApiException) baseException);
                    break;
            }
        } else if (baseException instanceof UnauthException) {
            _onUnAuth((UnauthException) baseException);
        } else if (baseException instanceof OtherException) {
            _onOtherException((OtherException) baseException);
        }
    }


    public void _onSuccess(T t) {
        onSuccess(t);
    }

    public void _onApiError(ApiException e) {
        onApiError(e);
    }

    public void _onUnAuth(UnauthException e) {
        onUnAuth(e);
    }

    public void _onOtherException(OtherException e) {
        onOtherError(e);
    }


    public abstract void onStart();

    public abstract void onSuccess(T t);

    public abstract void onApiError(ApiException e);

    public abstract void onUnAuth(UnauthException e);

    public abstract void onOtherError(OtherException e) ;

    public abstract void onFinish();


}
