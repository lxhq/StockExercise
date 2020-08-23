package com.stock.exercise.model.stock;

import java.time.LocalDate;
import java.util.List;

/**
 * This is the Stock interface which requires some methods that its implementation should
 * override.
 */
public interface Stock {

    /**
     * This method returns the stock ticker
     *
     * @return the symbol of the stock
     */
    String getSymbol();

    /**
     * This method returns all shares for this Stock
     *
     * @return a list of double numbers documenting the shares of each stock
     */
    List<Double> getShare();

    /**
     * This method returns all dates when each share was bought accordingly
     *
     * @return a list of date documenting when shares are bought accordingly
     */
    List<LocalDate> getDate();

    /**
     * Buy certain amount of shares{share} in a given date{date}
     *
     * @param share the given number to buy in
     * @param date  the given date to buy in
     */
    void addShare(double share, LocalDate date);

    /**
     * Buy this stock by using certain amount of money{money} at a given date{date}
     *
     * @param money         the given amount to invest
     * @param date          the given date
     */
    void addAmount(double money, LocalDate date);

    /**
     * This method returns the cost of all shares of this stock bought before a certain date{date}
     * Traverse all shares bought before the certain date
     * Then add all shares' costs together to a variable
     * Return this variable
     *
     * @param date the given searching date
     * @return the cost before the given date
     */
    double getCost(LocalDate date);

    /**
     * This method returns the value of all shares of this stock bought before a certain date{date}
     * Traverse all shares bought before the certain date
     * Add all shares' value in the certain date together to a variable
     * Return this variable
     *
     * @param date the given date to refer to
     * @return the value of the stocks bought
     */
    double getValue(LocalDate date);
}