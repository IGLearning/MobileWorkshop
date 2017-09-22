package com.ig.igtradinggame.storage;

import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.Map;

import static android.content.ContentValues.TAG;

public class SharedPreferencesStorage implements ClientIDStorage {
    private static final String CLIENT_ID_KEY = "clientID";
    private SharedPreferences sharedPreferences;

    public SharedPreferencesStorage(@NonNull final SharedPreferences sharedPreferences) {
        this.sharedPreferences = sharedPreferences;

        Log.d(TAG, "SharedPreferencesStorage: Initialised object.");
        printAllKeys();

    }

    private void printAllKeys() {
        Map<String, ?> keys = sharedPreferences.getAll();

        for (Map.Entry<String, ?> entry : keys.entrySet()) {
            Log.d("SHAREDPREFS: ", entry.getKey() + ": " + entry.getValue().toString());
        }
    }

    public void saveClientID(@NonNull String clientId) {
        Log.d(TAG, "saveClientID: SHARED");
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(CLIENT_ID_KEY, clientId);
        editor.commit();
        printAllKeys();
    }

    @Nullable
    public String loadClientId() {
        Log.d(TAG, "loadClientId: SHARED");
        printAllKeys();
        return sharedPreferences.getString(CLIENT_ID_KEY, null);
    }

    public void deleteClientId() {
        sharedPreferences.edit().remove(CLIENT_ID_KEY).commit();
    }
}
