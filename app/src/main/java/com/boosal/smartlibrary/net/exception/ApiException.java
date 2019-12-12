package com.boosal.smartlibrary.net.exception;

import com.boosal.smartlibrary.net.ErrorEnum;
import com.boosal.smartlibrary.net.exception.base.BaseException;

/*
网络异常
 */
public class ApiException extends BaseException {

    private int httpCode;
    private String errorMsg;
    private ErrorEnum errorEnum=ErrorEnum.OTHER_EXCEPTION;;

    public ApiException(int httpCode,String errorMsg) {
        this.httpCode = httpCode;
        this.errorMsg = errorMsg;
    }

    public ApiException(Throwable throwable, int httpCode) {
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

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }
}
