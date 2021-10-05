package com.anwenchu.caribbean.job.huobi;


import com.anwenchu.caribbean.channel.huobi.config.HuoBiArbitrageConfig;
import com.anwenchu.caribbean.channel.huobi.enums.CandlestickIntervalEnum;
import com.anwenchu.caribbean.channel.huobi.order.request.ReqCandlestickRequest;
import com.anwenchu.caribbean.channel.huobi.order.response.Candlestick;
import com.anwenchu.caribbean.channel.huobi.order.service.Client;
import com.anwenchu.caribbean.channel.huobi.model.MarketDao;
import com.anwenchu.caribbean.channel.huobi.repository.MarketRepository;
import com.anwenchu.caribbean.channel.huobi.repository.SymbolRepository;
import com.anwenchu.caribbean.utils.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.math.RoundingMode;
import java.util.Date;
import java.util.List;

@Component
@Slf4j
public class HuoBiMarketJob {

    @Autowired
    private MarketRepository marketRepository;

    @Autowired
    private Client client;

    @Autowired
    private HuoBiArbitrageConfig config;

    @Autowired
    private SymbolRepository symbolRepository;

    private volatile boolean running;


    @Scheduled(fixedDelay = 120000)
    public void huoBiZeroMarket() {
        symbolRepository.findAll().subList(config.getStarId(), config.getEndId()).forEach(symbol -> {
            Date now = DateUtils.getTime(1632930600000L);
            Date before = DateUtils.getBeforeYear(now, -1);
            while (now.after(before)) {
                Date toDate = DateUtils.getAfterMinute(now, 2000);
                Long to = toDate.getTime()/1000;
                Long from = now.getTime()/1000;
                String day = DateUtils.formatDateYYYYMMDD(now);
                if (marketRepository.getCountByDay(day, symbol.getSymbol()) < 21 &&
                        symbol.getTradeOpenAt().before(now) && !day.equalsIgnoreCase("2020-09-27")) {
                    log.warn("获取交易对:{} 在时间:{} ~ {}的K线", symbol.getSymbol(), now, toDate);
                    try {
                        client.reqCandlestick(ReqCandlestickRequest.builder()
                                .symbol(symbol.getSymbol())
                                .from(from)
                                .to(to)
                                .interval(CandlestickIntervalEnum.MIN1)
                                .build(), candlestickReq -> {
                            saveMarketDao(candlestickReq.getCandlestickList(), symbol.getSymbol());
                        });
                    } catch (Exception e) {
                        log.error("error: ", e);
                    }
                }
                now = DateUtils.getBeforeDay2(now);
            }

        });
    }

    private void saveMarketDao(List<Candlestick> candlestickList, String symbol) {
        candlestickList.forEach(candlestick -> {
            MarketDao marketDao = buildMarketDao(candlestick, symbol);
            try {
                marketRepository.save(marketDao);
            } catch (Exception e) {
                log.error("数据已存在 {}", candlestick);
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
