package com.stock.exercise.model.stock;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import com.stock.exercise.model.manager.StockManager;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class StockImpl implements Stock {

    private String stockSymbol;
    private List<Share> shares;
    private StockManager stockManager;

    /**
     * Constructs an object of Stock with its given parameter
     *
     * @param stockSymbol the given ticker symbol to create a stock object
     */
    @Inject
    public StockImpl(StockManager stockManager,
                     @Assisted  String stockSymbol) {
        this.stockManager = stockManager;
        this.shares = new ArrayList<>();
        //Move outside the constructor
        validTicker(stockSymbol);
        this.stockSymbol = stockSymbol;
    }

    @Override
    public String getSymbol() {
        return this.stockSymbol;
    }

    @Override
    public List<Double> getShare() {
        return shares.stream()
                .map(a -> a.getShare())
                .collect(Collectors.toList());
    }

    @Override
    public List<LocalDate> getDate() {
        return shares.stream()
                .map(a -> a.getDate())
                .collect(Collectors.toList());
    }

    @Override
    public void addShare(double share, LocalDate date) {
        double cost;
        try {
            cost = stockValue(date);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
        this.shares.add(new Share(share, date));
    }

    @Override
    public void addAmount(double money, LocalDate date) {
        double share;
        try {
            share = money / stockValue(date);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
        this.shares.add(new Share(share, date));
    }

    @Override
    public double getCost(LocalDate date) {
        //Use Stream
        return this.shares
                .stream()
                .filter(a -> a.getDate().isBefore(date) || a.getDate().isEqual(date))
                .map(a -> this.stockValue(a.getDate()) * a.getShare())
                .reduce(0.0, (a, b) -> (a + b));
    }

    @Override
    public double getValue(LocalDate date) {
        //Use Stream
        return this.shares
                .stream()
                .filter(a -> a.getDate().isBefore(date) || a.getDate().isEqual(date))
                .map(a -> this.stockValue(date) * a.getShare())
                .reduce(0.0, (a, b) -> (a + b));
    }

    private void validTicker(String stockSymbol) {
        if (!this.stockManager.isCached(stockSymbol)) {
            this.stockManager.refresh(stockSymbol);
            if (!this.stockManager.isCached(stockSymbol)) {
                //Try not throw from constructer
                throw new IllegalArgumentException(
                        stockSymbol + ": " + "Sorry: there might be something wrong on your input. Try again please.");
            }
        }
    }

    /**
     * This method returns the stock prices at the given date.
     *
     * @param date the given parameter to check the stock value
     * @return the stock value
     */
    private double stockValue(LocalDate date) {
        String stockValue;
        stockValue = this.stockManager.searchStockValue(this.stockSymbol, date);
        if (!stockValue.isEmpty()) {
            return Double.valueOf(stockValue);
        }
        this.stockManager.refresh(this.stockSymbol);
        stockValue = this.stockManager.searchStockValue(this.stockSymbol, date);
        if (!stockValue.isEmpty()) {
            return Double.valueOf(stockValue);
        } else {
            throw new IllegalArgumentException(
                    "Sorry: we currently do not provide data on " + date.toString() +
                            " for " + this.stockSymbol + ".");
        }
    }
}