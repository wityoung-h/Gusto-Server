package com.umc.gusto.global.exception.customException;

import com.umc.gusto.global.exception.Code;
import com.umc.gusto.global.exception.GeneralException;

public class TempException extends GeneralException {
    public TempException(Code errorCode){
        super(errorCode);
    }
}
