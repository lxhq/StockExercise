package com.stock.exerice.model.stock;

import com.google.inject.Inject;
import com.stock.exerice.model.manager.StockManager;

import java.time.LocalDate;
import java.util.List;

/**
 * This is the Stock interface which requires some methods that its implementation should
 * override.
 */
public interface Stock {

    /**
     * Gets the ticker symbol of the stock.
     *
     * @return the symbol of the stock
     */
    String getSymbol();

    /**
     * Gets the shares of each stock.
     *
     * @return a list of double numbers documenting the shares of each stock
     */
    List<Double> getShare();

    /**
     * Gets the date of buying stocks.
     *
     * @return a list of date documenting when stocks are bought
     */
    List<LocalDate> getDate();

    /**
     * Buy {share} shares stock in {date} date
     *
     * @param share the given number to buy in
     * @param date  the given date to buy in
     */
    void addShare(double share, LocalDate date);

    /**
     * Buy this stock by using certain amount of money at the given date
     *
     * @param money         the given amount to invest
     * @param date          the given date
     */
    void addAmount(double money, LocalDate date);

    /**
     * Gets the cost of stocks bought before the search date.
     *
     * @param date the given searching date
     * @return the cost before the given date
     */
    double getCost(LocalDate date);

    /**
     * Gets the value of all stocks bought.
     *
     * @param date the given date to refer to
     * @return the value of the stocks bought
     */
    double getValue(LocalDate date);
}