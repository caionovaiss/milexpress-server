package com.milexpress.milexpressserver.model.response;


import java.util.List;

public record CartResponse(
        List<ProductResponse> products,
        List<Integer> quantities
) {
}
