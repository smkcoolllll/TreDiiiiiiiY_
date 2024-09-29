package com.Tredy.demo.Repository;

import com.Tredy.demo.Model.Asset;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AssetRepo extends JpaRepository<Asset,Long> {

    List<Asset> findByUserUserId(Long userId);

    Asset findByUserUserIdAndBitcoinId(Long userId,String bitcoinId);
}
