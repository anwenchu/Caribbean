package com.anwenchu.caribbean.channel.huobi.account.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class AccountBalance {

  private Long id;

  private String type;

  private String state;

  private List<Balance> list;

  private String subType;

}
