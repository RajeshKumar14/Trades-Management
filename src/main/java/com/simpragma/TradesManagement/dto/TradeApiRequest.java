package com.simpragma.TradesManagement.dto;

import org.json.simple.JSONObject;
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
public class TradeApiRequest {
    private long id;
    private String type;
    private JSONObject user;
    private String symbol;
    private int shares;
    private double price;
}
