package edu.hw3;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class Task6Test {

    @Test
    @DisplayName("Standard Test Cases")
    void standardTestCases() {
        Task6.StockMarket stockMarket = new Task6.StockMarketImpl();

        Task6.Stock stock1 = new Task6.Stock("Apple", 150);
        Task6.Stock stock2 = new Task6.Stock("Google", 2500);
        Task6.Stock stock3 = new Task6.Stock("Microsoft", 200);

        stockMarket.add(stock1);
        stockMarket.add(stock2);
        stockMarket.add(stock3);

        assertEquals(stock2, stockMarket.mostValuableStock(), "Google should be the most valuable stock");

        stockMarket.remove(stock2);

        assertEquals(stock3, stockMarket.mostValuableStock(), "After removing Google, Microsoft should be the most valuable stock");
    }

    @Test
    @DisplayName("Edge Test Cases")
    void edgeTestCases() {
        Task6.StockMarket stockMarket = new Task6.StockMarketImpl();

        assertNull(stockMarket.mostValuableStock(), "With no stocks, mostValuableStock should return null");

        Task6.Stock stock1 = new Task6.Stock("Apple", 150);
        stockMarket.add(stock1);
        assertEquals(stock1, stockMarket.mostValuableStock(), "Apple should be the most valuable stock when it's the only stock");

        stockMarket.remove(stock1);
        assertNull(stockMarket.mostValuableStock(), "After removing the only stock, mostValuableStock should return null");
    }
}
