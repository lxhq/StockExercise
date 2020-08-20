package com.stock.exercise.model.stock;

public interface StockFactory {
    public Stock create(String stockSymbol);
}
