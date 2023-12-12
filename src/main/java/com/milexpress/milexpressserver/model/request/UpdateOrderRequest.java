package com.milexpress.milexpressserver.model.request;

public record UpdateOrderRequest(
        Integer orderId,
        String status
) {
}
