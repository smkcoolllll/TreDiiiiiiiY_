package com.Tredy.demo.Request;

import lombok.Data;

@Data
public class ResetPassRequest {
    private String otp;

    private String password;
}
