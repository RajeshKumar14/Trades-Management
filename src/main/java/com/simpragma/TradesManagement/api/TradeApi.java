package com.simpragma.TradesManagement.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;
import com.simpragma.TradesManagement.service.TradeService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@CrossOrigin
@RestController
public class TradeApi {
    @Autowired
    private TradeService tradeService;
}
