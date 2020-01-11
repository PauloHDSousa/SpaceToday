package com.PauloHDSousa.SpaceToday.services;

import android.content.Context;

import com.PauloHDSousa.SpaceToday.rest.JSONCallBack;
import com.PauloHDSousa.SpaceToday.rest.Rest;
import com.PauloHDSousa.SpaceToday.rest.VolleyResponseListener;
import com.PauloHDSousa.SpaceToday.services.apod.ApodModel;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

public class APOD extends BaseServices {

    Context context;
    String ServiceURL = "https://api.nasa.gov/planetary/apod?api_key=" + NASA_KEY;
    Gson gson = new Gson();

    public APOD(Context _context)
    {
        context = _context;
    }

    public void Get( String date, final JSONCallBack myCallBack) {

        if (!date.isEmpty())
            ServiceURL = ServiceURL + "&date=" + date;

        //GET
        Rest.GET_METHOD(context, ServiceURL, new VolleyResponseListener() {
            @Override
            public void onError(String message) {
                System.out.println("Error" + message);
            }

            @Override
            public void onResponse(String response) {
                ApodModel apod = gson.fromJson(response, ApodModel.class);
                myCallBack.onSuccess(apod);
            }
        });
    }

    public void PostTodaysImage() {
        //POST
        String URL_POST = " ";
        Rest.POST_METHOD(context, URL_POST, getParams(), new VolleyResponseListener() {
            @Override
            public void onError(String message) {
                System.out.println("Error" + message);
            }

            @Override
            public void onResponse(String response) {
                System.out.println("SUCCESS" + response);
            }
        });
    }

    public Map<String, String> getParams() {
        Map<String, String> params = new HashMap<String, String>();
        params.put("YOUR_KEY", "VALUE");
        return params;
    }

}
