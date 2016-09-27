package com.npay.hackathon.npay;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.npay.hackathon.npay.Widget.SmoothCheckBox;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import xyz.hanks.library.SmallBang;
import xyz.hanks.library.SmallBangListener;

/**
 * Created by Administrator on 2016-09-08.
 */

public class ItemUserAdapter extends RecyclerView.Adapter<ItemUserAdapter.ViewHolder> {

    Context mContext;
    List<User> mList;

    SmallBang mSmallBang;

    public ItemUserAdapter(Context context, List<User> mList, Activity activity) {
        this.mContext = context;
        this.mList = mList;

        mSmallBang = SmallBang.attach2Window(activity);
        mSmallBang.setColors(new int[] {mContext.getResources().getColor(R.color.emerald), mContext.getResources().getColor(R.color.emerald)});
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_user, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        Picasso.with(mContext).load(mList.get(position).getUserImage()).into(holder.userImage);
        holder.userName.setText(mList.get(position).getUserName());
        final User item = mList.get(position);

        if(item.getStep()!=3) {
            holder.userImageOk.setVisibility(View.GONE);
        } else {
            Picasso.with(mContext).load(R.drawable.ok).into(holder.userImageOk);
        }

        if(item.getStep()!=2) {
            holder.checkBox.setVisibility(View.GONE);
        } else if(item.getStep()==2) {
            holder.checkBox.setVisibility(View.VISIBLE);
            holder.checkBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.i("tag", mList.get(position).toString());
                    if(item.isChecked()) {

                        mList.get(position).setChecked(unCheck((ImageView)v));
                    } else {
                        mList.get(position).setChecked(Check((ImageView)v));
                    }
                }
            });
        }
    }

    private boolean unCheck(ImageView image) {
        image.setImageDrawable(mContext.getResources().getDrawable(R.drawable.icon_unchecked));
        return false;
    }
    private boolean Check(ImageView image) {
        image.setImageDrawable(mContext.getResources().getDrawable(R.drawable.icon_checked));
        mSmallBang.bang(image, 40, new SmallBangListener() {
            @Override
            public void onAnimationStart() {
            }
            @Override
            public void onAnimationEnd() {
            }
        });

        return true;
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public List<User> getSelectedUser() {
        List<User> list = new ArrayList<>();
        for(User u : mList) {
            if(u.isChecked()) {
                list.add(u);
            }
        }
        return list;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView userImage;
        private TextView userName;
        private ImageView userImageOk;
        private ImageView checkBox;

        public ViewHolder(View itemView) {
            super(itemView);

            userImage = (ImageView) itemView.findViewById(R.id.user_image);
            userName = (TextView) itemView.findViewById(R.id.user_name);
            userImageOk = (ImageView) itemView.findViewById(R.id.user_image_ok);
            checkBox = (ImageView) itemView.findViewById(R.id.checkbox);
        }
    }
}
