package com.Tredy.demo.Service;

import com.Tredy.demo.Model.Bitcoin;
import com.Tredy.demo.Model.User;
import com.Tredy.demo.Model.WatchList;

public interface WatchListServ {

    WatchList findUserWatchList(Long userId) throws Exception;
    WatchList createWatchList(User user);
    WatchList findById(Long id) throws Exception;
    Bitcoin addItemToWatchList(Bitcoin bitcoin,User user) throws Exception;
}
