package com.simpragma.TradesManagement.service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import org.json.simple.JSONValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.simpragma.TradesManagement.dto.DBOperationsStatus;
import com.simpragma.TradesManagement.dto.TradeApiRequest;
import com.simpragma.TradesManagement.dto.TradeData;
import com.simpragma.TradesManagement.dto.TradeStatus;
import com.simpragma.TradesManagement.model.Trade;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class TradeService {
    @Autowired
    private TradePersistenceService tradePersistenceService;

    @Transactional
    public TradeStatus createTrade(TradeApiRequest tradeApiRequest) {
        TradeStatus tradeStatus = new TradeStatus();
        try {
            if (tradeApiRequest.getId() > 0) {
                DBOperationsStatus dbOperationsStatus = tradePersistenceService.updateTrade(tradeApiRequest);
                if (dbOperationsStatus.getStatus().equals(DBOperationsStatus.dbOperationsStatus.TRADE_UPDATED)) {
                    tradeStatus.setStatus(TradeStatus.tradeStatus.TRADE_UPDATED);
                } else {
                    tradeStatus.setStatus(TradeStatus.tradeStatus.TRADE_NOT_UPDATED);
                }
            } else {
                Trade trade = prepareTradeForSave(tradeApiRequest);
                DBOperationsStatus dbOperationsStatus = tradePersistenceService.createTrade(trade);
                if (dbOperationsStatus.getStatus().equals(DBOperationsStatus.dbOperationsStatus.TRADE_CREATED)) {
                    tradeStatus.setStatus(TradeStatus.tradeStatus.TRADE_CREATED);
                } else {
                    tradeStatus.setStatus(TradeStatus.tradeStatus.TRADE_NOT_CREATED);
                }
            }
        } catch (Exception e) {
            tradeStatus.setStatus(TradeStatus.tradeStatus.TRADE_NOT_CREATED);
        }
        return tradeStatus;
    }

    @Transactional
    public TradeStatus deleteAllTrade() {
        TradeStatus tradeStatus = new TradeStatus();
        try {
            DBOperationsStatus dbOperationsStatus = tradePersistenceService.deleteAllTrade();
            if (dbOperationsStatus.getStatus().equals(DBOperationsStatus.dbOperationsStatus.ALL_TRADE_DELETED)) {
                tradeStatus.setStatus(TradeStatus.tradeStatus.ALL_TRADE_DELETED);
            } else {
                tradeStatus.setStatus(TradeStatus.tradeStatus.ALL_TRADE_NOT_DELETED);
            }
        } catch (Exception e) {
            tradeStatus.setStatus(TradeStatus.tradeStatus.ALL_TRADE_NOT_DELETED);
        }
        return tradeStatus;
    }

    public TradeStatus getAllTrade() {
        TradeStatus tradeStatus = new TradeStatus();
        try {
            DBOperationsStatus dbOperationsStatus = tradePersistenceService.getAllTrade();
            if (dbOperationsStatus.getStatus().equals(DBOperationsStatus.dbOperationsStatus.GET_ALL_TRADE_SUCCESS)) {
                List<Trade> tradeList = (List<Trade>) dbOperationsStatus.getData();
                List<TradeData> tradeDataList = tradeList.stream().map(d -> prepareTradeData(d)).collect(Collectors.toList());
                tradeStatus.setData(tradeDataList);
                tradeStatus.setStatus(TradeStatus.tradeStatus.GET_ALL_TRADE_SUCCESS);
            } else {
                tradeStatus.setStatus(TradeStatus.tradeStatus.GET_ALL_TRADE_FAIL);
            }
        } catch (Exception e) {
            tradeStatus.setStatus(TradeStatus.tradeStatus.GET_ALL_TRADE_FAIL);
        }
        return tradeStatus;
    }

    public TradeStatus getAllTradeDataByUserId(long userId) {
        TradeStatus tradeStatus = new TradeStatus();
        try {
            DBOperationsStatus dbOperationsStatus = tradePersistenceService.getAllTradeDataByUserId(userId);
            if (dbOperationsStatus.getStatus().equals(DBOperationsStatus.dbOperationsStatus.GET_ALL_TRADE_DATA_BY_USER_ID_SUCCESS)) {
                List<Trade> tradeList = (List<Trade>) dbOperationsStatus.getData();
                List<TradeData> tradeDataList = tradeList.stream().map(d -> prepareTradeData(d)).collect(Collectors.toList());
                tradeStatus.setData(tradeDataList);
                tradeStatus.setStatus(TradeStatus.tradeStatus.GET_ALL_TRADE_DATA_BY_USER_ID_SUCCESS);
            } else {
                tradeStatus.setStatus(TradeStatus.tradeStatus.GET_ALL_TRADE_DATA_BY_USER_ID_FAIL);
            }
        } catch (Exception e) {
            tradeStatus.setStatus(TradeStatus.tradeStatus.GET_ALL_TRADE_DATA_BY_USER_ID_FAIL);
        }
        return tradeStatus;
    }

    public TradeStatus getAllTradeDataByStockSymbolAndTradeType(String symbol, String type, String start, String end) {
        TradeStatus tradeStatus = new TradeStatus();
        try {
            DBOperationsStatus dbOperationsStatus = tradePersistenceService.getAllTradeDataByStockSymbolAndTradeType(symbol, type, start, end);
            if (dbOperationsStatus.getStatus()
                                  .equals(DBOperationsStatus.dbOperationsStatus.GET_ALL_TRADE_DATA_BY_STOCK_SYMBOL_AND_TRADE_TYPE_SUCCESS)) {
                List<Trade> tradeList = (List<Trade>) dbOperationsStatus.getData();
                List<TradeData> tradeDataList = tradeList.stream().map(d -> prepareTradeData(d)).collect(Collectors.toList());
                tradeStatus.setData(tradeDataList);
                tradeStatus.setStatus(TradeStatus.tradeStatus.GET_ALL_TRADE_DATA_BY_STOCK_SYMBOL_AND_TRADE_TYPE_SUCCESS);
            } else {
                tradeStatus.setStatus(TradeStatus.tradeStatus.GET_ALL_TRADE_DATA_BY_STOCK_SYMBOL_AND_TRADE_TYPE_FAIL);
            }
        } catch (Exception e) {
            tradeStatus.setStatus(TradeStatus.tradeStatus.GET_ALL_TRADE_DATA_BY_STOCK_SYMBOL_AND_TRADE_TYPE_FAIL);
        }
        return tradeStatus;
    }

    private TradeData prepareTradeData(Trade trade) {
        return new TradeData().builder().id(trade.getId()).type(trade.getType()).user(JSONValue.parse(trade.getUser())).symbol(trade.getSymbol())
                              .shares(trade.getShares()).price(trade.getPrice()).build();
    }

    private Trade prepareTradeForSave(TradeApiRequest tradeApiRequest) {
        return new Trade().builder().type(tradeApiRequest.getType()).user(String.valueOf(tradeApiRequest.getUser()))
                          .symbol(tradeApiRequest.getSymbol()).shares(tradeApiRequest.getShares()).price(tradeApiRequest.getPrice())
                          .created_at(new Timestamp(new Date().getTime())).build();
    }
}
