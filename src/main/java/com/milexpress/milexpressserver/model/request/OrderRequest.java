package com.milexpress.milexpressserver.model.request;

import com.milexpress.milexpressserver.model.db.Address;

import java.util.Map;

public record OrderRequest(String userEmail, Map<Integer, Integer> products, Address userAddress) {
}
