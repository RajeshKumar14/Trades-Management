package com.simpragma.TradesManagement.service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import org.json.simple.JSONValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.simpragma.TradesManagement.dto.DBOperationsStatus;
import com.simpragma.TradesManagement.dto.StockHighestAndLowestPrice;
import com.simpragma.TradesManagement.dto.StockHighestAndLowestPriceIsNotAvailable;
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
            if (tradePersistenceService.isExistTrade(tradeApiRequest.getId()).getStatus()
                                       .equals(DBOperationsStatus.dbOperationsStatus.CHECK_TRADE_DATA_EXIST_BY_ID_SUCCESS)) {
                tradeStatus.setStatus(TradeStatus.tradeStatus.TRADE_ID_EXIST);
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
                if (tradeList.isEmpty()) {
                    tradeStatus.setStatus(TradeStatus.tradeStatus.USER_ID_DOES_NOT_EXIST);
                } else {
                    List<TradeData> tradeDataList = tradeList.stream().map(d -> prepareTradeData(d)).collect(Collectors.toList());
                    tradeStatus.setData(tradeDataList);
                    tradeStatus.setStatus(TradeStatus.tradeStatus.GET_ALL_TRADE_DATA_BY_USER_ID_SUCCESS);
                }
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
            if (tradePersistenceService.isExistStockSymbol(symbol).getStatus().equals(DBOperationsStatus.dbOperationsStatus.STOCK_SYMBOL_AVAILABLE)) {
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
            } else {
                tradeStatus.setStatus(TradeStatus.tradeStatus.STOCK_SYMBOL_NOT_AVAILABLE);
            }
        } catch (Exception e) {
            tradeStatus.setStatus(TradeStatus.tradeStatus.GET_ALL_TRADE_DATA_BY_STOCK_SYMBOL_AND_TRADE_TYPE_FAIL);
        }
        return tradeStatus;
    }

    public TradeStatus getStockHighestAndLowestPriceByStockSymbol(String symbol, String start, String end) {
        TradeStatus tradeStatus = new TradeStatus();
        try {
            if (tradePersistenceService.isExistStockSymbol(symbol).getStatus().equals(DBOperationsStatus.dbOperationsStatus.STOCK_SYMBOL_AVAILABLE)) {
                DBOperationsStatus dbOperationsStatus = tradePersistenceService.getStockHighestAndLowestPriceByStockSymbol(symbol, start, end);
                if (dbOperationsStatus.getStatus()
                                      .equals(DBOperationsStatus.dbOperationsStatus.GET_STOCK_HIGHEST_AND_LOWEST_PRICE_BY_SYMBOL_AND_DATE_RANGE_SUCCESS)) {
                    List<Trade> tradeList = (List<Trade>) dbOperationsStatus.getData();
                    if (tradeList.isEmpty()) {
                        tradeStatus.setData(
                                new StockHighestAndLowestPriceIsNotAvailable().builder().message("There are no trades in the given date range")
                                                                              .build());
                        tradeStatus.setStatus(TradeStatus.tradeStatus.GET_STOCK_HIGHEST_AND_LOWEST_PRICE_BY_SYMBOL_AND_DATE_RANGE_SUCCESS);
                    } else {
                        StockHighestAndLowestPrice prepareStockHighestAndLowestPrice = prepareStockHighestAndLowestPrice(tradeList);
                        tradeStatus.setData(prepareStockHighestAndLowestPrice);
                        tradeStatus.setStatus(TradeStatus.tradeStatus.GET_STOCK_HIGHEST_AND_LOWEST_PRICE_BY_SYMBOL_AND_DATE_RANGE_SUCCESS);
                    }
                } else {
                    tradeStatus.setStatus(TradeStatus.tradeStatus.GET_STOCK_HIGHEST_AND_LOWEST_PRICE_BY_SYMBOL_AND_DATE_RANGE_FAIL);
                }
            } else {
                tradeStatus.setStatus(TradeStatus.tradeStatus.STOCK_SYMBOL_NOT_AVAILABLE);
            }
        } catch (Exception e) {
            tradeStatus.setStatus(TradeStatus.tradeStatus.GET_STOCK_HIGHEST_AND_LOWEST_PRICE_BY_SYMBOL_AND_DATE_RANGE_FAIL);
        }
        return tradeStatus;
    }

    private TradeData prepareTradeData(Trade trade) {
        return new TradeData().builder().id(trade.getId()).type(trade.getType()).user(JSONValue.parse(trade.getUser())).symbol(trade.getSymbol())
                              .shares(trade.getShares()).price(trade.getPrice()).build();
    }

    private Trade prepareTradeForSave(TradeApiRequest tradeApiRequest) {
        return new Trade().builder().id(tradeApiRequest.getId()).type(tradeApiRequest.getType()).user(String.valueOf(tradeApiRequest.getUser()))
                          .symbol(tradeApiRequest.getSymbol()).shares(tradeApiRequest.getShares()).price(tradeApiRequest.getPrice())
                          .created_at(new Timestamp(new Date().getTime())).build();
    }

    private StockHighestAndLowestPrice prepareStockHighestAndLowestPrice(List<Trade> tradeList) {
        Trade highestPriceTrade = tradeList.get(0);
        Trade lowestPriceTrade = tradeList.get(tradeList.size() - 1);
        return new StockHighestAndLowestPrice().builder().symbol(highestPriceTrade.getSymbol()).highest(highestPriceTrade.getPrice())
                                               .lowest(lowestPriceTrade.getPrice()).build();
    }
}
