package com.univocity.trader.exchange.interactivebrokers.api;

import com.univocity.trader.candles.*;
import com.univocity.trader.utils.*;

/**
 * @author uniVocity Software Pty Ltd - <a href="mailto:dev@univocity.com">dev@univocity.com</a>
 */
public class IBIncomingCandles extends IncomingCandles<Candle> {

    public IBIncomingCandles() {}

    public IBIncomingCandles(long timeout) {
        super(timeout);
    }

    private Candle prev;
    private int increment;

    @Override
    public void add(Candle candle) {
        // Workaround to deal with IB ticks that don't have millisecond precision and will come in the same second
        if (prev != null && prev.openTime == candle.openTime && prev.closeTime == candle.closeTime) {
            increment++;
            candle.closeTime += increment;
            candle.openTime += increment;
        } else {
            prev = candle;
            increment = 0;
        }
        super.add(candle);
    }
}
