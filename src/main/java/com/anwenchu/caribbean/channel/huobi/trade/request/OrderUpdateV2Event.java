package com.anwenchu.caribbean.channel.huobi.trade.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderUpdateV2Event {

  private String action;

  private String topic;

  private OrderUpdateV2 orderUpdate;


}
