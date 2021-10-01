package com.anwenchu.caribbean.channel.huobi.repository;

import com.anwenchu.caribbean.channel.huobi.model.MarketDayDao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;
import java.util.List;


public interface MarketDayRepository extends JpaRepository<MarketDayDao, Long> {
//
//    @Query("select m from MarketDao m WHERE SUBSTRING(m.marketTime, 11) in (' 23:59:00', ' 00:00:00') ORDER BY m.marketTime ASC")
//    List<MarketDao> listAllOrderByMarketTime();

    List<MarketDayDao> findAllByRateLessThanEqualOrRateGreaterThanEqualAndSymbolOrderByMarketDayDesc(BigDecimal lessThanRate, BigDecimal greaterThanRate, String symbol);


}
