package com.umc.gusto.global.exception.customException;

import com.umc.gusto.global.exception.Code;
import com.umc.gusto.global.exception.GeneralException;

public class PrivateItemException extends GeneralException {
    public PrivateItemException(Code code){
        super(code);
    }
}
