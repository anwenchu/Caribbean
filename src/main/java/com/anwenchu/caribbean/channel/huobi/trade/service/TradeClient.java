package com.anwenchu.caribbean.channel.huobi.trade.service;


import com.anwenchu.caribbean.channel.huobi.trade.request.CreateOrderRequest;

public interface TradeClient {

  Long createOrder(CreateOrderRequest request);
//
//  Long cancelOrder(Long orderId);
//
//  Integer cancelOrder(String clientOrderId);
//
//  com.huobi.model.trade.BatchCancelOpenOrdersResult batchCancelOpenOrders(BatchCancelOpenOrdersRequest request);
//
//  com.huobi.model.trade.BatchCancelOrderResult batchCancelOrder(List<Long> ids);
//
//  List<Order> getOpenOrders(OpenOrdersRequest request);
//
//  Order getOrder(Long orderId);
//
//  Order getOrder(String clientOrderId);
//
//  List<Order> getOrders(OrdersRequest request);
//
//  List<Order> getOrdersHistory(OrderHistoryRequest request);
//
//  List<com.huobi.model.trade.MatchResult> getMatchResult(Long orderId);
//
//  List<com.huobi.model.trade.MatchResult> getMatchResults(MatchResultRequest request);
//
//  List<com.huobi.model.trade.FeeRate> getFeeRate(FeeRateRequest request);
//
//  void subOrderUpdateV2(SubOrderUpdateV2Request request, ResponseCallback<OrderUpdateV2Event> callback);
//
//  void subTradeClearing(SubTradeClearingRequest request, ResponseCallback<TradeClearingEvent> callback);

//  static TradeClient create(Options options) {
//
//    if (options.getExchange().equals(ExchangeEnum.HUOBI)) {
//      return new HuobiTradeService(options);
//    }
//    throw new SDKException(SDKException.INPUT_ERROR, "Unsupport Exchange.");
//  }

}
