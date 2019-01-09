package com.application.exception;

import com.application.dto.UserDto;

public class CustomErrorType extends UserDto {
    private String errorMessage;

    public CustomErrorType(final String errorMessage) {
        super();
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
