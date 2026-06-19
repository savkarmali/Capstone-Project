package com.capstone.survey.util;

public final class ApiConstants {

    private ApiConstants() {
    }

    public static final String API_BASE = "/api";

    public static final String AUTH_BASE = API_BASE + "/auth";

    public static final String ADMIN_BASE = API_BASE + "/admin";

    public static final String PUBLIC_BASE = API_BASE + "/public";

    public static final String SURVEYS_BASE = ADMIN_BASE + "/surveys";

    public static final String QUESTIONS_BASE = ADMIN_BASE + "/questions";

    public static final String RESPONSES_BASE = ADMIN_BASE + "/responses";

    public static final String ANALYTICS_BASE = ADMIN_BASE + "/analytics";

    public static final String EXPORTS_BASE = ADMIN_BASE + "/exports";

    public static final int MAX_QUESTIONS_PER_SURVEY = 6;

    public static final String TOKEN_TYPE_BEARER = "Bearer";
}