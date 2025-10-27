package com.theatermgnt.theatermgnt.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

import lombok.Getter;

@Getter
public enum ErrorCode {
    UNCATEGORIZED_EXCEPTION(9999, "Uncategorized error", HttpStatus.INTERNAL_SERVER_ERROR), // Code: 500
    INVALID_KEY(1001, "Invalid message key", HttpStatus.BAD_REQUEST), // 404
    UNAUTHORIZED(1002, "You do not have permissions", HttpStatus.FORBIDDEN), // 403
    USER_EXISTED(1003, "User existed", HttpStatus.BAD_REQUEST),
    USER_NOT_EXISTED(1004, "User not existed", HttpStatus.NOT_FOUND),
    PHONE_NUMBER_EXISTED(1005, "User not existed", HttpStatus.BAD_REQUEST),
    ;

    ErrorCode(int code, String message, HttpStatusCode statusCode) {
        this.code = code;
        this.message = message;
        this.statusCode = statusCode;
    }

    private int code;
    private String message;
    private HttpStatusCode statusCode;
}
