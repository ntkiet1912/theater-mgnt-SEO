package com.theatermgnt.theatermgnt.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
public enum ErrorCode {
    UNCATEGORIZED_EXCEPTION(9999, "Uncategorized error", HttpStatus.INTERNAL_SERVER_ERROR), // Code: 500
    INVALID_KEY(1001, "Invalid message key", HttpStatus.BAD_REQUEST), // 404
    UNAUTHORIZED(1002, "You do not have permissions", HttpStatus.FORBIDDEN), // 403

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
