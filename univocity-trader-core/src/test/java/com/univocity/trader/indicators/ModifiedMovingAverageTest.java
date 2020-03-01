package com.univocity.trader.indicators;

import org.junit.*;

import static com.univocity.trader.candles.CandleHelper.*;
import static com.univocity.trader.indicators.base.TimeInterval.*;
import static junit.framework.TestCase.*;

public class ModifiedMovingAverageTest {

    @Test
    public void isUsable() {
        ModifiedMovingAverage ma = new ModifiedMovingAverage(10, minutes(1));

        update(ma, 1, 64.75);
        update(ma, 2, 63.79);
        update(ma, 3, 63.73);
        update(ma, 4, 63.73);
        update(ma, 5, 63.55);
        update(ma, 6, 63.19);
        update(ma, 7, 63.91);
        update(ma, 8, 63.85);
        update(ma, 9, 62.95);
        assertEquals(63.9983, update(ma, 10, 63.37), 0.001);
        assertEquals(63.7315, update(ma, 11, 61.33), 0.001);
        assertEquals(63.5093, update(ma, 12, 61.51), 0.001);

        ma = new ModifiedMovingAverage(3, minutes(2));
        ma.recalculateEveryTick(true);

        assertEquals(1.0, update(ma, 1, 1.0), 0.001);
        assertEquals(2.0, update(ma, 2, 2.0), 0.001);

        assertEquals(2.0, update(ma, 3, 2.0), 0.001);
        assertEquals(1.666, update(ma, 4, 1.0), 0.001);

        assertEquals(1.444, update(ma, 5, 1.0), 0.001);
        assertEquals(1.611, update(ma, 6, 1.5), 0.001);
    }

}