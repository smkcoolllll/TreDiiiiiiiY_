package com.Tredy.demo.Service;

import com.Tredy.demo.Domain.VerificationType;
import com.Tredy.demo.Model.User;
import com.Tredy.demo.Model.VerficationCode;
import com.Tredy.demo.Repository.VerificationCodeRepo;
import com.Tredy.demo.Utils.OTP_Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class VerificationCodesrvIMPl implements VerificationCodeserv{

    @Autowired
    private VerificationCodeRepo verificationCodeRepo;


    @Override
    public VerficationCode sendVerificationCode(User user, VerificationType verificationType) {

        VerficationCode newVerificationCode=new VerficationCode();
        newVerificationCode.setOtp(OTP_Utils.generateOTP());
        newVerificationCode.setVerificationType(verificationType);
        newVerificationCode.setUser(user);
        return verificationCodeRepo.save(newVerificationCode);
    }

    @Override
    public VerficationCode geVerificationCodeById(Long id) throws Exception {
        Optional<VerficationCode> verficationCode=verificationCodeRepo.findById(id);
        if (verficationCode.isEmpty()){
            throw new Exception("Verification code not found");
        }
        return verficationCode.get();
    }

    @Override
    public VerficationCode getVerificationCodeByUser(Long userId) {
        return verificationCodeRepo.findByUserUserId(userId);
    }

    @Override
    public void deleteVerificationCode(VerficationCode verficationCode) {

        verificationCodeRepo.delete(verficationCode);
    }
}
