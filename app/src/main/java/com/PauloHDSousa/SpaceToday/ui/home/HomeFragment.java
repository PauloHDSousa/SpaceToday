package com.PauloHDSousa.SpaceToday.ui.home;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.PauloHDSousa.SpaceToday.R;
import com.PauloHDSousa.SpaceToday.rest.JSONCallBack;
import com.PauloHDSousa.SpaceToday.services.ModelInterface.BaseModel;
import com.PauloHDSousa.SpaceToday.services.apod.APOD;
import com.PauloHDSousa.SpaceToday.services.apod.ApodModel;
import com.PauloHDSousa.SpaceToday.services.neo.NEO;
import com.PauloHDSousa.SpaceToday.services.neo.NeoModel;
import com.PauloHDSousa.SpaceToday.ui.dashboard.DashboardViewModel;
import com.PauloHDSousa.SpaceToday.utils.OnSwipeTouchListener;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    Context context;
    ImageView imgView;
    Date dt = new Date();
    TextView tvTitle, tvExplanation, tvNasaTitle;
    ScrollView svContent;
    Button ibTraduzir, ibZoom;
    String title, explanation, fullImageURL;
    ProgressBar pbLoading;
    String language;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel = ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        context = root.getContext();

        pbLoading = root.findViewById(R.id.pbLoading);

        ibTraduzir = root.findViewById(R.id.ibTraduzir);
        ibZoom = root.findViewById(R.id.ibZoom);

        imgView = root.findViewById(R.id.ivAPOD);

        tvTitle =  root.findViewById(R.id.tvTitle);
        tvExplanation =  root.findViewById(R.id.tvExplanation);
        tvNasaTitle =  root.findViewById(R.id.tvNasaTitle);

        svContent =  root.findViewById(R.id.svContent);

        language = Locale.getDefault().getLanguage();



        //Initialize date
        Calendar c = Calendar.getInstance();
        c.setTime(dt);
        dt = c.getTime();

        LoadImageOfTheDay(new SimpleDateFormat("yyyy-MM-DD").format(dt));

        ibTraduzir.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {
                String url = "https://translate.google.com.br/?hl="+language+"&q=" + title + " " + explanation  +"#view=home&op=translate&sl=en&tl="+language;

                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                startActivity(browserIntent);
            }
        });

        ibZoom.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(fullImageURL));
                startActivity(browserIntent);
            }
        });


        svContent.setOnTouchListener(new OnSwipeTouchListener(context) {

            public void onSwipeRight() {
                String sDate = getQueryDate(true);
                LoadImageOfTheDay(sDate);
            }

            public void onSwipeLeft() {

                //Wait a minute, Doc. Ah... Are you telling me that you built a time machine... out of a DeLorean?
                if(isToday()) {
                    return;
                }

                String sDate = getQueryDate(false);
                LoadImageOfTheDay(sDate);
            }
        });

        return root;
    }

    void LoadImageOfTheDay(final String filterDate){
        new APOD(context).Get(filterDate, new JSONCallBack(){
            @Override
            public void onSuccess(BaseModel success) {

                ApodModel m = (ApodModel)success;

                int height = (int) getResources().getDimension(R.dimen._400sdp);
                int width = (int) getResources().getDimension(R.dimen._400sdp);

                Picasso.get().load(m.HDURL).resize(width, height).centerCrop().placeholder(R.drawable.progress_animation).into(imgView);

                fullImageURL = m.HDURL;

                title = m.Title;
                explanation = m.Explanation;

                tvExplanation.setText(explanation);
                tvTitle.setText(getFormatedDate() + " - " +title);

                svContent.smoothScrollTo(0,0);

                ContenLoaded();
            }
        });
    }

    void ContenLoaded(){
        pbLoading.setVisibility(View.GONE);

        ibZoom.setVisibility(View.VISIBLE);
        tvTitle.setVisibility(View.VISIBLE);
        tvExplanation.setVisibility(View.VISIBLE);
        tvNasaTitle.setVisibility(View.VISIBLE);

        if(!language.equals("en")){
            ibTraduzir.setVisibility(View.VISIBLE);
        }
    }

    boolean isToday(){
        Calendar cal1 = Calendar.getInstance();
        Calendar cal2 = Calendar.getInstance();
        cal1.setTime(dt);
        cal2.setTime(new Date());
       return cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR) && cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR);
    }

    String getFormatedDate(){
        Calendar c = Calendar.getInstance();
        c.setTime(dt);
        dt = c.getTime();

        return new SimpleDateFormat("DD/MM/yyyy").format(dt);
    }

    String getQueryDate(boolean removeDay){
        Calendar c = Calendar.getInstance();
        c.setTime(dt);
        int days = 1;

        if(removeDay)
            days = -1;

        c.add(Calendar.DATE, days);
        dt = c.getTime();

        return new SimpleDateFormat("yyyy-MM-DD").format(dt);
    }
}