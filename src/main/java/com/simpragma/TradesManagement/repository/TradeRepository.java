package com.simpragma.TradesManagement.repository;

import java.util.List;
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

    @Query (value = "SELECT * FROM trade_master t where t.user->>'$.id'=:userId  ORDER BY id DESC", nativeQuery = true)
    public List<Trade> getAllTradeDataByUserId(@Param ("userId") long userId);

    @Query (value = "SELECT * FROM trade_master t where t.symbol=:symbol AND t.type=:type AND t.created_at >=:start AND t.created_at <:end ORDER BY id ASC", nativeQuery = true)
    public List<Trade> getAllTradeDataByStockSymbolAndTradeType(@Param ("symbol") String symbol, @Param ("type") String type,
            @Param ("start") String start, @Param ("end") String end);

    @Query (value = "SELECT * FROM trade_master t where t.symbol=:symbol AND t.created_at >=:start AND t.created_at <:end ORDER BY price DESC", nativeQuery = true)
    public List<Trade> getStockHighestAndLowestPriceByStockSymbol(@Param ("symbol") String symbol, @Param ("start") String start,
            @Param ("end") String end);

    @Query ("SELECT CASE WHEN COUNT(t)> 0 THEN true ELSE false END FROM Trade t WHERE t.symbol =:symbol")
    public boolean isExistStockSymbol(@Param ("symbol") String symbol);

}
