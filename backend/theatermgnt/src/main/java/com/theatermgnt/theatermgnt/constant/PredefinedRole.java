package com.theatermgnt.theatermgnt.constant;

import com.fasterxml.jackson.annotation.JsonSubTypes;

public class PredefinedRole {
    public static final String STAFF_ROLE = "STAFF";
    public static final String ADMIN_ROLE = "ADMIN";

    private PredefinedRole(){}
}
