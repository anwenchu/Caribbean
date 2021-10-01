package com.anwenchu.caribbean.channel.huobi.order.request;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.anwenchu.caribbean.channel.huobi.account.HuobiModelParser;
import com.anwenchu.caribbean.channel.huobi.order.response.Candlestick;

import java.util.List;

public class CandlestickParser implements HuobiModelParser<Candlestick> {

  @Override
  public Candlestick parse(JSONObject json) {
    return json.toJavaObject(Candlestick.class);
  }

  @Override
  public Candlestick parse(JSONArray json) {
    return null;
  }

  @Override
  public List<Candlestick> parseArray(JSONArray jsonArray) {
    return jsonArray.toJavaList(Candlestick.class);
  }
}
