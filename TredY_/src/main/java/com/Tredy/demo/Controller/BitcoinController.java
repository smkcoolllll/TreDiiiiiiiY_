package com.Tredy.demo.Controller;

import com.Tredy.demo.Model.Bitcoin;
import com.Tredy.demo.Service.BitcoinService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/coins")
@CrossOrigin(origins = "http://localhost:5173")
public class BitcoinController {

    @Autowired
    private BitcoinService bitcoinService;

    @Autowired
    private ObjectMapper objectMapper;

    @GetMapping
    ResponseEntity<List<Bitcoin>> getBitcoinList(@RequestParam(required = false, name="page") int page) throws Exception {
     List<Bitcoin> bitcoinList=bitcoinService.getBitcoinList(page);
     return  new ResponseEntity<>(bitcoinList, HttpStatus.ACCEPTED);
    }

    @GetMapping("/{bitcoinId}/chart")
    public ResponseEntity<?> getMarketChart(@PathVariable String bitcoinId, @RequestParam("days") int days) {
        try {
            // Fetch market chart data from service
            String response = bitcoinService.getMarketChart(bitcoinId, days);

            // Parse the response to JsonNode
            JsonNode jsonNode = objectMapper.readTree(response);

            // Return the parsed data with 202 ACCEPTED status
            return new ResponseEntity<>(jsonNode, HttpStatus.ACCEPTED);

        } catch (JsonProcessingException e) {
            // Handle JSON parsing issues
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid data format received");
        } catch (Exception e) {
            // General error handling
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error fetching market chart data");
        }
    }


    @GetMapping("/search")
    ResponseEntity<JsonNode> searchBitcoin(@RequestParam("q") String keyword) throws Exception {
        String bitCoin=bitcoinService.searchBitcoin(keyword);
        JsonNode jsonNode=objectMapper.readTree(bitCoin);
        return ResponseEntity.ok(jsonNode);
    }

    @GetMapping("/top50")
    ResponseEntity<JsonNode> get50CoinByMarketCapRank() throws Exception {
        String bitCoin=bitcoinService.getTop50BitcoinsByMarketCapRank();
        JsonNode jsonNode=objectMapper.readTree(bitCoin);
        return  ResponseEntity.ok(jsonNode);
    }

    @GetMapping("/trending")
    ResponseEntity<JsonNode>  getTreadingBitcoin() throws Exception {
        String bitCoin=bitcoinService.getTreadingBitcoins();
        JsonNode jsonNode=objectMapper.readTree(bitCoin);
        return ResponseEntity.ok(jsonNode);
    }

    @GetMapping("/bitCoinDetails/{bitcoinId}")
    ResponseEntity<JsonNode> getBitcoinDetails(@PathVariable String bitcoinId) throws Exception {
        String bitCoin=bitcoinService.getBitcoinDetails(bitcoinId);
        JsonNode jsonNode=objectMapper.readTree(bitCoin);
        return  ResponseEntity.ok(jsonNode);
    }

}
