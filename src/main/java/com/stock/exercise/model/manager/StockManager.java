package com.stock.exercise.model.manager;

import java.time.LocalDate;

public interface StockManager {
    /**
     * Return true if the stockSymbol is cached in memory
     * @param stockSymbol Stock ticker, we want to check
     * @return True for in memory. False for not
     */
    boolean isCached(String stockSymbol);

    /**
     * Fetch Stock prices for the given ticker from the Internet and cache them into a local file
     * @param stockSymbol the stock ticker
     */
    void refresh(String stockSymbol);

    /**
     * Return the stock price with the given ticker and date.
     *
     * @param date the given date to get stock value
     * @return the stock value with given parameters
     */
    String searchStockValue(String tickerSymbol, LocalDate date);
}
