package com.Tredy.demo.Service;

import com.Tredy.demo.Model.Asset;
import com.Tredy.demo.Model.Bitcoin;
import com.Tredy.demo.Model.User;

import java.util.List;

public interface AssetService {
    Asset createAsset(User user, Bitcoin bitcoin,double quantity);
    Asset getAssetById(Long assetId) throws Exception;
    Asset getAssetByUserIdAndAssetId(Long userId,Long assetId);
    List<Asset> getUsersAssets(Long userId);
    Asset updateAsset(Long assetId,double quantity) throws Exception;
    Asset findAssetByUserIdAndBitcoinId(Long userId,String bitcoinId);
    void deleteAsset(Long assetId);
}
