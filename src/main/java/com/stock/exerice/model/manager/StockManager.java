package com.stock.exerice.model.manager;

import java.time.LocalDate;

public interface StockManager {
    /**
     * Test if the stockSymbol is cached in memory. If not, fetch it from Internet by API.
     * @param stockSymbol Stock ticker, we want to check
     * @return True for in memory. False for not
     */
    boolean isCached(String stockSymbol);

    /**
     * Refresh the stockValues
     * @param stockSymbol the stock ticker
     */
    void refresh(String stockSymbol);

    /**
     * Returns the stock value with given {@code tickerSymbol} and {@code date}.
     *
     * @param date the given date to get stock value
     * @return the stock value with given parameters
     */
    String searchStockValue(String tickerSymbol, LocalDate date);
}
