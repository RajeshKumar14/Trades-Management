package com.simpragma.TradesManagement.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import com.simpragma.TradesManagement.dto.DBOperationsStatus;
import com.simpragma.TradesManagement.dto.TradeApiRequest;
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

    public DBOperationsStatus deleteAllTrade() {
        DBOperationsStatus dbOperationsStatus = new DBOperationsStatus();
        try {
            tradeRepository.deleteAll();
            dbOperationsStatus.setStatus(DBOperationsStatus.dbOperationsStatus.ALL_TRADE_DELETED);
        } catch (Exception e) {
            String message = "Error in deleting Trade Data from DB:";
            log.error(message, e);
            dbOperationsStatus.setStatus(DBOperationsStatus.dbOperationsStatus.ALL_TRADE_NOT_DELETED);
        }
        return dbOperationsStatus;
    }

    public DBOperationsStatus getAllTrade() {
        DBOperationsStatus dbOperationsStatus = new DBOperationsStatus();
        try {
            dbOperationsStatus.setData(tradeRepository.findAll(Sort.by(Sort.Direction.ASC, "id")));
            dbOperationsStatus.setStatus(DBOperationsStatus.dbOperationsStatus.GET_ALL_TRADE_SUCCESS);
        } catch (Exception e) {
            String message = "Error in getting all Trade Data from DB:";
            log.error(message, e);
            dbOperationsStatus.setStatus(DBOperationsStatus.dbOperationsStatus.GET_ALL_TRADE_FAIL);
        }
        return dbOperationsStatus;
    }

    public DBOperationsStatus getAllTradeDataByUserId(long userId) {
        DBOperationsStatus dbOperationsStatus = new DBOperationsStatus();
        try {
            dbOperationsStatus.setData(tradeRepository.getAllTradeDataByUserId(userId));
            dbOperationsStatus.setStatus(DBOperationsStatus.dbOperationsStatus.GET_ALL_TRADE_DATA_BY_USER_ID_SUCCESS);
        } catch (Exception e) {
            String message = "Error in getting all Trade Data by user id from DB:";
            log.error(message, e);
            dbOperationsStatus.setStatus(DBOperationsStatus.dbOperationsStatus.GET_ALL_TRADE_DATA_BY_USER_ID_FAIL);
        }
        return dbOperationsStatus;
    }

    public DBOperationsStatus getAllTradeDataByStockSymbolAndTradeType(String symbol, String type, String start, String end) {
        DBOperationsStatus dbOperationsStatus = new DBOperationsStatus();
        try {
            dbOperationsStatus.setData(tradeRepository.getAllTradeDataByStockSymbolAndTradeType(symbol, type, start, end));
            dbOperationsStatus.setStatus(DBOperationsStatus.dbOperationsStatus.GET_ALL_TRADE_DATA_BY_STOCK_SYMBOL_AND_TRADE_TYPE_SUCCESS);
        } catch (Exception e) {
            String message = "Error in getting all Trade Data by stock symbol, trade type and date range from DB:";
            log.error(message, e);
            dbOperationsStatus.setStatus(DBOperationsStatus.dbOperationsStatus.GET_ALL_TRADE_DATA_BY_STOCK_SYMBOL_AND_TRADE_TYPE_FAIL);
        }
        return dbOperationsStatus;
    }

    public DBOperationsStatus getStockHighestAndLowestPriceByStockSymbol(String symbol, String start, String end) {
        DBOperationsStatus dbOperationsStatus = new DBOperationsStatus();
        try {
            dbOperationsStatus.setData(tradeRepository.getStockHighestAndLowestPriceByStockSymbol(symbol, start, end));
            dbOperationsStatus.setStatus(DBOperationsStatus.dbOperationsStatus.GET_STOCK_HIGHEST_AND_LOWEST_PRICE_BY_SYMBOL_AND_DATE_RANGE_SUCCESS);
        } catch (Exception e) {
            String message = "Error in getting all Trade Data by stock symbol and date range from DB:";
            log.error(message, e);
            dbOperationsStatus.setStatus(DBOperationsStatus.dbOperationsStatus.GET_STOCK_HIGHEST_AND_LOWEST_PRICE_BY_SYMBOL_AND_DATE_RANGE_FAIL);
        }
        return dbOperationsStatus;
    }

    public DBOperationsStatus isExistStockSymbol(String symbol) {
        DBOperationsStatus dbOperationsStatus = new DBOperationsStatus();
        try {
            if (tradeRepository.isExistStockSymbol(symbol)) {
                dbOperationsStatus.setStatus(DBOperationsStatus.dbOperationsStatus.STOCK_SYMBOL_AVAILABLE);
            }else {
                dbOperationsStatus.setStatus(DBOperationsStatus.dbOperationsStatus.STOCK_SYMBOL_NOT_AVAILABLE);
            }
        } catch (Exception e) {
            String message = "Error in checking stock symbol in DB:";
            log.error(message, e);
            dbOperationsStatus.setStatus(DBOperationsStatus.dbOperationsStatus.IS_STOCK_SYMBOL_AVAILABLE_FAIL);
        }
        return dbOperationsStatus;
    }

    public DBOperationsStatus isExistTrade(long id) {
        DBOperationsStatus dbOperationsStatus = new DBOperationsStatus();
        try {
            if (tradeRepository.existsById(id)) {
                dbOperationsStatus.setStatus(DBOperationsStatus.dbOperationsStatus.CHECK_TRADE_DATA_EXIST_BY_ID_SUCCESS);
            }else {
                dbOperationsStatus.setStatus(DBOperationsStatus.dbOperationsStatus.CHECK_TRADE_DATA_NOT_EXIST_BY_ID_SUCCESS);
            }
        } catch (Exception e) {
            String message = "Error in checking trade id is exist in DB:";
            log.error(message, e);
            dbOperationsStatus.setStatus(DBOperationsStatus.dbOperationsStatus.CHECK_TRADE_DATA_EXIST_BY_ID_FAIL);
        }
        return dbOperationsStatus;
    }
}
