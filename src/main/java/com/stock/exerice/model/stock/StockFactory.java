package com.stock.exerice.model.stock;

public interface StockFactory {
    public Stock create(String stockSymbol);
}
