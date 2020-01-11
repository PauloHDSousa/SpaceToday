package com.PauloHDSousa.SpaceToday.ui.dashboard;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.PauloHDSousa.SpaceToday.MainActivity;
import com.PauloHDSousa.SpaceToday.R;
import com.PauloHDSousa.SpaceToday.rest.JSONCallBack;
import com.PauloHDSousa.SpaceToday.services.APOD;
import com.PauloHDSousa.SpaceToday.services.ModelInterface.BaseModel;
import com.PauloHDSousa.SpaceToday.services.apod.ApodModel;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.SerializedName;
import com.squareup.picasso.Picasso;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

class Post {

    @SerializedName("date")
    Date dateCreated;

    String copyright;
    String explanation;
    String url;
    String title;
}

public class DashboardFragment extends Fragment {

    private DashboardViewModel dashboardViewModel;
    private Context context;
    Long date = 0l;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        dashboardViewModel = ViewModelProviders.of(this).get(DashboardViewModel.class);
        View root = inflater.inflate(R.layout.fragment_dashboard, container, false);

        context = root.getContext();

        final ImageView ivAPOD = root.findViewById(R.id.ivAPOD);
        final CalendarView cv = root.findViewById(R.id.cvDate);

        cv.setOnDateChangeListener(new CalendarView.OnDateChangeListener(){
            public void onSelectedDayChange(CalendarView view, int year, int month, int day) {

                    date = cv.getDate();

                    month++;

                    String sMonth = String.valueOf(month);
                    String sDay = String.valueOf(day);

                    if(month < 10)
                        sMonth = "0" + month;


                    if(day < 10)
                        sDay  = "0" + day ;

                    String date = year+"-"+sMonth+"-"+sDay;


                    new APOD(context).Get(date, new JSONCallBack(){
                        @Override
                        public void onSuccess(BaseModel success) {
                            // no errors
                            ApodModel m = (ApodModel)success;

                            Picasso.get().load(m.URL).into(ivAPOD);
                        }
                    });
            }
        });

        return root;
    }

}