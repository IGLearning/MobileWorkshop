package com.ig.igtradinggame.storage;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

public interface ClientIDStorage {
    void saveClientID(@NonNull final String clientId);
    @Nullable String loadClientId();
    void deleteClientId();
}
