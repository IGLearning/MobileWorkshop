package com.ig.igtradinggame.storage;

import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.ig.igtradinggame.network.NetworkConfig;

import java.util.Map;

public class SharedPreferencesStorage implements ClientIDStorage, BaseUrlStorage {
    private static final String CLIENT_ID_KEY = "clientID";
    private static final String BASE_URL_KEY = "baseUrl";

    private SharedPreferences sharedPreferences;

    public SharedPreferencesStorage(@NonNull final SharedPreferences sharedPreferences) {
        this.sharedPreferences = sharedPreferences;
    }

    // FOR DEBUG ONLY!
    private void printAllKeys() {
        Map<String, ?> keys = sharedPreferences.getAll();

        for (Map.Entry<String, ?> entry : keys.entrySet()) {
            Log.d("SHAREDPREFS: ", entry.getKey() + ": " + entry.getValue().toString());
        }
    }

    public void saveClientID(@NonNull String clientId) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(CLIENT_ID_KEY, clientId);
        editor.commit();
    }

    @Nullable
    @Override
    public String loadClientId() {
        return sharedPreferences.getString(CLIENT_ID_KEY, null);
    }

    public void deleteClientId() {
        sharedPreferences.edit().remove(CLIENT_ID_KEY).commit();
    }

    @Override
    public void saveBaseURL(@NonNull String url) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(BASE_URL_KEY, url);
        editor.commit();
    }

    @Nullable
    @Override
    public String loadBaseUrl() {
        return sharedPreferences.getString(BASE_URL_KEY, NetworkConfig.EMULATOR_DEFAULT_LOCALHOST_URL);
    }
}
