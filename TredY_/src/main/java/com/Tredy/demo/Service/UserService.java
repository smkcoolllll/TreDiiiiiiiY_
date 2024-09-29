package com.Tredy.demo.Service;

import com.Tredy.demo.Domain.VerificationType;
import com.Tredy.demo.Model.User;

public interface UserService {
    public User findUserByJWT(String jwt) throws Exception;
    public User findUserByEmail(String email) throws Exception;
    public User findUserById(Long userId) throws Exception;
    public User enableTwoFactorAuthentication(VerificationType verificationType,String sendTo,User user);
    User updatePassword(User user,String newPassword);
}
