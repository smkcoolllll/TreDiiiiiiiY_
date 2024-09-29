package com.Tredy.demo.Repository;

import com.Tredy.demo.Model.TwoFactorOTP;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TwoFactorOTPRepo extends JpaRepository<TwoFactorOTP,String> {

    TwoFactorOTP findByUserUserId(Long userId);
}
