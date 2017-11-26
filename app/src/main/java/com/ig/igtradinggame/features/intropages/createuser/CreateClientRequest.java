package com.ig.igtradinggame.features.intropages.createuser;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CreateClientRequest {

    @SerializedName("userName")
    @Expose
    private String userName;

    public CreateClientRequest(String userName) {
        this.userName = userName;
    }
}
