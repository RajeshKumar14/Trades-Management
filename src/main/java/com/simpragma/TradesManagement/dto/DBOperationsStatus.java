package com.simpragma.TradesManagement.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude (JsonInclude.Include.NON_NULL)
public class DBOperationsStatus {
    public enum dbOperationsStatus {
        TRADE_CREATED,
        TRADE_NOT_CREATED,
        TRADE_UPDATED,
        TRADE_NOT_UPDATED,
        ALL_TRADE_DELETED,
        ALL_TRADE_NOT_DELETED,
        GET_ALL_TRADE_SUCCESS,
        GET_ALL_TRADE_FAIL,
        GET_ALL_TRADE_DATA_BY_USER_ID_SUCCESS,
        GET_ALL_TRADE_DATA_BY_USER_ID_FAIL,
        GET_ALL_TRADE_DATA_BY_STOCK_SYMBOL_AND_TRADE_TYPE_SUCCESS,
        GET_ALL_TRADE_DATA_BY_STOCK_SYMBOL_AND_TRADE_TYPE_FAIL,
        GET_STOCK_HIGHEST_AND_LOWEST_PRICE_BY_SYMBOL_AND_DATE_RANGE_SUCCESS,
        GET_STOCK_HIGHEST_AND_LOWEST_PRICE_BY_SYMBOL_AND_DATE_RANGE_FAIL,
        STOCK_SYMBOL_NOT_AVAILABLE,
        STOCK_SYMBOL_AVAILABLE,
        IS_STOCK_SYMBOL_AVAILABLE_SUCCESS,
        IS_STOCK_SYMBOL_AVAILABLE_FAIL,
        CHECK_TRADE_DATA_EXIST_BY_ID_SUCCESS,
        CHECK_TRADE_DATA_NOT_EXIST_BY_ID_SUCCESS,
        CHECK_TRADE_DATA_EXIST_BY_ID_FAIL
    }

    @JsonProperty ("status")
    private dbOperationsStatus status;

    @JsonProperty ("data")
    private Object data;

}
