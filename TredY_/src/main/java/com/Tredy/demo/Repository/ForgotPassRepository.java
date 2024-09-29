package com.Tredy.demo.Repository;

import com.Tredy.demo.Model.ForgotPassToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ForgotPassRepository extends JpaRepository<ForgotPassToken,String> {

    ForgotPassToken findByUserUserId(Long userId);
}
