package com.theatermgnt.theatermgnt.account.dto.request;

public interface IAccountCreationRequest {
    String getUsername();

    String getPassword();

    String getEmail();

    String getPhoneNumber();
}
