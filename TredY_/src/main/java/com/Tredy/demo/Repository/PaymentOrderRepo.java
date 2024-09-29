package com.Tredy.demo.Repository;

import com.Tredy.demo.Model.PaymentOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentOrderRepo extends JpaRepository<PaymentOrder,Long> {

}
