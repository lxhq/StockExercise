package com.stock.exerice.model.manager;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

/**
 * This is a helper class to get some values about stock.
 */
public final class CheckingStock {

    /**
     * Fetching the data with given tickerSymbol.
     *
     * @param tickerSymbol the given parameter
     * @return the stock information
     */
    public static String fetchingData(String tickerSymbol) {
        String apiKey = "537ORK8WW95GYYS2";
        String stockSymbol = tickerSymbol;
        URL url = null;

        try {
            url = new URL("https://www.alphavantage"
                    + ".co/query?function=TIME_SERIES_DAILY"
                    + "&outputsize=full"
                    + "&symbol"
                    + "=" + stockSymbol + "&apikey=" + apiKey + "&datatype=csv");
        } catch (MalformedURLException e) {
            throw new RuntimeException("the alphavantage API has either changed or "
                    + "no longer works");
        }
        InputStream in = null;
        StringBuilder output = new StringBuilder();

        try {
            in = url.openStream();
            int b;

            while ((b = in.read()) != -1) {
                output.append((char) b);
            }
        } catch (IOException e) {
            throw new IllegalArgumentException("No price data found for " + stockSymbol);
        }
        return validStockData(output.toString());
    }

    /**
     * Gets rid of data whose volume is 0 from data from Alpha API.
     *
     * @param dataFromAlpha the data fetching from Alpha API
     * @return all valid data
     */
    private static String validStockData(String dataFromAlpha) {
        if (!dataFromAlpha.substring(0, 9).equals("timestamp")) {
            return dataFromAlpha;
        }
        StringBuilder sb = new StringBuilder();
        Scanner scanner = new Scanner(dataFromAlpha);
        while (scanner.hasNext()) {
            String line = scanner.nextLine();
            String[] list = line.split(",");
            String volume = list[5];
            if (!volume.equals("0")) {
                sb.append(line + "\n");
            }
        }
        return sb.toString();
    }
}
