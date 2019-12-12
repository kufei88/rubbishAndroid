package com.boosal.smartlibrary.net.exception;

import com.boosal.smartlibrary.net.ErrorEnum;
import com.boosal.smartlibrary.net.exception.base.BaseException;

/*
error_unauth
 */
public class UnauthException extends BaseException {

    private int httpCode;

    private ErrorEnum errorEnum=ErrorEnum.UNAUTH_EXCEPTION;

    public UnauthException(Throwable throwable, int httpCode) {
        super(throwable);
        this.httpCode=httpCode;
    }

    public int getHttpCode() {
        return httpCode;
    }

    public void setHttpCode(int httpCode) {
        this.httpCode = httpCode;
    }

    public ErrorEnum getErrorEnum() {
        return errorEnum;
    }

    public void setErrorEnum(ErrorEnum errorEnum) {
        this.errorEnum = errorEnum;
    }
}
