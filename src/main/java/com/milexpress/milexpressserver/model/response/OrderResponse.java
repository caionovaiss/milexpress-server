package com.milexpress.milexpressserver.model.response;

import java.time.Instant;

public record OrderResponse(Integer orderId,
                            String status,
                            double subtotal,
                            double tax,
                            double total,
                            double discount,
                            Instant date

) {
}
