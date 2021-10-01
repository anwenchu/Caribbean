package com.anwenchu.caribbean.channel.huobi.trade.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TradeClearingEvent {
  private String action;

  private String topic;

  private TradeClearing tradeClearing;

}
