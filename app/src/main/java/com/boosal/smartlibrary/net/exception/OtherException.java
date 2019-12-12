package com.boosal.smartlibrary.net.exception;

import com.boosal.smartlibrary.net.ErrorEnum;
import com.boosal.smartlibrary.net.exception.base.BaseException;

public class OtherException extends BaseException {

    private ErrorEnum errorEnum=ErrorEnum.OTHER_EXCEPTION;

    public OtherException(Throwable throwable) {
        super(throwable);
    }

    public ErrorEnum getErrorEnum() {
        return errorEnum;
    }

    public void setErrorEnum(ErrorEnum errorEnum) {
        this.errorEnum = errorEnum;
    }
}
