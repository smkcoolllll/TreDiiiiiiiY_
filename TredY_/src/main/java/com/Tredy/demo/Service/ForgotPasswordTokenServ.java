package com.Tredy.demo.Service;

import com.Tredy.demo.Domain.VerificationType;
import com.Tredy.demo.Model.ForgotPassToken;
import com.Tredy.demo.Model.User;

public interface ForgotPasswordTokenServ {

    ForgotPassToken createToken(User user, String id, String otp, VerificationType verificationType, String sendTo);

    ForgotPassToken findById(String id) throws Exception;

    ForgotPassToken findByUser(Long userId);

    void deleteToken(ForgotPassToken token);

}
