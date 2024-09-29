package com.Tredy.demo.Repository;

import com.Tredy.demo.Model.Withdrawal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WithdrawalRepo extends JpaRepository<Withdrawal,Long> {

    List<Withdrawal> findByUserUserId(Long userId);
}
