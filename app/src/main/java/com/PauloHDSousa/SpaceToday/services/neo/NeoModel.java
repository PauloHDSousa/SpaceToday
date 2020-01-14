package com.PauloHDSousa.SpaceToday.services.neo;

import com.PauloHDSousa.SpaceToday.services.ModelInterface.BaseModel;
import com.google.gson.annotations.SerializedName;

import java.util.HashMap;
import java.util.Map;

class Links {
    public String next;
    public String prev;
    public String self;
}

public class NeoModel  implements BaseModel {
    public Links links;
    public  int element_count;
    public  NearEarthObjectsModel near_earth_objects;

    Map<String, Object> a;


    HashMap<String, Object>[] b;


    HashMap<String, Object> c;


    Map<String, Object>[] d;

    Map<String, Object> e;

    public  Object [] f;


    public static class NearEarthObjectsModel {


        HashMap<String, Object> a;


        HashMap<String, Object> b;


        public Map<String, Object>[] c;

        public Map<String, Object> d;

        @SerializedName("2015-09-08")
        public Object[] e;
    }

    public static  class AsteroidModel {



        public String id;

    /*
    public float absolute_magnitude_h;

    public ApproachDataModel[] close_approach_data;
    public DiameterModel estimated_diameter;

    public boolean is_potentially_hazardous_asteroid;
    public boolean is_sentry_object;

    @SerializedName("name")
    public String Name;

    public String nasa_jpl_url;
    public String neo_reference_id;*/
    }
}
