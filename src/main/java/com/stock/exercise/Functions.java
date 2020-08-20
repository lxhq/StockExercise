package com.stock.exercise;

import com.stock.exercise.model.stock.Stock;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public final class Functions {

    /**
     * Get Top five valuable stocks in all stocks we currently hold
     * value = current stock price per share * shares for this stock we currently hold
     * @param list All stocks we hold
     * @return Top valuable five stocks
     */
    public static List<Stock> getCurrentTopFiveValuableStocks(List<Stock> list, LocalDate localDate) {
        return list
                .stream()
                .sorted((a, b) -> (Double.compare(b.getValue(localDate), a.getValue(localDate))))
                .limit(5)
                .collect(Collectors.toList());
    }

    /**
     * Top five expense stock we currently hold
     * expense = stock price per share when we bought it * shares for this stock we currently hold
     * @param list All stocks we hold
     * @return Top five expense stocks
     */
    public static List<Stock> getCurrentTopFiveExpenseStocks(List<Stock> list, LocalDate localDate) {
        return list
                .stream()
                .sorted((a, b) -> (Double.compare(b.getCost(localDate), a.getCost(localDate))))
                .limit(5)
                .collect(Collectors.toList());
    }

    /**
     * Traverse all stocks we have and return all stocks with a value larger than the threshold
     * @param list All stocks we hold
     * @param threshold we want returned stock(s) have value larger than this one
     * @return A list of stocks with a value larger than the threshold
     */
    public static List<Stock> getCurrentHoldStockValueLargerThan(List<Stock> list, double threshold, LocalDate localDate) {
        return list
                .stream()
                .filter(a -> a.getValue(localDate) >= threshold)
                .collect(Collectors.toList());
    }

    /**
     * Traverse all stocks we have and return all stocks that bought before a time
     * @param list All stocks we hold
     * @param localDate we want the returned stocks are bought before this time
     * @return A list of stocks that bought before a time
     */
    public static List<Stock> getStocksBoughtBefore(List<Stock> list, LocalDate localDate) {
        return list
                .stream()
                .filter(a -> {
                    for (LocalDate ld : a.getDate())
                        if (ld.isBefore(localDate) || ld.isEqual(localDate)) return true;
                    return false;
                })
                .collect(Collectors.toList());
    }

    /**
     * Traverse all stocks we have and return all profitable stocks
     * @param list All stocks we hold
     * @return A list of all profitable stocks we currently hold
     */
    public static List<Stock> getProfitableStocks(List<Stock> list, LocalDate localDate) {
        return list
                .stream()
                .filter(a -> a.getValue(localDate) > a.getCost(localDate))
                .collect(Collectors.toList());
    }

    /**
     * Traverse all stocks we have and return all unprofitable stocks
     * @param list All stocks we hold
     * @return A list of all unprofitable stocks
     */
    public static List<Stock> getUnprofitableStocks(List<Stock> list, LocalDate localDate) {
        return list
                .stream()
                .filter(a -> a.getValue(localDate) <= a.getCost(localDate))
                .collect(Collectors.toList());
    }
}
