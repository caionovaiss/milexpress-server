package com.milexpress.milexpressserver.model.response;

public record OrderResponse(Integer orderId,
                            String status,
                            double subtotal,
                            double tax,
                            double total,
                            double discount
) {
}
