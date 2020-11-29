package com.simpragma.TradesManagement.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
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
public class StockHighestAndLowestPrice {
    @JsonProperty ("symbol")
    private String symbol;

    @JsonProperty ("highest")
    private double highest;

    @JsonProperty ("lowest")
    private double lowest;

    @JsonProperty ("message")
    private String message;
}
