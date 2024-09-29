package com.Tredy.demo.Repository;

import com.Tredy.demo.Model.User;
import com.Tredy.demo.Model.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WalletRepo extends JpaRepository<Wallet,Long> {
    Wallet findByUserUserId(Long userId);
}
