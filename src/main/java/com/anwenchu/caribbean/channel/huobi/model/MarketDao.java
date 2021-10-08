package com.anwenchu.caribbean.channel.huobi.model;


import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.*;

import lombok.*;


@Entity
@Table(name = "t_huobi_market") // 指定关联的数据库的表名
@Getter
@Setter
@ToString
@AllArgsConstructor
@Builder
public class MarketDao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "f_id")
    private Long id; // 主键ID

    @Column(name = "f_symbol")
    private String symbol;

    @Column(name = "f_amount")
    private BigDecimal amount;

    @Column(name = "f_count")
    private BigDecimal count;

    @Column(name = "f_open")
    private BigDecimal open;

    @Column(name = "f_close")
    private BigDecimal close;

    @Column(name = "f_high")
    private BigDecimal high;

    @Column(name = "f_low")
    private BigDecimal low;

    @Column(name = "f_vol")
    private BigDecimal vol;

    @Column(name = "f_arbitrage")
    private BigDecimal arbitrage;

    @Column(name = "f_market_time")
    private Date marketTime;

    @Column(name = "f_market_day")
    private String marketDay;

    public MarketDao() {}

}
