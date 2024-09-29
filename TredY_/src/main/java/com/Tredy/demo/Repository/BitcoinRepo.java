package com.Tredy.demo.Repository;

import com.Tredy.demo.Model.Bitcoin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BitcoinRepo extends JpaRepository<Bitcoin,String> {
}
