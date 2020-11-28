package com.simpragma.TradesManagement.service;

import java.sql.Timestamp;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.simpragma.TradesManagement.dto.DBOperationsStatus;
import com.simpragma.TradesManagement.dto.TradeApiRequest;
import com.simpragma.TradesManagement.dto.TradeStatus;
import com.simpragma.TradesManagement.model.Trade;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class TradeService {
    @Autowired
    private TradePersistenceService tradePersistenceService;

    public TradeStatus createTrade(TradeApiRequest tradeApiRequest) {
        TradeStatus tradeStatus = new TradeStatus();
        try {
            Trade trade = prepareTrade(tradeApiRequest);
            DBOperationsStatus dbOperationsStatus = tradePersistenceService.createTrade(trade);
            if (dbOperationsStatus.getStatus().equals(DBOperationsStatus.dbOperationsStatus.TRADE_CREATED)) {
                tradeStatus.setStatus(TradeStatus.tradeStatus.TRADE_CREATED);
            } else {
                tradeStatus.setStatus(TradeStatus.tradeStatus.TRADE_NOT_CREATED);
            }
        } catch (Exception e) {
            tradeStatus.setStatus(TradeStatus.tradeStatus.TRADE_NOT_CREATED);
        }
        return tradeStatus;
    }

    private Trade prepareTrade(TradeApiRequest tradeApiRequest) {
        return new Trade().builder().type(tradeApiRequest.getType()).user(String.valueOf(tradeApiRequest.getUser()))
                          .symbol(tradeApiRequest.getSymbol()).shares(tradeApiRequest.getShares()).price(tradeApiRequest.getPrice())
                          .created_at(new Timestamp(new Date().getTime())).updated_at(new Timestamp(new Date().getTime())).build();
    }
}
