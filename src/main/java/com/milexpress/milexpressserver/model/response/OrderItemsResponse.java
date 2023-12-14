package com.milexpress.milexpressserver.model.response;

import com.milexpress.milexpressserver.model.db.OrderItems;

import java.util.List;

public record OrderItemsResponse(
        List<OrderItems> orderItemsList,
        OrderResponse orderResponse

) {
}
