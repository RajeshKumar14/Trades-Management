package com.simpragma.TradesManagement.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.google.gson.Gson;
import com.simpragma.TradesManagement.dto.ApiResponse;
import com.simpragma.TradesManagement.dto.TradeApiRequest;
import com.simpragma.TradesManagement.dto.TradeStatus;
import com.simpragma.TradesManagement.service.TradeService;
import com.simpragma.TradesManagement.utils.BindingResultHelper;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@CrossOrigin
@RestController
public class TradeApi {
    private static final String TRADE_URL = "/api/v1/trade";

    @Autowired
    private TradeService tradeService;

    @DeleteMapping (TRADE_URL)
    public ResponseEntity<?> deleteAllTrade() {
        try {
            TradeStatus tradeStatus = tradeService.deleteAllTrade();
            if (tradeStatus.getStatus().equals(TradeStatus.tradeStatus.ALL_TRADE_DELETED)) {
                return new ResponseEntity(HttpStatus.OK);
            } else {
                return new ResponseEntity(HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            return new ResponseEntity(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping (TRADE_URL)
    public ResponseEntity<?> createTrade(@RequestBody TradeApiRequest tradeApiRequest, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return new ResponseEntity(new BindingResultHelper().getErrorMessage(TRADE_URL, bindingResult), HttpStatus.BAD_REQUEST);
        }
        try {
            ApiResponse apiResponse = new ApiResponse();
            TradeStatus tradeStatus = tradeService.createTrade(tradeApiRequest);
            if (tradeStatus.getStatus().equals(TradeStatus.tradeStatus.TRADE_ID_EXIST)) {
                return new ResponseEntity(apiResponse, HttpStatus.BAD_REQUEST);
            } else {
                return new ResponseEntity(apiResponse, HttpStatus.CREATED);
            }
        } catch (Exception e) {
            return new ResponseEntity(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping (TRADE_URL)
    public ResponseEntity<?> getAllTrade() {
        try {
            TradeStatus tradeStatus = tradeService.getAllTrade();
            ApiResponse apiResponse = new ApiResponse();
            if (tradeStatus.getStatus().equals(TradeStatus.tradeStatus.GET_ALL_TRADE_SUCCESS)) {
                apiResponse.setData(tradeStatus.getData());
                return new ResponseEntity(apiResponse, HttpStatus.OK);
            } else {
                return new ResponseEntity(HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            return new ResponseEntity(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping (TRADE_URL + "/users/{userID}")
    public ResponseEntity<?> getAllTradeDataByUserId(@PathVariable ("userID") long userId) {
        try {
            TradeStatus tradeStatus = tradeService.getAllTradeDataByUserId(userId);
            ApiResponse apiResponse = new ApiResponse();
            if (tradeStatus.getStatus().equals(TradeStatus.tradeStatus.GET_ALL_TRADE_DATA_BY_USER_ID_SUCCESS)) {
                if (tradeStatus.getStatus().equals(TradeStatus.tradeStatus.USER_ID_DOES_NOT_EXIST)) {
                    return new ResponseEntity(HttpStatus.NOT_FOUND);
                } else {
                    apiResponse.setData(tradeStatus.getData());
                    return new ResponseEntity(apiResponse, HttpStatus.OK);
                }
            } else {
                return new ResponseEntity(HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            return new ResponseEntity(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping (TRADE_URL + "/stocks/{stockSymbol}/trades")
    public ResponseEntity<?> getAllTradeDataByStockSymbolAndTradeType(@PathVariable ("stockSymbol") String stockSymbol,
            @RequestParam ("type") String type, @RequestParam ("start") String start, @RequestParam ("end") String end) {
        try {
            TradeStatus tradeStatus = tradeService.getAllTradeDataByStockSymbolAndTradeType(stockSymbol, type, start, end);
            ApiResponse apiResponse = new ApiResponse();
            if (tradeStatus.getStatus().equals(TradeStatus.tradeStatus.STOCK_SYMBOL_NOT_AVAILABLE)) {
                return new ResponseEntity(HttpStatus.NOT_FOUND);
            } else if (tradeStatus.getStatus().equals(TradeStatus.tradeStatus.GET_ALL_TRADE_DATA_BY_STOCK_SYMBOL_AND_TRADE_TYPE_SUCCESS)) {
                apiResponse.setData(tradeStatus.getData());
                return new ResponseEntity(apiResponse, HttpStatus.OK);
            } else {
                return new ResponseEntity(apiResponse, HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            return new ResponseEntity(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping (TRADE_URL + "/stocks/{stockSymbol}/price")
    public ResponseEntity<?> getStockHighestAndLowestPriceByStockSymbol(@PathVariable ("stockSymbol") String stockSymbol,
            @RequestParam ("start") String start, @RequestParam ("end") String end) {
        try {
            TradeStatus tradeStatus = tradeService.getStockHighestAndLowestPriceByStockSymbol(stockSymbol, start, end);
            ApiResponse apiResponse = new ApiResponse();
            if (tradeStatus.getStatus().equals(TradeStatus.tradeStatus.STOCK_SYMBOL_NOT_AVAILABLE)) {
                return new ResponseEntity(HttpStatus.NOT_FOUND);
            } else if (tradeStatus.getStatus().equals(TradeStatus.tradeStatus.GET_STOCK_HIGHEST_AND_LOWEST_PRICE_BY_SYMBOL_AND_DATE_RANGE_SUCCESS)) {
                apiResponse.setData(tradeStatus.getData());
                return new ResponseEntity(apiResponse, HttpStatus.OK);
            } else {
                return new ResponseEntity(apiResponse, HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            return new ResponseEntity(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
