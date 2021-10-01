package com.anwenchu.caribbean.channel.huobi.order.service;


import com.anwenchu.caribbean.channel.huobi.order.request.ReqCandlestickRequest;
import com.anwenchu.caribbean.channel.huobi.order.response.CandlestickReq;
import com.anwenchu.caribbean.channel.huobi.order.response.ResponseCallback;
import com.anwenchu.caribbean.channel.huobi.order.response.Symbol;

import java.util.List;

public interface Client {

  void reqCandlestick(ReqCandlestickRequest request, ResponseCallback<CandlestickReq> callback);

  List<Symbol> getSymbols();
}
