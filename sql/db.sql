-- auto-generated definition
create table t_huobi_market_day
(
    f_id         bigint unsigned auto_increment comment '主键'
        primary key,
    f_symbol     varchar(64)     not null comment '交易对',
    f_amount     decimal(36, 18) not null comment '成交数量',
    f_count      decimal(36, 18) not null comment '成交单数',
    f_open       decimal(36, 18) not null comment '开盘价格',
    f_close      decimal(36, 18) not null comment '收盘价格',
    f_high       decimal(36, 18) not null comment '最高价',
    f_low        decimal(36, 18) not null comment '最低价',
    f_vol        decimal(36, 18) not null comment '成交额, 即 sum(每一笔成交价 * 该笔的成交量)',
    f_arbitrage  decimal(36, 18) not null comment '最高收益 最高价/开盘价',
    f_rate       decimal(36, 18) not null comment '涨跌幅百分比',
    f_market_day varchar(64)     not null comment '行情时间，日：2020-01-01',
    constraint uniq_idx_symbol_time
        unique (f_symbol, f_market_day)
)
    comment '行情数据-天' charset = utf8;



-- auto-generated definition
create table t_huobi_market
(
    f_id          bigint unsigned auto_increment comment '主键'
        primary key,
    f_symbol      varchar(64)     not null comment '交易对',
    f_amount      decimal(36, 18) not null comment '成交数量',
    f_count       decimal(36, 18) not null comment '成交单数',
    f_open        decimal(36, 18) not null comment '开盘价格',
    f_close       decimal(36, 18) not null comment '收盘价格',
    f_high        decimal(36, 18) not null comment '最高价',
    f_low         decimal(36, 18) not null comment '最低价',
    f_vol         decimal(36, 18) not null comment '成交额, 即 sum(每一笔成交价 * 该笔的成交量)',
    f_arbitrage   decimal(36, 18) not null comment '最高收益 最高价/开盘价',
    f_market_time timestamp       not null comment '行情时间',
    f_market_day  varchar(64)     not null comment '行情时间，日：2020-01-01',
    constraint uniq_idx_symbol_time
        unique (f_symbol, f_market_time)
)
    comment '行情数据' charset = utf8;

create index idx_market_day
    on t_huobi_market (f_market_day);

create index idx_market_time
    on t_huobi_market (f_market_time);

create index idx_symbol
    on t_huobi_market (f_symbol);

-- auto-generated definition
create table t_huobi_symbols
(
    f_id             bigint auto_increment
        primary key,
    f_symbol         varchar(256) not null,
    f_base_currency  varchar(256) not null,
    f_quote_currency varchar(256) not null,
    f_trade_open_at  timestamp    not null comment '开盘时间',
    constraint uniq_idx_symbol
        unique (f_symbol)
);




