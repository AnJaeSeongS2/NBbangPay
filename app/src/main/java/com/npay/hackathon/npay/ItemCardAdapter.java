package com.npay.hackathon.npay;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;


/**
 * Created by Administrator on 2016-09-08.
 */

public class ItemCardAdapter extends PagerAdapter {

    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private List<Card> mList;

    public ItemCardAdapter(Context context, List<Card> mList) {
        super();
        this.mContext = context;
        this.mList = mList;
        mLayoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        Log.i("tag", "instantiateItem");

        View view = null;
        if(position < 0 || mList.size() <= position) {

        } else {
            view = mLayoutInflater.inflate(R.layout.item_card, null);

            Card item = mList.get(position);

            ImageView cardImage = (ImageView) view.findViewById(R.id.card_image);
            TextView cardName = (TextView) view.findViewById(R.id.card_name);
            TextView cardNumber = (TextView) view.findViewById(R.id.card_number);
            TextView cardValid = (TextView) view.findViewById(R.id.card_valid);

            final ProgressBar progressBar = (ProgressBar) view.findViewById(R.id.loading);
            progressBar.setVisibility(View.VISIBLE);
            Log.i("tag", ""+item.getCardImage());
            Picasso.with(mContext).load(item.getCardImage()).into(cardImage, new Callback() {
                @Override
                public void onSuccess() {
                    progressBar.setVisibility(View.GONE);
                }

                @Override
                public void onError() {

                }
            });

            cardName.setText(item.getCardName());
            cardNumber.setText(item.getCardNumber());
            cardValid.setText(item.getCardValid());
        }

        ((ViewPager) container).addView(view, position);

        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ((ViewPager)container).removeView((View) object);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view==object;
    }
}
