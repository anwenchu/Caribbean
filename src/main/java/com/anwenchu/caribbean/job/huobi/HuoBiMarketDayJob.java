package com.anwenchu.caribbean.job.huobi;


import com.anwenchu.caribbean.channel.huobi.config.HuoBiArbitrageConfig;
import com.anwenchu.caribbean.channel.huobi.enums.CandlestickIntervalEnum;
import com.anwenchu.caribbean.channel.huobi.order.request.ReqCandlestickRequest;
import com.anwenchu.caribbean.channel.huobi.order.response.Candlestick;
import com.anwenchu.caribbean.channel.huobi.order.service.Client;
import com.anwenchu.caribbean.channel.huobi.model.MarketDayDao;
import com.anwenchu.caribbean.channel.huobi.repository.MarketDayRepository;
import com.anwenchu.caribbean.channel.huobi.repository.SymbolRepository;
import com.anwenchu.caribbean.utils.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.List;

@Component
@Slf4j
public class HuoBiMarketDayJob {

    @Autowired
    private MarketDayRepository marketDayRepository;


    @Autowired
    private Client client;

    @Autowired
    private HuoBiArbitrageConfig config;

    @Autowired
    private SymbolRepository symbolRepository;

    private volatile boolean running;


//    @Scheduled(fixedDelay = 5000)
    public void huoBiMarket() {
        if (running) {
            return;
        }
        symbolRepository.findAll().subList(config.getStarId(), config.getEndId()).forEach(symbol -> {
            running = true;
            Date now = DateUtils.getTime(1623859200000l);
            Date before = DateUtils.getBeforeYear(now, -3);
            while (now.after(before)) {
                Date fromDate = DateUtils.getBeforeDay(now, -200);
                Long to = now.getTime()/1000;
                Long from = fromDate.getTime()/1000;

                log.warn("获取交易对:{} 在时间:{} ~ {}的K线", symbol, fromDate, now);
                try {
                    client.reqCandlestick(ReqCandlestickRequest.builder()
                            .symbol(symbol.getSymbol())
                            .from(from)
                            .to(to)
                            .interval(CandlestickIntervalEnum.DAY1)
                            .build(), candlestickReq -> {
                        saveMarketDayDao(candlestickReq.getCandlestickList(), symbol.getSymbol());
                    });
                } catch (Exception e) {
                    log.error("error: ", e);
                }
                now = fromDate;
            }

        });
    }

    private void saveMarketDayDao(List<Candlestick> candlestickList, String symbol) {
        candlestickList.forEach(candlestick -> {
            MarketDayDao marketDao = buildMarketDayDao(candlestick, symbol);
            try {
                marketDayRepository.save(marketDao);
            } catch (Exception e) {
                log.error("数据已存在 {}", candlestick);
            }
        });
    }

    private MarketDayDao buildMarketDayDao(Candlestick candlestick, String symbol) {
        MarketDayDao marketDayDao = new MarketDayDao();
        marketDayDao.setMarketDay(DateUtils.formatDateYYYYMMDD(new Date(candlestick.getId() * 1000)));
        marketDayDao.setAmount(candlestick.getAmount());
        marketDayDao.setClose(candlestick.getClose());
        marketDayDao.setCount(candlestick.getCount());
        marketDayDao.setHigh(candlestick.getHigh());
        marketDayDao.setLow(candlestick.getLow());
        marketDayDao.setSymbol(symbol);
        marketDayDao.setOpen(candlestick.getOpen());
        marketDayDao.setVol(candlestick.getVol());
        // (最高价/开盘价)-1
        marketDayDao.setArbitrage(candlestick.getHigh().divide(candlestick.getOpen(), 4, RoundingMode.DOWN)
                .subtract(BigDecimal.ONE));
        // (收盘价/开盘价)-1
        marketDayDao.setRate(candlestick.getClose().divide(candlestick.getOpen(), 4, RoundingMode.DOWN)
                .subtract(BigDecimal.ONE));
        return marketDayDao;
    }

}
