package com.anwenchu.caribbean.channel.huobi.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Symbol {

    private String name;

    // 基础货币 btcusdt 中的btc
    private String baseCurrency;

    // 基础货币 btcusdt 中的usdt
    private String quoteCurrency;

}
