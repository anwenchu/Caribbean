package com.anwenchu.caribbean.channel.huobi.account.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.math.BigDecimal;

/**
 * The balance of specified account.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Balance {

  private String currency;

  private String type;

  private BigDecimal balance;

}
