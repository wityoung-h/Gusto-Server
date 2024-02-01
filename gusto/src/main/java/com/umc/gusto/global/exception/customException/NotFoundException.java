package com.umc.gusto.global.exception.customException;

import com.umc.gusto.global.exception.Code;
import com.umc.gusto.global.exception.GeneralException;

public class NotFoundException extends GeneralException {

    public NotFoundException(Code code) {
        super(code);
    }
}
