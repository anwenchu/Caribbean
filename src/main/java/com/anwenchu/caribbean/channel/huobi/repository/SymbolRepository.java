package com.anwenchu.caribbean.channel.huobi.repository;

import java.util.List;

import com.anwenchu.caribbean.channel.huobi.model.SymbolDao;
import org.springframework.data.jpa.repository.JpaRepository;


public interface SymbolRepository extends JpaRepository<SymbolDao, Long> {

    List<SymbolDao> findBySymbol(String symbol);

}
