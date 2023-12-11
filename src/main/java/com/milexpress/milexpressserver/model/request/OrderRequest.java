package com.milexpress.milexpressserver.model.request;

public record OrderRequest(
        double subtotal,
        double tax,
        double total,
        double discount,
        String status,
        String userEmail
) {
}
