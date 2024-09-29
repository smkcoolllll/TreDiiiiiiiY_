package com.Tredy.demo.Controller;

import com.Tredy.demo.Model.Asset;
import com.Tredy.demo.Model.User;
import com.Tredy.demo.Service.AssetService;
import com.Tredy.demo.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/asset")
public class AssetController {

    @Autowired
    private AssetService assetService;

    @Autowired
    private UserService userService;

    @GetMapping("/{assetId}")
    public ResponseEntity<Asset> getAssetById(@PathVariable Long assetId) throws Exception{
        Asset asset=assetService.getAssetById(assetId);
        return ResponseEntity.ok().body(asset);
    }

    @GetMapping("/bitCoin/{bitcoinId}/user")
    public ResponseEntity<Asset> getAssetByUserIdAndBitcoinId(@PathVariable String bitcoinId, @RequestHeader("Authorization") String jwt) throws Exception {

        User user=userService.findUserByJWT(jwt);
        Asset asset=assetService.findAssetByUserIdAndBitcoinId(user.getUserId(),bitcoinId);
        return ResponseEntity.ok(asset);
    }

    @GetMapping("/Assets")
    public ResponseEntity<List<Asset>> getAssetForUser(@RequestHeader("Authorization") String jwt) throws Exception {
        User user=userService.findUserByJWT(jwt);
        List<Asset> assets=assetService.getUsersAssets(user.getUserId());
        return ResponseEntity.ok().body(assets);
    }


}
