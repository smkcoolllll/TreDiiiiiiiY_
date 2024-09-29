package com.Tredy.demo.Service;

import com.Tredy.demo.Model.TwoFactorOTP;
import com.Tredy.demo.Model.User;
import org.springframework.stereotype.Service;

@Service
public interface TwoFactorOTPsrv {

    TwoFactorOTP createTwoFactorOTP(User user, String otp, String jwt);

    TwoFactorOTP findByUser(Long userId);

    TwoFactorOTP findById(String id);

    boolean verifyTwoFactorOTP(TwoFactorOTP twoFactorOTP,String otp);

    void deleteTwoFactorOTP(TwoFactorOTP twoFactorOTP);
}
