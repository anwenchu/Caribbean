package com.anwenchu.caribbean.channel.huobi.trade.request;

import com.anwenchu.caribbean.channel.huobi.enums.OrderSideEnum;
import com.anwenchu.caribbean.channel.huobi.enums.QueryDirectionEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OpenOrdersRequest {

  private  String symbol;

  private Long accountId;

  private Integer size;

  private OrderSideEnum side;

  private QueryDirectionEnum direct;

  private Long from;

}
