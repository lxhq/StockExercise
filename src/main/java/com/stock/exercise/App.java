package com.stock.exercise;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.stock.exercise.model.StockModule;
import com.stock.exercise.model.stock.Stock;
import com.stock.exercise.model.stock.StockFactory;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class App 
{
    public static void main( String[] args )
    {
        //injection
        Injector injector = Guice.createInjector(new StockModule());
        StockFactory sf = injector.getInstance(StockFactory.class);

        //Create stocks
        List<Stock> list = new ArrayList<>();
        list.add(sf.create("FB"));       //FaceBook
        list.add(sf.create("TCS"));      //Tata
        list.add(sf.create("INFY"));     //Infosys
        list.add(sf.create("IBM"));      //IBM
        list.add(sf.create("GOOGL"));    //Google
        list.add(sf.create("AMZN"));     //Amazon
        list.add(sf.create("CTSH"));     //Cognizant
        list.add(sf.create("ORCL"));     //Oracle
        list.add(sf.create("MSFT"));     //Microsoft
        list.add(sf.create("BAC"));      //BOA
        list.add(sf.create("JPM"));      //Chase

        //Buy Stocks
        buyStocks(list);

        //Apply functions
        applyFunctions(list);
    }

    private static void applyFunctions(List<Stock> list) {
        LocalDate localDate = LocalDate.of(2020, 8, 18);
        Function<Stock, String> fValue =
                a -> a.getSymbol() + ": " +
                        a.getShare()
                                .stream()
                                .mapToDouble(Double::byteValue)
                                .sum()
                + " shares, total value:" + a.getValue(localDate);

        Function<Stock, String> fCost =
                a -> a.getSymbol() + ": " +
                        a.getShare()
                                .stream()
                                .mapToDouble(Double::byteValue)
                                .sum()
                        + " shares, total cost:" + a.getCost(localDate);

        //Top five valuable stocks in all stocks we currently hold
        System.out.println("Top five valuable stocks in all stocks we currently hold");
        Functions.getCurrentTopFiveValuableStocks(list, localDate)
                .stream()
                .map(fValue)
                .forEach(System.out::println);
        System.out.println();

        //Top five expense stock we currently hold
        System.out.println("Top five expense stock we currently hold");
        Functions.getCurrentTopFiveExpenseStocks(list, localDate)
                .stream()
                .map(fCost)
                .forEach(System.out::println);
        System.out.println();

        //All stocks with a value larger than the threshold
        System.out.println("All stocks with a value larger than the threshold");
        Functions.getCurrentHoldStockValueLargerThan(list, 20000, localDate)
                .stream()
                .map(fValue)
                .forEach(System.out::println);
        System.out.println();

        //All stocks that bought before a time
        System.out.println("All stocks that bought before a time");
        Functions.getStocksBoughtBefore(list, LocalDate.of(2017, 1,1))
                .stream()
                .map(a -> a.getSymbol())
                .forEach(System.out::println);
        System.out.println();

        //All profitable stocks
        System.out.println("All profitable stocks");
        Functions.getProfitableStocks(list, localDate)
                .stream()
                .map(a -> a.getSymbol() + " earns " + (a.getValue(localDate) - a.getCost(localDate)) + " dollar")
                .forEach(System.out::println);
        System.out.println();

        //All unprofitable stocks
        System.out.println("All unprofitable stocks");
        Functions.getUnprofitableStocks(list, localDate)
                .stream()
                .map(a -> a.getSymbol() + " lose " + (a.getCost(localDate) - a.getValue(localDate)) + " dollar")
                .forEach(System.out::println);
        System.out.println();
    }

    private static void buyStocks(List<Stock> list) {
        for (Stock stock : list) {
            stock.addShare(10, LocalDate.of(2020, 8, 14));
        }

        //FaceBook
        list.get(0).addShare(9, LocalDate.of(2018, 7, 11));
        list.get(0).addShare(2, LocalDate.of(2018, 3, 7));
        list.get(0).addShare(3, LocalDate.of(2018, 5, 9));
        list.get(0).addShare(4, LocalDate.of(2018, 12, 4));

        //Tata
        list.get(1).addShare(4, LocalDate.of(2018, 1, 11));
        list.get(1).addShare(3, LocalDate.of(2019, 5, 9));
        list.get(1).addShare(2, LocalDate.of(2018, 3, 13));
        list.get(1).addShare(1, LocalDate.of(2017, 7, 11));


        //Infosys
        list.get(2).addShare(10, LocalDate.of(2018, 4, 11));
        list.get(2).addShare(4, LocalDate.of(2016, 7, 12));
        list.get(2).addShare(9, LocalDate.of(2018, 6, 25));
        list.get(2).addShare(12, LocalDate.of(2012, 12, 13));

        //IBM
        list.get(3).addShare(1, LocalDate.of(2010, 11, 12));
        list.get(3).addShare(2, LocalDate.of(2013, 9, 3));
        list.get(3).addShare(10, LocalDate.of(2018, 7, 23));
        list.get(3).addShare(16, LocalDate.of(2019, 5, 8));

        //Google
        list.get(4).addShare(1, LocalDate.of(2019, 1, 8));
        list.get(4).addShare(2, LocalDate.of(2018, 9, 19));
        list.get(4).addShare(9, LocalDate.of(2019, 10, 21));
        list.get(4).addShare(10, LocalDate.of(2018, 4, 30));

        //Amazon
        list.get(5).addShare(2, LocalDate.of(2017, 3, 14));
        list.get(5).addShare(3, LocalDate.of(2018, 5, 17));
        list.get(5).addShare(9, LocalDate.of(2016, 7, 20));
        list.get(5).addShare(8, LocalDate.of(2015, 11, 12));

        //Cognizant
        list.get(6).addShare(1, LocalDate.of(2016, 12, 13));
        list.get(6).addShare(10, LocalDate.of(2017, 10, 9));
        list.get(6).addShare(17, LocalDate.of(2018, 7, 6));
        list.get(6).addShare(1, LocalDate.of(2019, 3, 26));

        //Oracle
        list.get(7).addShare(2, LocalDate.of(2019, 4, 30));
        list.get(7).addShare(4, LocalDate.of(2019, 2, 20));
        list.get(7).addShare(11, LocalDate.of(2018, 1, 11));
        list.get(7).addShare(16, LocalDate.of(2017, 10, 12));

        //Microsoft
        list.get(8).addShare(8, LocalDate.of(2013, 4, 8));
        list.get(8).addShare(9, LocalDate.of(2016, 6, 13));
        list.get(8).addShare(4, LocalDate.of(2012, 1, 9));
        list.get(8).addShare(1, LocalDate.of(2017, 10, 24));

        //BOA
        list.get(9).addShare(12, LocalDate.of(2018, 11, 30));
        list.get(9).addShare(2, LocalDate.of(2014, 7, 1));
        list.get(9).addShare(4, LocalDate.of(2018, 4, 19));
        list.get(9).addShare(6, LocalDate.of(2017, 7, 3));

        //Chase
        list.get(10).addShare(9, LocalDate.of(2013, 4, 9));
        list.get(10).addShare(3, LocalDate.of(2010, 8, 19));
        list.get(10).addShare(1, LocalDate.of(2012, 9, 13));
        list.get(10).addShare(4, LocalDate.of(2018, 7, 31));
    }
}
























