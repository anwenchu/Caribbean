package com.anwenchu.caribbean.channel.huobi.order.response;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SymbolInfo {
    private String symbol_code;
    private Long trade_open_at;
}
