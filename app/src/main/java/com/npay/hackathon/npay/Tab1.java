package com.npay.hackathon.npay;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.andexert.library.RippleView;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jaekwon.loopviewpagerlayout.lib.LoopViewPagerLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.lucasr.twowayview.TwoWayView;

import java.io.IOError;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * Created by Administrator on 2016-09-08.
 */

public class Tab1 extends Fragment {

    private View rootView;

    private ViewPager mViewPager;
    private ImageView mArrowRight;
    private ImageView mArrowLeft;

    private RelativeLayout mButtonPay;

    private CustomTabLayout mCustomTabLayout;

    private EditText mEditText;

    private ApiService mApiService;

    private ConnectThread connectThread;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.i("tag", "onCreateView()");
        rootView = inflater.inflate(R.layout.tab1, container, false);

        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(mApiService.API_URL)
                .build();
        mApiService = retrofit.create(ApiService.class);

        connectThread = new ConnectThread();
        connectThread.start();


        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mViewPager = (ViewPager) rootView.findViewById(R.id.viewpager);
        ItemCardAdapter itemCardAdapter = new ItemCardAdapter(getContext(), getCardData());
        mViewPager.setAdapter(itemCardAdapter);

        mArrowRight = (ImageView) rootView.findViewById(R.id.arrow_right);
        mArrowLeft = (ImageView) rootView.findViewById(R.id.arrow_left);

        mArrowRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewPager.setCurrentItem(Math.min(mViewPager.getCurrentItem()+1,getCardData().size()));
            }
        });

        mArrowLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewPager.setCurrentItem(Math.max(mViewPager.getCurrentItem()-1,0));
            }
        });


        RelativeLayout buttonPay = (RelativeLayout) rootView.findViewById(R.id.button_pay);
        buttonPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConnectThread.setRoomName(mEditText.getText().toString());
                connectThread.createRoom();

                Intent intent = new Intent(getContext(), Pay1Activity.class);
                startActivity(intent);
            }
        });

        mEditText = (EditText) rootView.findViewById(R.id.edittext_room_name);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    public List<Card> getCardData() {
        List<Card> list = new ArrayList<>();
        list.add(new Card(R.drawable.card_kb, "KB국민카드", "9436-4505-****-5141", "03/20"));
        list.add(new Card(R.drawable.card_samsung, "삼성카드", "2348-9865-****-9514", "03/01"));
        list.add(new Card(R.drawable.card_hana, "하나카드", "6140-5143-****-6530", "01/24"));

        return list;
    }

}
