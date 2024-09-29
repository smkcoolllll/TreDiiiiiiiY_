package com.Tredy.demo.Service;

import com.Tredy.demo.Model.TwoFactorOTP;
import com.Tredy.demo.Model.User;
import com.Tredy.demo.Repository.TwoFactorOTPRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class TwoFactorOTPsvImpl implements TwoFactorOTPsrv{

    @Autowired
    private TwoFactorOTPRepo twoFactorOTPRepo;
    @Override
    public TwoFactorOTP createTwoFactorOTP(User user, String otp, String jwt) {

        UUID uuid=UUID.randomUUID();
        String id=uuid.toString();
        TwoFactorOTP twoFactorOTP=new TwoFactorOTP();
        twoFactorOTP.setOtp(otp);
        twoFactorOTP.setJwt(jwt);
        twoFactorOTP.setO_ID(id);
        twoFactorOTP.setUser(user);
        return twoFactorOTPRepo.save(twoFactorOTP);
    }

    @Override
    public TwoFactorOTP findByUser(Long userId) {
        return twoFactorOTPRepo.findByUserUserId(userId);
    }

    @Override
    public TwoFactorOTP findById(String id) {
        Optional<TwoFactorOTP> opty=twoFactorOTPRepo.findById(id);
        return opty.orElse(null);
    }

    @Override
    public boolean verifyTwoFactorOTP(TwoFactorOTP twoFactorOTP, String otp) {
        return twoFactorOTP.getOtp().equals(otp);
    }

    @Override
    public void deleteTwoFactorOTP(TwoFactorOTP twoFactorOTP) {
        twoFactorOTPRepo.delete(twoFactorOTP);

    }
}
