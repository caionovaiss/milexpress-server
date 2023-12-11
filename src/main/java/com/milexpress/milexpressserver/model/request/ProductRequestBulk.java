package com.milexpress.milexpressserver.model.request;

import java.util.List;

public record ProductRequestBulk(
        List<ProductRequest> products
) {
}
