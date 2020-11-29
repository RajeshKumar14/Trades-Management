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
    }

    @JsonProperty ("status")
    private dbOperationsStatus status;

    @JsonProperty ("data")
    private Object data;

}
