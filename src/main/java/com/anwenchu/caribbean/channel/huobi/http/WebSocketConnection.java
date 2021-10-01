package com.anwenchu.caribbean.channel.huobi.http;


import com.anwenchu.caribbean.channel.huobi.enums.ConnectionStateEnum;

public interface WebSocketConnection {

  ConnectionStateEnum getState();

  Long getConnectionId();

  void reConnect();

  void reConnect(int delayInSecond);

  long getLastReceivedTime();

  void send(String str);
}
