package com.Tredy.demo.Service;

import com.Tredy.demo.Model.Bitcoin;
import com.Tredy.demo.Model.User;
import com.Tredy.demo.Model.WatchList;
import com.Tredy.demo.Repository.WatchListRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class WatchListServIMPl implements WatchListServ{

    @Autowired
    private WatchListRepo watchListRepo;
    @Override
    public WatchList findUserWatchList(Long userId) throws Exception {
        WatchList watchList=watchListRepo.findByUserUserId(userId);
        if(watchList==null){
            throw  new Exception("watchlist not found");
        }
        return watchList;
    }

    @Override
    public WatchList createWatchList(User user) {
        WatchList watchList=new WatchList();
        watchList.setUser(user);
        return watchListRepo.save(watchList);
    }

    @Override
    public WatchList findById(Long id) throws Exception {
        Optional<WatchList> watchListOptional=watchListRepo.findById(id);
        if(watchListOptional.isEmpty()){
            throw  new Exception("watchlist is not found");
        }
        return watchListOptional.get();
    }

    @Override
    public Bitcoin addItemToWatchList(Bitcoin bitcoin, User user) throws Exception {
        WatchList watchList=findUserWatchList(user.getUserId());
        if(watchList.getBitcoinList().contains(bitcoin)){
            watchList.getBitcoinList().remove(bitcoin);
        }else {
            watchList.getBitcoinList().add(bitcoin);
        }
        watchListRepo.save(watchList);

        return bitcoin;
    }
}
