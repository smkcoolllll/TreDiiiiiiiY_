package com.Tredy.demo.Repository;

import com.Tredy.demo.Model.VerficationCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VerificationCodeRepo extends JpaRepository<VerficationCode,Long> {
    VerficationCode findByUserUserId(Long userId);
}
