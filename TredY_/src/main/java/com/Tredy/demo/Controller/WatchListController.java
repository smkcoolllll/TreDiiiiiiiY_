package com.Tredy.demo.Controller;

import com.Tredy.demo.Model.Bitcoin;
import com.Tredy.demo.Model.User;
import com.Tredy.demo.Model.WatchList;
import com.Tredy.demo.Service.BitcoinService;
import com.Tredy.demo.Service.UserService;
import com.Tredy.demo.Service.WatchListServ;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/watchList")
public class WatchListController {

    @Autowired
    private WatchListServ watchListServ;
    @Autowired
    private UserService userService;
    @Autowired
    private BitcoinService bitcoinService;

    @GetMapping("/user")
    public ResponseEntity<WatchList> getUserWatchList(@RequestHeader("Authorization") String jwt) throws Exception {
        User user=userService.findUserByJWT(jwt);
        WatchList watchList=watchListServ.findUserWatchList(user.getUserId());
        return ResponseEntity.ok(watchList);
    }

    @PostMapping("/create")
    public ResponseEntity<WatchList> createWatchList(@RequestHeader("Authorization") String jwt) throws Exception {
        User user=userService.findUserByJWT(jwt);
        WatchList watchList=watchListServ.createWatchList(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(watchList);
    }
    @GetMapping("/get/{watchListId}")
    public ResponseEntity<WatchList> getWatchListById(@PathVariable Long watchListId) throws Exception {
        WatchList watchList=watchListServ.findById(watchListId);
        return ResponseEntity.ok(watchList);
    }
    @PatchMapping("/add/bitCoin/{bitcoinId}")
    public ResponseEntity<Bitcoin> addItemToWatchList(@RequestHeader("Authorization") String jwt,@PathVariable String bitcoinId) throws Exception {
        User user=userService.findUserByJWT(jwt);
        Bitcoin bitcoin=bitcoinService.findById(bitcoinId);
        Bitcoin addedBitCoin=watchListServ.addItemToWatchList(bitcoin,user);
        return ResponseEntity.ok(addedBitCoin);
    }


}
