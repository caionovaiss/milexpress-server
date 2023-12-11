package com.milexpress.milexpressserver.model.response;

public record ProductResponse(
        Integer productId,
        String title,
        String description,
        String image,
        String note,
        double value
) {
}
