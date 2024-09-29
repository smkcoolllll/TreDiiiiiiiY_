package com.Tredy.demo.Service;

import com.Tredy.demo.Model.Bitcoin;

import java.util.List;

public interface BitcoinService {
    List<Bitcoin> getBitcoinList(int page) throws Exception;
    String getMarketChart(String bitcoinId,int days) throws Exception;
    String getBitcoinDetails(String bitcoinId) throws Exception;
    Bitcoin findById(String bitcoinId) throws Exception;
    String searchBitcoin(String keyword) throws Exception;
    String getTop50BitcoinsByMarketCapRank() throws Exception;
    String getTreadingBitcoins() throws Exception;
}
