package com.simpragma.TradesManagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.simpragma.TradesManagement.model.Trade;

@Repository
public interface TradeRepository extends JpaRepository<Trade,Long> {

}
