package com.Tredy.demo.Service;

import com.Tredy.demo.Model.Asset;
import com.Tredy.demo.Model.Bitcoin;
import com.Tredy.demo.Model.User;
import com.Tredy.demo.Repository.AssetRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AssetServiceIMPl implements AssetService {

    @Autowired
    private AssetRepo assetRepo;
    @Override
    public Asset createAsset(User user, Bitcoin bitcoin, double quantity) {
        Asset asset=new Asset();
        asset.setUser(user);
        asset.setBitcoin(bitcoin);
        asset.setQuantity(quantity);
        asset.setBuyPrice(bitcoin.getCurrentPrice());
        return assetRepo.save(asset);
    }

    @Override
    public Asset getAssetById(Long assetId) throws Exception {
        return assetRepo.findById(assetId).orElseThrow(() ->  new Exception("Asset not found"));
    }

    @Override
    public Asset getAssetByUserIdAndAssetId(Long userId, Long assetId) {
        return null;
    }

    @Override
    public List<Asset> getUsersAssets(Long userId) {
        return assetRepo.findByUserUserId(userId);
    }

    @Override
    public Asset updateAsset(Long assetId, double quantity) throws Exception {
        Asset oldAsset=getAssetById(assetId);
        oldAsset.setQuantity(quantity+oldAsset.getQuantity());
        return assetRepo.save(oldAsset);
    }

    @Override
    public Asset findAssetByUserIdAndBitcoinId(Long userId, String bitcoinId) {

        return assetRepo.findByUserUserIdAndBitcoinId(userId,bitcoinId);
    }

    @Override
    public void  deleteAsset(Long assetId) {
        assetRepo.deleteById(assetId);
    }
}
