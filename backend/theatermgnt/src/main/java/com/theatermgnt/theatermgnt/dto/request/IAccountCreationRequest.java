package com.theatermgnt.theatermgnt.dto.request;

import lombok.*;

public interface IAccountCreationRequest {
    String getUsername();

    String getPassword();

    String getEmail();

    String getPhoneNumber();
}
