package com.univocity.trader.chart.charts;

import com.univocity.trader.candles.*;
import com.univocity.trader.chart.*;
import com.univocity.trader.chart.charts.controls.*;

import java.awt.*;
import java.util.function.*;

public class HistogramChart extends FilledBarChart<HistogramChartController> {

    private final Function<Candle, Double> valueReader;

    public HistogramChart(CandleHistoryView candleHistory) {
        this(candleHistory, (c) -> c.volume);
    }

    public HistogramChart(CandleHistoryView candleHistory, Function<Candle, Double> valueReader) {
        super(candleHistory);
        this.valueReader = valueReader;
    }

    @Override
    protected void drawBar(Candle trade, Point location, Graphics2D g, Color lineColor, Color fillColor) {
        int h = getHeight() - getYCoordinate(getCentralValue(trade));
        g.setColor(fillColor);
        g.fillRect(location.x - getBarWidth() / 2, getHeight() - h, getBarWidth(), h);

        g.setColor(lineColor);
        g.drawRect(location.x - getBarWidth() / 2, getHeight() - h, getBarWidth(), h);
    }

    @Override
    public HistogramChartController newController() {
        return new HistogramChartController(this);
    }

    public double getHighestPlottedValue(Candle candle) {
        return valueReader.apply(candle);
    }

    public double getLowestPlottedValue(Candle candle) {
        return valueReader.apply(candle);
    }

    public double getCentralValue(Candle candle) {
        return valueReader.apply(candle);
    }
}
