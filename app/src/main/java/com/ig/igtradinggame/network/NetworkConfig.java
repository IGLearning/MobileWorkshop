package com.ig.igtradinggame.network;

import okhttp3.logging.HttpLoggingInterceptor;

public final class NetworkConfig {
    /**
     * Note that this is the default localhost address for
     * Android through the emulator. Remember, unless this is
     * set to something that's publicly exposed, you won't
     * be able to connect to the API.
     */
    public static final String API_BASE_URL = "http://10.0.2.2:8085/";
    public static final HttpLoggingInterceptor.Level RETROFIT_LOGGING_LEVEL = HttpLoggingInterceptor.Level.BODY;
}
