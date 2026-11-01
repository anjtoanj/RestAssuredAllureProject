package com.pets.core;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public enum ErrorCodeEnum {

    ERROR_502("368", "Unauthorised", "blah blah blah");

    private final String code;
    private final String message;
    private final String fields;

    ErrorCodeEnum(final String code, final String message, final String fields) {
        this.code = code;
        this.message = message;
        this.fields = fields;
    }

    private static final Map<String, ErrorCodeEnum> ENUM_MAP = Arrays.stream(ErrorCodeEnum.values())
            .collect(Collectors.toMap(ErrorCodeEnum::getCode, v -> v));


    public String getCode() {
        return this.code;
    }

    public String getMessage() {
        return this.message;
    }

    public String getFields() {
        return this.fields;
    }

    public static ErrorCodeEnum getErrorEnumByCode(final String errorCode) {
        return ENUM_MAP.get(errorCode);
    }
}
