package com.npay.hackathon.npay;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.lucasr.twowayview.TwoWayView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016-09-08.
 */

public class Tab2 extends Fragment {

    private View rootView;

    private ImageView mMyImage;
    private TextView mMyText;

    private RecyclerView mRecyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.tab2, container, false);

        mMyImage = (ImageView) rootView.findViewById(R.id.my_image);
        mMyText = (TextView) rootView.findViewById(R.id.my_text);

        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerview);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setAdapter(new ItemUserAdapter(getContext(), getUserData(), getActivity()));


        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    public List<User> getUserData() {
        List<User> list = new ArrayList<>();
        list.add(new User(R.drawable.profile2, "김태우", false, 1, false, 1));
        list.add(new User(R.drawable.profile3, "박희진", false, 1, false, 1));
        list.add(new User(R.drawable.profile4, "진나리", false, 1, false, 1));
        list.add(new User(R.drawable.profile2, "김태우", false, 1, false, 1));
        list.add(new User(R.drawable.profile3, "박희진", false, 1, false, 1));
        list.add(new User(R.drawable.profile4, "진나리", false, 1, false, 1));
        list.add(new User(R.drawable.profile2, "김태우", false, 1, false, 1));
        list.add(new User(R.drawable.profile3, "박희진", false, 1, false, 1));
        list.add(new User(R.drawable.profile4, "진나리", false, 1, false, 1));

        return list;
    }

}
