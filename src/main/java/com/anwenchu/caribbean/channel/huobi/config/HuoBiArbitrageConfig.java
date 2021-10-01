package com.anwenchu.caribbean.channel.huobi.config;

import com.anwenchu.caribbean.channel.huobi.model.Symbol;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@ConfigurationProperties("huobi.arbitrage")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Component
public class HuoBiArbitrageConfig {
    private String restHost;
    private String webSocketHost;
    private boolean webSocketAutoConnect;
    private String httpHost;
    private String apiKey;
    private String secretKey;
    private Long sportAccountId;
    private Integer scale;

    private Integer starId;
    private Integer endId;

}
