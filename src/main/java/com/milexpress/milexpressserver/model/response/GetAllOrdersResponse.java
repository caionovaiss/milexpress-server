package com.milexpress.milexpressserver.model.response;

import com.milexpress.milexpressserver.model.db.OrderItems;

import java.util.List;

public record GetAllOrdersResponse(
        List<OrderResponse> orderResponses,
        List<List<OrderItems>> orderItemsList
) {
}
