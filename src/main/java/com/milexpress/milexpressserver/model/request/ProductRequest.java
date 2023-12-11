package com.milexpress.milexpressserver.model.request;

public record ProductRequest(String title,
                             String description,
                             double value,
                             String image,
                             String note
                             ) {
}
