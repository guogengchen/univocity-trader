package com.univocity.trader.chart.charts.scrolling;

import com.univocity.trader.candles.*;
import com.univocity.trader.chart.charts.*;

import java.awt.*;

/**
 * @author uniVocity Software Pty Ltd - <a href="mailto:dev@univocity.com">dev@univocity.com</a>
 */
class TimeIntervalHandle extends Draggable {
    private final boolean leftHandle;
    private int width = 8;
    private int maxPosition = width;
    private int minPosition = 0;
    private Cursor cursor;
    Candle candle;

    private static final Color glassBlack = new Color(0, 0, 0, 128);
    private static final Color glassGray = new Color(128, 128, 128, 128);
    private Point gradientStart = new Point(0, 0);
    private Point gradientEnd = new Point(0, 0);

    public TimeIntervalHandle(boolean leftHandle) {
        this.leftHandle = leftHandle;

        if (leftHandle) {
            setCursor(Cursor.getPredefinedCursor(Cursor.W_RESIZE_CURSOR));
            setMinPosition(0);
            setPosition(0);
        } else {
            setCursor(Cursor.getPredefinedCursor(Cursor.E_RESIZE_CURSOR));
        }

    }

    public Cursor getCursor() {
        return cursor;
    }

    public void setCursor(Cursor cursor) {
        this.cursor = cursor;
    }

    public boolean isCursorOver(Point p) {
        if (leftHandle) {
            return p.x >= getPosition() && p.x <= getPosition() + width;
        } else {
            return p.x >= getPosition() - width && p.x <= getPosition();
        }
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getWidth() {
        return width;
    }

    public int getMaxPosition() {
        return maxPosition;
    }

    public void setMaxPosition(int maxPosition) {
        this.maxPosition = maxPosition;
        setPosition(getPosition());
    }

    public int getMinPosition() {
        return minPosition;
    }

    public void setMinPosition(int minPosition) {
        this.minPosition = minPosition;
        setPosition(getPosition());
    }

    public void draw(Graphics2D g, Component c, BasicChart<?> chart) {
        if (chart != null) {
            Point location = chart.locationOf(candle);
            if (location != null) {
                setPosition(location.x);
            }
        }

        int position = this.getPosition();
        if (!leftHandle) {
            position = position - width;
        }

        gradientStart.x = position - 2;
        gradientEnd.x = position + (width / 2);
        g.setPaint(new GradientPaint(gradientStart, glassBlack, gradientEnd, glassGray));
        g.fillRect(position, 0, width / 2, c.getHeight());

        gradientStart.x = position + (width / 2);
        gradientEnd.x = position + width + 2;
        g.setPaint(new GradientPaint(gradientStart, glassGray, gradientEnd, glassBlack));
        g.fillRect(position + width / 2, 0, width / 2, c.getHeight());

        g.setColor(glassGray);
        if (leftHandle) {
            g.drawRect(position + 1, 0, width, c.getHeight());
        } else {
            g.drawRect(position - 1, 0, width, c.getHeight());
        }
    }

    @Override
    protected int minPosition() {
        return minPosition;
    }

    @Override
    protected int maxPosition() {
        return maxPosition;
    }
}
