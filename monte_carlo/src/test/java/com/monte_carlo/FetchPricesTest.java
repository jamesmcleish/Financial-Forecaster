package com.monte_carlo;


import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.io.IOException;
public class FetchPricesTest {


    @Test
    public void testPriceQuoteWithValidTicker() throws IOException {
        // Arrange
        String ticker = "AAPL";
        Double actualPrice = FetchPrices.priceQuote(ticker);

        // Assert
        assertTrue(actualPrice > 0 );
    }

    @Test
    public void testInvalidTicker() {
        String invalidTicker = "INVALID";
        Double price = FetchPrices.priceQuote(invalidTicker);
        assertEquals(0, price);
    }
}