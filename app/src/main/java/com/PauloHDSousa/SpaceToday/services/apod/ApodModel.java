package com.PauloHDSousa.SpaceToday.services.apod;

import com.PauloHDSousa.SpaceToday.services.ModelInterface.BaseModel;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class ApodModel  implements BaseModel {

    @SerializedName("date")
    public Date Date;

    @SerializedName("explanation")
    public String Explanation;

    @SerializedName("url")
    public  String URL;

    @SerializedName("title")
    public  String Title;

    @SerializedName("hdurl")
    public  String HDURL;
}
