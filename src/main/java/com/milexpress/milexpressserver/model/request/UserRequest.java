package com.milexpress.milexpressserver.model.request;

import com.milexpress.milexpressserver.model.db.UserRole;

public record UserRequest(
        String email,
        String password,
        String name,
        UserRole role
        ) {
}
