package com.milexpress.milexpressserver.model.request;

public record RateOrderRequest(
        Integer orderId,
        Integer starRating,
        String note
) {
}
