package com.anwenchu.caribbean.job.huobi;


import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import javax.annotation.PostConstruct;

import com.anwenchu.caribbean.channel.huobi.enums.CandlestickIntervalEnum;
import com.anwenchu.caribbean.channel.huobi.model.MarketDao;
import com.anwenchu.caribbean.channel.huobi.model.SymbolDao;
import com.anwenchu.caribbean.channel.huobi.order.request.ReqCandlestickRequest;
import com.anwenchu.caribbean.channel.huobi.order.response.Candlestick;
import com.anwenchu.caribbean.channel.huobi.order.service.Client;
import com.anwenchu.caribbean.channel.huobi.repository.MarketRepository;
import com.anwenchu.caribbean.channel.huobi.repository.SymbolRepository;
import com.anwenchu.caribbean.utils.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class HuoBiMarketJob {

    @Value("${com.anwenchu.fetch.num:300}")
    private int fetchNum;

    @Value("${com.anwenchu.fetch.sleep:1000}")
    private int sleepTime;

    @Value("${com.anwenchu.fetch.symbol:btcusdt}")
    private String fetchSymbol;

    @Autowired
    private MarketRepository marketRepository;

    @Autowired
    private Client client;

    @Autowired
    private SymbolRepository symbolRepository;

    @PostConstruct
    public void start() {
        List<SymbolDao> symbolDaos = symbolRepository.findBySymbol(fetchSymbol);

        //不知道为啥，这里不save一次，下面的程序执行就无法保存
        MarketDao marketDao = MarketDao.builder()
                .marketDay("").marketTime(new Date()).amount(BigDecimal.ZERO)
                .close(BigDecimal.ZERO).open(BigDecimal.ZERO).high(BigDecimal.ZERO).low(BigDecimal.ZERO)
                .count(BigDecimal.ZERO).arbitrage(BigDecimal.ZERO).vol(BigDecimal.ZERO).symbol("test")
                .build();
        marketRepository.save(marketDao);


        huoBiZeroMarket(symbolDaos.get(0));
    }


    @SuppressWarnings("BusyWait")
    private void huoBiZeroMarket(SymbolDao symbol) {

        long startTime = symbol.getTradeOpenAt().getTime();

        PageRequest pageRequest = PageRequest.of(0, 1);

        Page<MarketDao> marketDaos =
                marketRepository.findBySymbolOrderByMarketTimeDesc(symbol.getSymbol(), pageRequest);
        if (Objects.nonNull(marketDaos) && marketDaos.getContent().size() > 0) {
            MarketDao marketDao = marketDaos.getContent().get(0);
            long fetchTime = marketDao.getMarketTime().getTime();
            startTime = Math.max(startTime, fetchTime);
        }

        long start = startTime / 1000;
        // 1min 线 300 根
        long end = start + (fetchNum * 60);

        while (System.currentTimeMillis() > (end * 1000)) {
            log.warn("获取交易对:{} 在时间:{} ~ {}的K线", symbol.getSymbol(), start, end);
            try {
                client.reqCandlestick(ReqCandlestickRequest.builder()
                        .symbol(symbol.getSymbol())
                        .from(start)
                        .to(end)
                        .interval(CandlestickIntervalEnum.MIN1)
                        .build(),
                        candlestickReq -> saveMarketDao(candlestickReq.getCandlestickList(), symbol.getSymbol()));

                Thread.sleep(sleepTime);
                start = end;
                end = start + (fetchNum * 60);
            } catch (Exception ioe) {
                log.error("error: ", ioe);
            }
        }
    }


    private void saveMarketDao(List<Candlestick> candlestickList, String symbol) {
        List<Candlestick> data;
        if (candlestickList.size() > fetchNum) {
            // 扔掉第一个元素，
            data = candlestickList.subList(1, candlestickList.size());
        } else {
            data = candlestickList;
        }
        data.forEach(candlestick -> {
            MarketDao marketDao = buildMarketDao(candlestick, symbol);
            try {
                marketRepository.save(marketDao);
            } catch (Throwable e) {
                log.error("数据已存在 {}", candlestick, e);
            }
        });
    }

    private MarketDao buildMarketDao(Candlestick candlestick, String symbol) {
        MarketDao marketDao = new MarketDao();
        Date date = new Date(candlestick.getId() * 1000);
        marketDao.setMarketTime(date);
        marketDao.setMarketDay(DateUtils.formatDateYYYYMMDD(date));
        marketDao.setAmount(candlestick.getAmount());
        marketDao.setClose(candlestick.getClose());
        marketDao.setCount(candlestick.getCount());
        marketDao.setHigh(candlestick.getHigh());
        marketDao.setLow(candlestick.getLow());
        marketDao.setSymbol(symbol);
        marketDao.setOpen(candlestick.getOpen());
        marketDao.setVol(candlestick.getVol());
        marketDao.setArbitrage(candlestick.getHigh().divide(candlestick.getOpen(), 4, RoundingMode.DOWN));
        return marketDao;
    }

}
