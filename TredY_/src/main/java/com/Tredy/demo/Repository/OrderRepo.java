package com.Tredy.demo.Repository;

import com.Tredy.demo.Model.Order;
import com.Tredy.demo.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepo extends JpaRepository<Order,Long> {

    List<Order> findByUserUserId(Long userId);
}
