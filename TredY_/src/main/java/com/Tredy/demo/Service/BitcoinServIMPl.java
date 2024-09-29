package com.Tredy.demo.Service;

import com.Tredy.demo.Model.Bitcoin;
import com.Tredy.demo.Repository.BitcoinRepo;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

@Service
public class BitcoinServIMPl implements BitcoinService{

    @Autowired
    private BitcoinRepo bitcoinRepo;

    @Autowired
    private ObjectMapper objectMapper;
    @Override
    public List<Bitcoin> getBitcoinList(int page) throws Exception {
        String url="https://api.coingecko.com/api/v3/coins/markets?vs_currency=usd&per_page=10&page="+page;
        RestTemplate restTemplate=new RestTemplate();
         try {
             HttpHeaders headers=new HttpHeaders();
             HttpEntity<String> entity=new HttpEntity<>("parameters",headers);
             ResponseEntity<String> response=restTemplate.exchange(url, HttpMethod.GET,entity,String.class);
             List<Bitcoin> bitcoinList=objectMapper.readValue(response.getBody(), new TypeReference<List<Bitcoin>>() {});
             return bitcoinList;
         }catch (HttpClientErrorException | HttpServerErrorException e){
             throw new Exception(e.getMessage());
         }
    }

    @Override
    public String getMarketChart(String bitcoinId, int days) throws Exception {
        String url="https://api.coingecko.com/api/v3/coins/"+bitcoinId+"/market_chart?vs_currency=usd&days="+days;
        RestTemplate restTemplate=new RestTemplate();
        try {
            HttpHeaders headers=new HttpHeaders();
            HttpEntity<String> entity=new HttpEntity<>("parameters",headers);
            ResponseEntity<String> response=restTemplate.exchange(url, HttpMethod.GET,entity,String.class);
            return response.getBody();
        }catch (HttpClientErrorException | HttpServerErrorException e){
            throw new Exception(e.getMessage());
        }
    }

    @Override
    public String getBitcoinDetails(String bitcoinId) throws Exception {
        String url="https://api.coingecko.com/api/v3/coins/"+bitcoinId;
        RestTemplate restTemplate=new RestTemplate();
        try {
            HttpHeaders headers=new HttpHeaders();
            HttpEntity<String> entity=new HttpEntity<>("parameters",headers);
            ResponseEntity<String> response=restTemplate.exchange(url, HttpMethod.GET,entity,String.class);
            JsonNode jsonNode=objectMapper.readTree(response.getBody());
            Bitcoin bitcoin=new Bitcoin();
            bitcoin.setId(jsonNode.get("id").asText());
            bitcoin.setName(jsonNode.get("name").asText());
            bitcoin.setSymbol(jsonNode.get("symbol").asText());
            bitcoin.setImage(jsonNode.get("image").get("large").asText());

            JsonNode marketData= jsonNode.get("market_data");
            bitcoin.setCurrentPrice(marketData.get("current_price").get("usd").asDouble());
            bitcoin.setMarketCap(marketData.get("market_cap").get("usd").asLong());
            bitcoin.setMarketCapRank(marketData.get("market_cap_rank").asInt());
            bitcoin.setTotalVolume(marketData.get("total_volume").get("usd").asLong());
            bitcoin.setHigh24h(marketData.get("high_24h").get("usd").asDouble());
            bitcoin.setLow24h(marketData.get("low_24h").get("usd").asDouble());
            bitcoin.setPriceChange24h(marketData.get("price_change_24h").asDouble());
            bitcoin.setPriceChangePercentage24h(marketData.get("price_change_percentage_24h").asDouble());
            bitcoin.setMarketCapChange24h(marketData.get("market_cap_change_24h").asLong());
            bitcoin.setMarketCapChangePercentage24h(marketData.get("market_cap_change_percentage_24h").asDouble());
            bitcoin.setTotalSupply(marketData.get("total_supply").asLong());
            bitcoinRepo.save(bitcoin);
            return response.getBody();
        }catch (HttpClientErrorException | HttpServerErrorException e){
            throw new Exception(e.getMessage());
        }
    }

    @Override
    public Bitcoin findById(String bitcoinId) throws Exception {
        Optional<Bitcoin> optionalBitcoin=bitcoinRepo.findById(bitcoinId);
        if (optionalBitcoin.isEmpty()) throw new Exception("Bitcoin is not found...");
        return optionalBitcoin.get();
    }

    @Override
    public String searchBitcoin(String keyword) throws Exception {
        String url="https://api.coingecko.com/api/v3/search?query="+keyword;
        RestTemplate restTemplate=new RestTemplate();
        try {
            HttpHeaders headers=new HttpHeaders();
            HttpEntity<String> entity=new HttpEntity<>("parameters",headers);
            ResponseEntity<String> response=restTemplate.exchange(url, HttpMethod.GET,entity,String.class);
            return response.getBody();
        }catch (HttpClientErrorException | HttpServerErrorException e){
            throw new Exception(e.getMessage());
        }
    }

    @Override
    public String getTop50BitcoinsByMarketCapRank() throws Exception {
        String url="https://api.coingecko.com/api/v3/coins/markets?vs_currency=usd&per_page=50&page=1";
        RestTemplate restTemplate=new RestTemplate();
        try {
            HttpHeaders headers=new HttpHeaders();
            HttpEntity<String> entity=new HttpEntity<>("parameters",headers);
            ResponseEntity<String> response=restTemplate.exchange(url, HttpMethod.GET,entity,String.class);
            return response.getBody();
        }catch (HttpClientErrorException | HttpServerErrorException e){
            throw new Exception(e.getMessage());
        }
    }

    @Override
    public String getTreadingBitcoins() throws Exception {
        String url="https://api.coingecko.com/api/v3/search/trending";
        RestTemplate restTemplate=new RestTemplate();
        try {
            HttpHeaders headers=new HttpHeaders();
            HttpEntity<String> entity=new HttpEntity<>("parameters",headers);
            ResponseEntity<String> response=restTemplate.exchange(url, HttpMethod.GET,entity,String.class);
            return response.getBody();
        }catch (HttpClientErrorException | HttpServerErrorException e){
            throw new Exception(e.getMessage());
        }
    }
}
