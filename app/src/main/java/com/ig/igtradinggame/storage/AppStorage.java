package com.ig.igtradinggame.storage;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ig.igtradinggame.models.MarketModel;
import com.ig.igtradinggame.network.NetworkConfig;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.annotations.NonNull;

/**
 * NOTE: THIS IS NOT THREAD SAFE! JUST FOR DEMO PURPOSES WITH SHAREDPREFS.
 * DO NOT USE ASYNCHRONOUSLY!
 */
public class AppStorage {
    private static final String CLIENT_ID_KEY = "clientID";
    private static final String BASE_URL_KEY = "baseUrl";
    private static final String MARKET_IDS_KEY = "marketIds";

    private static AppStorage instance = null;
    private SharedPreferences sharedPreferences;

    private AppStorage(Context context) {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public static AppStorage getInstance(Context context) {
        if (instance == null) {
            instance = new AppStorage(context.getApplicationContext());
        }

        return instance;
    }

    public String loadClientId() {
        return sharedPreferences.getString(CLIENT_ID_KEY, null);
    }

    public void saveClientId(@NonNull String clientId) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(CLIENT_ID_KEY, clientId);
        editor.commit();
    }

    public void deleteClientID() {
        sharedPreferences.edit().remove(CLIENT_ID_KEY).commit();
    }

    public void saveBaseURL(@NonNull String url) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(BASE_URL_KEY, url);
        editor.commit();
    }

    public String loadBaseUrl() {
        return sharedPreferences.getString(BASE_URL_KEY, NetworkConfig.EMULATOR_DEFAULT_LOCALHOST_URL);
    }

    public void saveMarketNames(List<MarketModel> marketModels) {
        Map<String, String> valueMap = new HashMap<>();

        for (MarketModel model : marketModels) {
            valueMap.put(model.getMarketId(), model.getMarketName());
        }

        Gson gson = new Gson();
        String json = gson.toJson(valueMap);

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(MARKET_IDS_KEY, json);
        editor.commit();
    }

    public Map<String, String> loadMarketNames() {
        String storedHashMapString = sharedPreferences.getString(MARKET_IDS_KEY, "");
        java.lang.reflect.Type type = new TypeToken<HashMap<String, String>>() {
        }.getType();

        Gson gson = new Gson();
        return gson.fromJson(storedHashMapString, type);
    }
}
