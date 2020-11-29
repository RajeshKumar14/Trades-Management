package com.simpragma.TradesManagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.simpragma.TradesManagement.model.Trade;

@Repository
public interface TradeRepository extends JpaRepository<Trade, Long> {
    @Modifying
    @Query ("update Trade t set t.type =:type,t.user=:user,t.symbol=:symbol,t.shares=:shares,t.price=:price  where t.id=:id")
    public void updateTradeById(@Param ("type") String type, @Param ("user") String user, @Param ("symbol") String symbol,
            @Param ("shares") int shares, @Param ("price") double price, @Param ("id") long id);
}
