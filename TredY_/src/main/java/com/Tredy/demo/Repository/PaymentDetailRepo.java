package com.Tredy.demo.Repository;

import com.Tredy.demo.Model.PaymentDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentDetailRepo extends JpaRepository<PaymentDetails,Long> {

    PaymentDetails findByUserUserId(Long userId);

}
