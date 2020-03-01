package com.univocity.trader.iqfeed.api;

import com.univocity.trader.iqfeed.api.constant.*;
import org.apache.commons.lang3.builder.*;

public class IQFeedApiError {

    private int code;
    private String msg;

    @Override
    public String toString() {
        return new ToStringBuilder(this, IQFeedApiConstants.TO_STRING_BUILDER_STYLE)
            .append("code", code)
            .append("msg", msg)
            .toString();
    }

    public String getMsg() {
        return this.toString();
    }
}
