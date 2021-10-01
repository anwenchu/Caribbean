package com.anwenchu.caribbean.channel.huobi.order.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CandlestickReq {

  private String ch;

  private Long ts;

  private List<Candlestick> candlestickList;

}
