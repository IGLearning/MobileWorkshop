package com.ig.igtradinggame.network;

import okhttp3.logging.HttpLoggingInterceptor;

public final class NetworkConfig {
    /**
     * Note that this is the default localhost address for
     * Android through the emulator. Remember, unless this is
     * set to something that's publicly exposed, you won't
     * be able to connect to the API.
     */
    public static final String EMULATOR_DEFAULT_LOCALHOST_URL = "http://10.0.2.2:8085/";
    public static final String LIVE_HEROKU_SERVER_URL = "https://glacial-plateau-36826.herokuapp.com/";
    public static final HttpLoggingInterceptor.Level RETROFIT_LOGGING_LEVEL = HttpLoggingInterceptor.Level.BASIC;
}
