package com.Tredy.demo.Service;

import com.Tredy.demo.Config.JwtProvider;
import com.Tredy.demo.Domain.VerificationType;
import com.Tredy.demo.Model.TwoFactorAuth;
import com.Tredy.demo.Model.User;
import com.Tredy.demo.Repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServIMPl implements UserService{

    @Autowired
    private UserRepo userRepo;

    @Override
    public User findUserByJWT(String jwt) throws Exception {
        String email= JwtProvider.getEmailFromToken(jwt);
        User user=userRepo.findByEmail(email);

        if(user==null){
            throw new Exception("User you are looking is not found...");
        }

        return user;
    }

    @Override
    public User findUserByEmail(String email) throws Exception {
        User user=userRepo.findByEmail(email);
        if(user==null){
            throw new Exception("User you are looking is not found...");
        }
        return user;
    }

    @Override
    public User findUserById(Long userId) throws Exception {
        Optional<User> user=userRepo.findById(userId);
        if(user.isEmpty()){
            throw  new Exception("User not found");
        }
        return user.get();
    }

    @Override
    public User enableTwoFactorAuthentication(VerificationType verificationType, String sendTo, User user) {
        TwoFactorAuth twoFactorAuth=new TwoFactorAuth();
        twoFactorAuth.setEnabled(true);
        twoFactorAuth.setSendTo(verificationType);
        user.setTwoFactorAuth(twoFactorAuth);
        return userRepo.save(user);
    }


    @Override
    public User updatePassword(User user, String newPassword) {
        user.setPassword(newPassword);
        return userRepo.save(user);
    }
}
