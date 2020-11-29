package com.simpragma.TradesManagement.dto;

import org.json.simple.JSONObject;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude (JsonInclude.Include.NON_NULL)
public class TradeData {
    private long id;
    private String type;
    private Object user;
    private String symbol;
    private int shares;
    private double price;
}
