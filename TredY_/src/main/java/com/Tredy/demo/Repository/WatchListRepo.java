package com.Tredy.demo.Repository;

import com.Tredy.demo.Model.WatchList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WatchListRepo extends JpaRepository<WatchList,Long> {

    WatchList findByUserUserId(Long userId);

}
