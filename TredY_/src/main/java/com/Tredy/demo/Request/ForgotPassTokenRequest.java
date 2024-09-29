package com.Tredy.demo.Request;

import com.Tredy.demo.Domain.VerificationType;
import lombok.Data;

@Data
public class ForgotPassTokenRequest {

    private String sendTo;

    private String otp;

    private VerificationType verificationType;
}
