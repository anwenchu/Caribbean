package com.anwenchu.caribbean.channel.huobi.order.service;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.anwenchu.caribbean.channel.huobi.config.HuoBiArbitrageConfig;
import com.anwenchu.caribbean.channel.huobi.order.request.CandlestickReqParser;
import com.anwenchu.caribbean.channel.huobi.order.request.ReqCandlestickRequest;
import com.anwenchu.caribbean.channel.huobi.order.response.CandlestickReq;
import com.anwenchu.caribbean.channel.huobi.order.response.ResponseCallback;
import com.anwenchu.caribbean.channel.huobi.order.response.Symbol;
import com.anwenchu.caribbean.channel.huobi.http.HuobiRestConnection;
import com.anwenchu.caribbean.channel.huobi.http.HuobiWebSocketConnection;
import com.anwenchu.caribbean.channel.huobi.signature.UrlParamsBuilder;
import com.anwenchu.caribbean.utils.InputChecker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class HuobiService implements Client {
  public static final String OP_REQ = "req";

  public static final String GET_SYMBOLS_PATH = "/v1/common/symbols";

  public static final String WEBSOCKET_CANDLESTICK_TOPIC = "market.$symbol$.kline.$period$";

  

  @Autowired
  private HuobiRestConnection restConnection;

  @Autowired
  private HuoBiArbitrageConfig options;


  @Override
  public void reqCandlestick(ReqCandlestickRequest request, ResponseCallback<CandlestickReq> callback) {

    // 检查参数
    InputChecker.checker()
            .shouldNotNull(request.getSymbol(), "symbol")
            .shouldNotNull(request.getInterval(), "interval");

    String topic = WEBSOCKET_CANDLESTICK_TOPIC
            .replace("$symbol$", request.getSymbol())
            .replace("$period$", request.getInterval().getCode());

    JSONObject command = new JSONObject();
    command.put(OP_REQ, topic);
    command.put("id", System.nanoTime());
    if (request.getFrom() != null) {
      command.put("from", request.getFrom());
    }
    if (request.getTo() != null) {
      command.put("to", request.getTo());
    }
    List<String> commandList = new ArrayList<>(1);
    commandList.add(command.toJSONString());

    HuobiWebSocketConnection.createMarketConnection(options, commandList, new CandlestickReqParser(), callback, true);
  }



  public List<Symbol> getSymbols() {
    JSONObject jsonObject = restConnection.executeGet(GET_SYMBOLS_PATH, UrlParamsBuilder.build());
    JSONArray data = jsonObject.getJSONArray("data");
    return parseArray(data);
  }

  public List<Symbol> parseArray(JSONArray jsonArray) {
    if (jsonArray == null || jsonArray.size() <= 0) {
      return new ArrayList<>();
    }

    List<Symbol> list = new ArrayList<>();
    for (int i = 0; i < jsonArray.size(); i++) {
      list.add(parse(jsonArray.getJSONObject(i)));
    }

    return list;
  }

  public Symbol parse(JSONObject json) {
    return json.toJavaObject(Symbol.class);
  }


}
