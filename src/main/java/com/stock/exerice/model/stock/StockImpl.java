package com.stock.exerice.model.stock;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import com.stock.exerice.model.manager.StockManager;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class StockImpl implements Stock {

    private String stockSymbol;
    private List<Double> shares;
    private List<LocalDate> dates;
    private List<Double> costs;

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
        this.dates = new ArrayList<>();
        this.costs = new ArrayList<>();
        if (!this.stockManager.isCached(stockSymbol)) {
            this.stockManager.refresh(stockSymbol);
            if (!this.stockManager.isCached(stockSymbol)) {
                throw new IllegalArgumentException(
                        stockSymbol + ": " + "Sorry: there might be something wrong on your input. Try again please.");
            }
        }
        this.stockSymbol = stockSymbol;
    }

    @Override
    public String getSymbol() {
        return this.stockSymbol;
    }

    @Override
    public List<Double> getShare() {
        return new ArrayList<>(this.shares);
    }

    @Override
    public List<LocalDate> getDate() {
        return new ArrayList<>(this.dates);
    }

    @Override
    public void addShare(double share, LocalDate date) {
        double cost;
        try {
            cost = stockValue(date);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
        this.shares.add(share);
        this.dates.add(date);
        this.costs.add(cost * share);
    }

    @Override
    public void addAmount(double money, LocalDate date) {
        double share;
        try {
            share = money / stockValue(date);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
        this.shares.add(share);
        this.dates.add(date);
        this.costs.add(money);
    }

    @Override
    public double getCost(LocalDate date) {
        double cost = 0.0;
        for (int i = 0; i < this.dates.size(); i++) {
            if (this.dates.get(i).isBefore(date) || this.dates.get(i).isEqual(date)) {
                cost += this.costs.get(i);
            }
        }
        return cost;
    }

    @Override
    public double getValue(LocalDate date) {
        double value = 0.0;
        for (int i = 0; i < this.dates.size(); i++) {
            if (this.dates.get(i).isBefore(date) || this.dates.get(i).isEqual(date)) {
                value += stockValue(date) * this.shares.get(i);
            }
        }
        return value;
    }

    /**
     * Gets the stock value at the given date.
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