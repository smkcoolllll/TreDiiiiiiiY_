package com.Tredy.demo.Service;

import com.Tredy.demo.Domain.VerificationType;
import com.Tredy.demo.Model.User;
import com.Tredy.demo.Model.VerficationCode;

public interface VerificationCodeserv {

    VerficationCode sendVerificationCode(User user, VerificationType verificationType);
    VerficationCode geVerificationCodeById(Long id) throws Exception;
    VerficationCode getVerificationCodeByUser(Long userId);
    void deleteVerificationCode(VerficationCode verficationCode);
}
