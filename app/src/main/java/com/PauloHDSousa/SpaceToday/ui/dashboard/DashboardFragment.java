package com.PauloHDSousa.SpaceToday.ui.dashboard;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
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

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.PauloHDSousa.SpaceToday.R;
import com.PauloHDSousa.SpaceToday.rest.JSONCallBack;
import com.PauloHDSousa.SpaceToday.services.apod.APOD;
import com.PauloHDSousa.SpaceToday.services.ModelInterface.BaseModel;
import com.PauloHDSousa.SpaceToday.services.apod.ApodModel;
import com.PauloHDSousa.SpaceToday.utils.OnSwipeTouchListener;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
public class DashboardFragment extends Fragment {

    private DashboardViewModel dashboardViewModel;
    Context context;
    ImageView imgView;
    Date dt = new Date();
    TextView tvTitle, tvExplanation,tvDate;
    ScrollView svContent;
    Button ibTraduzir, ibZoom;
    String title, explanation;
    ProgressBar pbLoading;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        dashboardViewModel = ViewModelProviders.of(this).get(DashboardViewModel.class);
        View root = inflater.inflate(R.layout.fragment_dashboard, container, false);
        context = root.getContext();

        pbLoading = root.findViewById(R.id.pbLoading);

        ibTraduzir = root.findViewById(R.id.ibTraduzir);
        ibZoom = root.findViewById(R.id.ibZoom);

        imgView = root.findViewById(R.id.ivAPOD);

        tvTitle =  root.findViewById(R.id.tvTitle);
        tvExplanation =  root.findViewById(R.id.tvExplanation);
        tvDate =  root.findViewById(R.id.tvDate);

        svContent =  root.findViewById(R.id.svContent);

        LoadImageOfTheDay("");

        ibTraduzir.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {
            String url = "https://translate.google.com.br/?hl=pt-BR&q=" + title + " " + explanation  +"#view=home&op=translate&sl=en&tl=pt";

            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            startActivity(browserIntent);
            }
        });


        svContent.setOnTouchListener(new OnSwipeTouchListener(context) {

            public void onSwipeRight() {

                Calendar c = Calendar.getInstance();
                c.setTime(dt);
                c.add(Calendar.DATE, -1);
                dt = c.getTime();

                String sDate = new SimpleDateFormat("yyyy-MM-DD").format(dt);

                LoadImageOfTheDay(sDate);
            }

            public void onSwipeLeft() {
                Toast.makeText(context, "left +1", Toast.LENGTH_SHORT).show();
            }
        });

        return root;
    }

    void LoadImageOfTheDay(final String filterDate){
        new APOD(context).Get(filterDate, new JSONCallBack(){
            @Override
            public void onSuccess(BaseModel success) {

                ApodModel m = (ApodModel)success;

                int height = (int) getResources().getDimension(R.dimen._300sdp);
                int width = (int) getResources().getDimension(R.dimen._300sdp);

                Picasso.get().load(m.URL).resize(width, height).centerCrop().placeholder(R.drawable.progress_animation).into(imgView);


                title = m.Title;
                explanation = m.Explanation;

                tvDate.setText(filterDate);
                tvExplanation.setText(explanation);
                tvTitle.setText(title);

                svContent.smoothScrollTo(0,0);

                ContenLoaded();

            }
        });
    }

    void ContenLoaded(){

        pbLoading.setVisibility(View.GONE);

        ibTraduzir.setVisibility(View.VISIBLE);
        tvTitle.setVisibility(View.VISIBLE);
        tvExplanation.setVisibility(View.VISIBLE);
        tvDate.setVisibility(View.VISIBLE);

    }

    String getCurrentDate(){
        Calendar c = Calendar.getInstance();
        c.setTime(dt);
        dt = c.getTime();

        return  new SimpleDateFormat("DD/MM/yyyy").format(dt);
    }
}

