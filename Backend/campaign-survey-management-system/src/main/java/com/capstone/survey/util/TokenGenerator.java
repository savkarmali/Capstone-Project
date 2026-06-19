package com.capstone.survey.util;

import java.util.UUID;

public final class TokenGenerator {

    private TokenGenerator() {
    }

    public static String generatePublicToken() {
        return UUID.randomUUID().toString();
    }
}