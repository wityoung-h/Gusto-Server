package com.umc.gusto.global.common;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum PublishStatus {
    PUBLIC(true),
    PRIVATE(false);

    private final boolean check;

    public boolean isCheck() {
        return check;
    }

    public static PublishStatus of(boolean check){
        return check ? PUBLIC : PRIVATE;
    }
}
