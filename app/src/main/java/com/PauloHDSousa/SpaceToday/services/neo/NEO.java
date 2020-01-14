package com.PauloHDSousa.SpaceToday.services.neo;

import android.content.Context;
import android.widget.Toast;

import com.PauloHDSousa.SpaceToday.rest.JSONCallBack;
import com.PauloHDSousa.SpaceToday.rest.Rest;
import com.PauloHDSousa.SpaceToday.rest.VolleyResponseListener;
import com.PauloHDSousa.SpaceToday.services.BaseServices;
import com.PauloHDSousa.SpaceToday.services.apod.ApodModel;
import com.google.gson.Gson;


public class NEO  extends BaseServices {

    Context context;
    String ServiceURL= "https://api.nasa.gov/neo/rest/v1/feed?start_date=2015-09-07&end_date=2015-09-08&api_key=" + NASA_KEY;
    Gson gson = new Gson();

    public NEO(Context _context)
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
                Toast.makeText(context,message, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(String response) {
                NeoModel apod = gson.fromJson(response, NeoModel.class);
                myCallBack.onSuccess(apod);
            }
        });
    }
}
