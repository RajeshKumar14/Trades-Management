package com.simpragma.TradesManagement.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.simpragma.TradesManagement.dto.DBOperationsStatus;
import com.simpragma.TradesManagement.model.Trade;
import com.simpragma.TradesManagement.repository.TradeRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class TradePersistenceService {
    @Autowired
    private TradeRepository tradeRepository;

    public DBOperationsStatus createTrade(Trade trade) {
        DBOperationsStatus dbOperationsStatus = new DBOperationsStatus();
        try {
            tradeRepository.save(trade);
            dbOperationsStatus.setStatus(DBOperationsStatus.dbOperationsStatus.TRADE_CREATED);
        } catch (Exception e) {
            String message = "Error in save Trade Data in DB:";
            log.error(message, e);
            dbOperationsStatus.setStatus(DBOperationsStatus.dbOperationsStatus.TRADE_NOT_CREATED);
        }
        return dbOperationsStatus;
    }
}
