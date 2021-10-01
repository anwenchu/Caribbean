package com.anwenchu.caribbean.channel.huobi.order.request;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.anwenchu.caribbean.channel.huobi.account.HuobiModelParser;
import com.anwenchu.caribbean.channel.huobi.order.response.CandlestickReq;
import com.anwenchu.caribbean.utils.DataUtils;

import java.util.List;

public class CandlestickReqParser implements HuobiModelParser<CandlestickReq> {

  @Override
  public CandlestickReq parse(JSONObject json) {

    String dataKey = DataUtils.getDataKey(json);

    return CandlestickReq.builder()
        .ch(json.getString("ch"))
        .ts(json.getLong("ts"))
        .candlestickList(new CandlestickParser().parseArray(json.getJSONArray(dataKey)))
        .build();
  }

  @Override
  public CandlestickReq parse(JSONArray json) {
    return null;
  }

  @Override
  public List<CandlestickReq> parseArray(JSONArray jsonArray) {
    return null;
  }
}
