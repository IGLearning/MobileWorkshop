package com.ig.igtradinggame.storage;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

public interface BaseUrlStorage {
    void saveBaseURL(@NonNull String url);

    @Nullable
    String loadBaseUrl();
}
