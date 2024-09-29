package com.Tredy.demo.Service;

import com.Tredy.demo.Domain.VerificationType;
import com.Tredy.demo.Model.ForgotPassToken;
import com.Tredy.demo.Model.User;
import com.Tredy.demo.Repository.ForgotPassRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ForgotpassTokenIMPl implements ForgotPasswordTokenServ {

    @Autowired
    private ForgotPassRepository forgotPassRepository;
    @Override
    public ForgotPassToken createToken(User user, String id, String otp, VerificationType verificationType, String sendTo) {
        ForgotPassToken token=new ForgotPassToken();
        token.setUser(user);
        token.setOtp(otp);
        token.setVerificationType(verificationType);
        token.setSendTo(sendTo);
        token.setId(id);
        return forgotPassRepository.save(token);
    }

    @Override
    public ForgotPassToken findById(String id) throws Exception {
        Optional<ForgotPassToken> token=forgotPassRepository.findById(id);
        if (token.isEmpty()){
            throw new Exception("Token you are looking is not valid");
        }
        return token.get();
    }

    @Override
    public ForgotPassToken findByUser(Long userId) {
        return forgotPassRepository.findByUserUserId(userId);
    }

    @Override
    public void deleteToken(ForgotPassToken token) {

        forgotPassRepository.delete(token);

    }
}
