package com.stock.exercise.model.manager;

import com.google.inject.Singleton;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;

@Singleton
public class StockManagerImpl implements StockManager{
    private Map<String, String> stockValues;

    public StockManagerImpl() {
        this.stockValues = new HashMap<>();
        read();
    }

    public boolean isCached(String stockSymbol) {
        return stockValues.containsKey(stockSymbol);
    }

    public void refresh(String stockSymbol) {
        String stockValue = CheckingStock.fetchingData(stockSymbol);
        if (stockValue.substring(0, 9).equals("timestamp")) {
            stockValues.put(stockSymbol, stockValue);
            store(stockSymbol);
        }
    }

    public String searchStockValue(String tickerSymbol, LocalDate date) {
        if (!stockValues.containsKey(tickerSymbol))
            return "";
        Scanner scanner = new Scanner(stockValues.get(tickerSymbol));
        while (scanner.hasNext()) {
            String line = scanner.nextLine();
            String[] list = line.split(",");
            String yearMonthDay = list[0];
            if (yearMonthDay.equals(date.toString())) {
                return list[4];
            }
        }
        return "";
    }

    /**
     * This method must be called first. Try to retrieve cached stock information from text file
     * Expect to use JDBC and retrieve data from a database later
     */
    private void read() {
        System.out.println("Read from cached file to memory");
        try {
            String json = Files.readString(Path.of("cache"));
            ObjectMapper mapper = new ObjectMapper();
            stockValues = mapper.readValue(json, new TypeReference<Map<String, String>>() {});
        } catch (JsonGenerationException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("Cache file not found");
        }
    }

    /**
     * Convert current stock prices in memory to JSON String and store to cache
     * Expect to use JDBC and store to a database later
     * @param tickerSymbol
     */
    private void store(String tickerSymbol) {
        ObjectMapper mapper = new ObjectMapper();
        try{
            String json = mapper.writeValueAsString(stockValues);
            FileWriter file = new FileWriter("cache");
            file.write(json);
            file.close();
        } catch (JsonGenerationException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("store from memory");
    }
}