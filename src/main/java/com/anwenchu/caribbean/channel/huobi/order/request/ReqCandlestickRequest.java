package com.anwenchu.caribbean.channel.huobi.order.request;

import com.anwenchu.caribbean.channel.huobi.enums.CandlestickIntervalEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ReqCandlestickRequest {

  private String symbol;

  private CandlestickIntervalEnum interval;

  private Long from;

  private Long to;

}
