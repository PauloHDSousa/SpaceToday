package com.PauloHDSousa.SpaceToday.rest;

public interface VolleyResponseListener {

    void onError(String message);

    void onResponse(String response);
}