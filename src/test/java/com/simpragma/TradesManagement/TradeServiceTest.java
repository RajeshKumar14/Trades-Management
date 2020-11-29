package com.simpragma.TradesManagement;

import org.json.simple.JSONObject;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import com.google.gson.Gson;
import com.simpragma.TradesManagement.dto.DBOperationsStatus;
import com.simpragma.TradesManagement.dto.TradeApiRequest;
import com.simpragma.TradesManagement.dto.TradeStatus;
import com.simpragma.TradesManagement.service.TradePersistenceService;
import com.simpragma.TradesManagement.service.TradeService;
import com.simpragma.TradesManagement.utils.FileParser;
import lombok.extern.slf4j.Slf4j;

//@Ignore
@Slf4j
@RunWith (SpringRunner.class)
@SpringBootTest
public class TradeServiceTest {
    @Autowired
    private TradeService tradeService;

    @Autowired
    private TradePersistenceService tradePersistenceService;

    @Test
    public void testCreateTrade() {
        String resourceName = "TradeData.json";
        JSONObject tradeJsonObject = new FileParser().JsonFileToJSONObject(resourceName);
        log.info("Trade Data Json :{}", new Gson().toJson(prepareTradeForSave(tradeJsonObject)));
        TradeStatus tradeStatus = tradeService.createTrade(prepareTradeForSave(tradeJsonObject));
        Assert.assertTrue(tradeStatus.getStatus().equals(TradeStatus.tradeStatus.TRADE_CREATED));
    }

    @Test
    public void testDeleteAllTrade() {
        TradeStatus tradeStatus = tradeService.deleteAllTrade();
        Assert.assertTrue(tradeStatus.getStatus().equals(TradeStatus.tradeStatus.ALL_TRADE_DELETED));
    }

    @Test
    public void testGetAllTrade() {
        TradeStatus tradeStatus = tradeService.getAllTrade();
        log.info("All Trade Data in ASC Order:{}", new Gson().toJson(tradeStatus.getData()));
        Assert.assertTrue(tradeStatus.getStatus().equals(TradeStatus.tradeStatus.GET_ALL_TRADE_SUCCESS));
    }

    @Test
    public void testGetAllTradeDataByUserId() {
        TradeStatus tradeStatus = tradeService.getAllTradeDataByUserId(1);
        log.info("All Trade Data By User Id:{}", new Gson().toJson(tradeStatus.getData()));
        Assert.assertTrue(tradeStatus.getStatus().equals(TradeStatus.tradeStatus.GET_ALL_TRADE_DATA_BY_USER_ID_SUCCESS));
    }

    @Test
    public void testGetAllTradeDataByStockSymbolAndTradeType() {
        TradeStatus tradeStatus = tradeService.getAllTradeDataByStockSymbolAndTradeType("AC", "buy", "2020-11-29", "2020-11-30");
        log.info("All Trade Data By Stock Symbol and Trade Type:{}", new Gson().toJson(tradeStatus));
        Assert.assertTrue(tradeStatus.getStatus().equals(TradeStatus.tradeStatus.GET_ALL_TRADE_DATA_BY_STOCK_SYMBOL_AND_TRADE_TYPE_SUCCESS));
    }

    @Test
    public void testGetStockHighestAndLowestPriceByStockSymbol() {
        TradeStatus tradeStatus = tradeService.getStockHighestAndLowestPriceByStockSymbol("A", "2020-11-29", "2020-11-30");
        log.info("All Trade Data By Stock Symbol and Trade Type:{}", new Gson().toJson(tradeStatus.getData()));
        Assert.assertTrue(
                tradeStatus.getStatus().equals(TradeStatus.tradeStatus.GET_STOCK_HIGHEST_AND_LOWEST_PRICE_BY_SYMBOL_AND_DATE_RANGE_SUCCESS));
    }

    @Test
    public void testGetAllTradeDataByStockSymbolAndTradeTypeIfSymbolNotAvailable() {
        TradeStatus tradeStatus = tradeService.getAllTradeDataByStockSymbolAndTradeType("AC", "buy", "2020-11-29", "2020-11-30");
        log.info("All Trade Data By Stock Symbol and Trade Type:{}", new Gson().toJson(tradeStatus.getData()));
        Assert.assertTrue(tradeStatus.getStatus().equals(TradeStatus.tradeStatus.STOCK_SYMBOL_NOT_AVAILABLE));
    }

    @Test
    public void testGetStockHighestAndLowestPriceByStockSymbolIfSymbolNotAvailable() {
        TradeStatus tradeStatus = tradeService.getStockHighestAndLowestPriceByStockSymbol("A", "2020-11-29", "2020-11-30");
        log.info("All Trade Data By Stock Symbol and Trade Type:{}", new Gson().toJson(tradeStatus.getData()));
        Assert.assertTrue(tradeStatus.getStatus().equals(TradeStatus.tradeStatus.STOCK_SYMBOL_NOT_AVAILABLE));
    }

    @Test
    public void testIsExistStockSymbol() {
        DBOperationsStatus dbOperationsStatus = tradePersistenceService.isExistStockSymbol("AC");
        log.info("Symbol exist or not:{}", new Gson().toJson(dbOperationsStatus));
        Assert.assertTrue(dbOperationsStatus.getStatus().equals(DBOperationsStatus.dbOperationsStatus.STOCK_SYMBOL_NOT_AVAILABLE));
    }

    public TradeApiRequest prepareTradeForSave(JSONObject tradeJsonObject) {
        return new TradeApiRequest().builder().type(tradeJsonObject.get("type").toString()).user((JSONObject) tradeJsonObject.get("user"))
                                    .symbol(tradeJsonObject.get("symbol").toString()).shares(tradeJsonObject.get("shares").hashCode())
                                    .price(Double.parseDouble(tradeJsonObject.get("price").toString())).build();
    }

}
