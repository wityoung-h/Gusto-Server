package com.umc.gusto.global.exception.customException;

import com.umc.gusto.global.exception.Code;
import com.umc.gusto.global.exception.GeneralException;

public class NoPermission extends GeneralException {

    public NoPermission(Code code) {
        super(code);
    }
}
