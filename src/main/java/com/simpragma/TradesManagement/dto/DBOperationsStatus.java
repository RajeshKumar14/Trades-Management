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
        TRADE_NOT_CREATED
    }

    @JsonProperty ("status")
    private dbOperationsStatus status;

    @JsonProperty ("data")
    private Object data;

}
