package com.theatermgnt.theatermgnt.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
@Getter
public enum ErrorCode {
    UNCATEGORIZED_EXCEPTION(9999, "Uncategotized error", HttpStatus.INTERNAL_SERVER_ERROR),
    INVALID_TYPING(1001, "WRONG IN YOUR CODE", HttpStatus.BAD_REQUEST),
    USER_EXISTED(1002, "User existed", HttpStatus.BAD_REQUEST),
    USERNAME_INVALID(1003, "Username must be at least {min} characters", HttpStatus.BAD_REQUEST),
    PASSWORD_INVALID(1004, "Password must be at least {min} characters", HttpStatus.BAD_REQUEST),
    USER_NOT_EXISTED(1005, "User not existed", HttpStatus.NOT_FOUND),
    UNAUTHENTICATED(1006, "User not authenticated", HttpStatus.UNAUTHORIZED),
    UNAUTHORIZE(1007, "You do not have permission", HttpStatus.FORBIDDEN),
    INVALID_DOB(1008, "Your age must be at least {min}", HttpStatus.BAD_REQUEST),
    //----
    CINEMA_EXISTED(2001, "Cinema existed", HttpStatus.BAD_REQUEST),
    CINEMA_NOT_EXISTED(2002, "Cinema not existed", HttpStatus.BAD_REQUEST),
    ROOM_EXISTED(2003, "Room existed", HttpStatus.BAD_REQUEST),
    ROOM_NOT_EXISTED(2004, "Room not existed", HttpStatus.BAD_REQUEST),
    SEATTYPE_EXISTED(2005, "Seat type existed", HttpStatus.BAD_REQUEST),
    SEATTYPE_NOT_EXISTED(2006, "Seat type not existed", HttpStatus.BAD_REQUEST),
    PRICECONFIG_NOT_EXISTED(2007, "Price config not existed", HttpStatus.BAD_REQUEST),
    PRICECONFIG_EXISTED(2008, "Price config existed", HttpStatus.BAD_REQUEST),
    SEAT_NOT_EXISTED(2009, "Seat not existed", HttpStatus.BAD_REQUEST),
    SEAT_EXISTED(20010, "Seat existed", HttpStatus.BAD_REQUEST),
    ;
    private int code;
    private String message;
    private HttpStatusCode statusCode;

    ErrorCode(int code, String message, HttpStatusCode statusCode) {
        this.message = message;
        this.code = code;
        this.statusCode = statusCode;
    }

}

