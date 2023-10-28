package edu.hw3;

import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * This class provides a simple stock market implementation.
 */
public class Task6 {

    private Task6() {}

    /**
     * Represents a stock market with operations to add, remove, and find the most valuable stock.
     */
    public interface StockMarket {

        /**
         * Adds a stock to the market.
         *
         * @param stock The stock to be added.
         */
        void add(Stock stock);

        /**
         * Removes a stock from the market.
         *
         * @param stock The stock to be removed.
         */
        void remove(Stock stock);

        /**
         * Retrieves the most valuable stock from the market.
         *
         * @return The stock with the highest price.
         */
        Stock mostValuableStock();
    }

    /**
     * Represents a stock with a name and price.
     */
    public record Stock(String name, int price) {
        @Override
        public String toString() {
            return "Stock{name='" + name + "', price=" + price + '}';
        }
    }

    /**
     * A simple stock market implementation using a priority queue.
     */
    public static class StockMarketImpl implements StockMarket {

        private final PriorityQueue<Stock> stocks;

        public StockMarketImpl() {
            this.stocks = new PriorityQueue<>(Comparator.comparingInt(Stock::price).reversed());
        }

        @Override
        public void add(Stock stock) {
            stocks.offer(stock);
        }

        @Override
        public void remove(Stock stock) {
            stocks.remove(stock);
        }

        @Override
        public Stock mostValuableStock() {
            return stocks.peek();
        }
    }
}
