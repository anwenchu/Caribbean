package com.anwenchu.caribbean.channel.huobi.trade.request;

import com.anwenchu.caribbean.channel.huobi.enums.OrderTypeEnum;
import com.anwenchu.caribbean.channel.huobi.enums.QueryDirectionEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class MatchResultRequest {

  private String symbol;

  private List<OrderTypeEnum> types;

  private Date startDate;

  private Date endDate;

  private Integer size;

  private String from;

  private QueryDirectionEnum direction;


  public String getTypeString(){
    if (types == null || types.size() <= 0) {
      return null;
    }

    StringBuffer typeBuffer = new StringBuffer();
    this.getTypes().forEach(orderType -> {
      typeBuffer.append(orderType.getCode()).append(",");
    });

    return typeBuffer.substring(0, typeBuffer.length() - 1);
  }

}
