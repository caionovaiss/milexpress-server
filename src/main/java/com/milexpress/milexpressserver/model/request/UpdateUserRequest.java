package com.milexpress.milexpressserver.model.request;

public record UpdateUserRequest(String email,
                                String name,
                                String isPremium,
                                String isPcd,
                                String age,
                                String state,
                                String allowSlang,
                                String allowRegionalExpressions,
                                String academicDegree
) {
}
