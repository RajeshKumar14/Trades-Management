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
import com.simpragma.TradesManagement.dto.TradeApiRequest;
import com.simpragma.TradesManagement.dto.TradeStatus;
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

    @Test
    public void testCreateTrade() {
        String resourceName = "TradeData.json";
        JSONObject tradeJsonObject = new FileParser().JsonFileToJSONObject(resourceName);
        log.info("Trade Data Json :{}", new Gson().toJson(prepareTrade(tradeJsonObject)));
        TradeStatus tradeStatus = tradeService.createTrade(prepareTrade(tradeJsonObject));
        Assert.assertTrue(tradeStatus.getStatus().equals(TradeStatus.tradeStatus.TRADE_CREATED));
    }

    public TradeApiRequest prepareTrade(JSONObject tradeJsonObject) {
        return new TradeApiRequest().builder().type(tradeJsonObject.get("type").toString()).user((JSONObject) tradeJsonObject.get("user"))
                                    .symbol(tradeJsonObject.get("symbol").toString()).shares(tradeJsonObject.get("shares").hashCode())
                                    .price(Double.parseDouble(tradeJsonObject.get("price").toString())).build();
    }
}
