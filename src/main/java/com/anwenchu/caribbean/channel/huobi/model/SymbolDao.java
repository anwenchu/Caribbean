package com.anwenchu.caribbean.channel.huobi.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "t_huobi_symbols") // 指定关联的数据库的表名
@Data
public class SymbolDao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "f_id")
    private Long id; // 主键ID

    @Column(name = "f_symbol")
    private String symbol;

    @Column(name = "f_base_currency")
    private String baseCurrency;

    @Column(name = "f_quote_currency")
    private String quoteCurrency;

    @Column(name = "f_trade_open_at")
    private Date tradeOpenAt;
}
