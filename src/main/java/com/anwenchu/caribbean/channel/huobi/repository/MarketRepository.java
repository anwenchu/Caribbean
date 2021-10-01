package com.anwenchu.caribbean.channel.huobi.repository;

import com.anwenchu.caribbean.channel.huobi.model.MarketDao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface MarketRepository extends JpaRepository<MarketDao, Long> {

    @Query("select m from MarketDao m WHERE SUBSTRING(m.marketTime, 11) in (' 00:00:00') ORDER BY m.marketTime DESC ")
    List<MarketDao> listAllOrderByMarketTime();

    @Query("select m from MarketDao m WHERE SUBSTRING(m.marketTime, 11) in (:times) AND m.marketDay =:marketDay AND m.symbol =:symbol ORDER BY m.marketTime ASC")
    List<MarketDao> listAllOrderByDayAndTimePre(@Param("times") List<String> times, @Param("marketDay")String marketDay, @Param("symbol")String symbol);

    @Query("select m from MarketDao m WHERE SUBSTRING(m.marketTime, 11) in (' 00:00:00', ' 00:10:00', ' 00:20:00') AND m.marketDay =: marketDay ORDER BY m.marketTime ASC")
    List<MarketDao> listAllOrderByDayAndTimeNext(@Param("marketDay") String marketDay);

    @Query("select COUNT(m.id) from MarketDao m WHERE SUBSTRING(m.marketTime, 1, 10) =:day AND  m.symbol =:symbol")
    int getCountByDay(@Param("day") String day, @Param("symbol")String symbol);

}
