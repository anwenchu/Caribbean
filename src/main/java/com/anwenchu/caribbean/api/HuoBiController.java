package com.anwenchu.caribbean.api;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.anwenchu.caribbean.channel.huobi.order.response.SymbolInfo;
import com.anwenchu.caribbean.channel.huobi.order.service.Client;
import com.anwenchu.caribbean.channel.huobi.http.HuobiRestConnection;
import com.anwenchu.caribbean.channel.huobi.model.SymbolDao;
import com.anwenchu.caribbean.channel.huobi.repository.SymbolRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/test")
@Slf4j
public class HuoBiController {


    @Autowired
    private Client client;

    @Autowired
    private SymbolRepository symbolRepository;

    @Autowired
    private HuobiRestConnection huobiRestConnection;


    @GetMapping("/init/symbols")
    public Object initSymbols() {

        JSONObject jsonObject = huobiRestConnection.executeGet("https://www.huobi.bo/-/x/pro/v2/beta/common/symbols");
        JSONArray data = jsonObject.getJSONArray("data");
        List<SymbolInfo> symbolInfo = JSON.parseArray(data.toJSONString(), SymbolInfo.class);
        Map<String, Long> symbolMap = new HashMap<>();
        symbolInfo.forEach(symbolInfo1 -> symbolMap.put(symbolInfo1.getSymbol_code(), symbolInfo1.getTrade_open_at()));
        List<com.anwenchu.caribbean.channel.huobi.order.response.Symbol> symbols = client.getSymbols();
        symbols.forEach(symbol -> {
            if (symbol.getQuoteCurrency().equalsIgnoreCase("usdt")) {
                try {
                    SymbolDao symbolDao = new SymbolDao();
                    symbolDao.setSymbol(symbol.getSymbol());
                    symbolDao.setBaseCurrency(symbol.getBaseCurrency());
                    symbolDao.setQuoteCurrency(symbol.getQuoteCurrency());
                    symbolDao.setTradeOpenAt(new Date(symbolMap.get(symbol.getSymbol())));
                    symbolRepository.save(symbolDao);
                } catch (Exception e) {
                    log.error("{} 已存在", symbol.getSymbol());
                }
            }
        });
        return symbols;
    }

}
