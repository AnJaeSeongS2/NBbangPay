package com.npay.hackathon.npay;

import android.app.Dialog;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    private CustomTabLayout mTabLayout;
    private ViewPager mViewPager;

    public static Handler handler;

    Dialog dig;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.i("tag", "Main onCreate");

        mTabLayout = (CustomTabLayout) findViewById(R.id.tablayout_main);
        mTabLayout.addTab(mTabLayout.newTab().setText("결제").setIcon(R.drawable.icon_section1));
        mTabLayout.addTab(mTabLayout.newTab().setText("내 친구").setIcon(R.drawable.icon_section2));
        mTabLayout.addTab(mTabLayout.newTab().setText("설정").setIcon(R.drawable.icon_section3));
        mTabLayout.setTabTextColors(getResources().getColor(R.color.borderColor), getResources().getColor(R.color.colorPrimary));
        mTabLayout.setSelectedTabIndicatorColor(getResources().getColor(R.color.colorPrimary));
        mTabLayout.setSelectedTabIndicatorHeight(8);

        mViewPager = (ViewPager) findViewById(R.id.viewpager_main);
        final PagerAdapter adapter = new TabPagerAdapter(getSupportFragmentManager(), mTabLayout.getTabCount(), mTabLayout);
        mViewPager.setAdapter(adapter);

        // ViewPager에 tablayout 추가
        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTabLayout));

        mTabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mViewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        /*
        SocketConnect socketConnect = SocketConnect.getInstance();
        socketConnect.start();
        socketConnect.setContext(getBaseContext());
        */

        handler = new Handler(){
            @Override
            public void handleMessage(Message msg){
                super.handleMessage(msg);
                Bundle bundle = msg.getData();
                String message = bundle.getString("msg");

                try{
                    Log.i("tag", "MainAcitivity receive message");
                    if(message == null) {
                        Log.i("tag", "message is null");
                    }

                    // JSONParser jsonParser = new JSONParser();
                    // JSON데이터를 넣어 JSON Object 로 만들어 준다.
                    JSONObject jsonObject = new JSONObject(message);
                    // books의 배열을 추출
                    JSONArray array = (JSONArray) jsonObject.get("Message");
                    JSONObject object = (JSONObject) array.get(0);

                    Log.i("tag", object.toString());
                    Log.i("tag", object.get("R_name").toString());

                    if(object.get("code").toString().equals("JOIN_US")){
                        dig = new Dialog(MainActivity.this);
                        dig.setContentView(R.layout.dialog);
                        TextView textView = (TextView) dig.findViewById(R.id.dialog_text);

                        if(textView == null) {
                            Log.i("tag", "textView is null");
                        } else {
                            Log.i("tag", "textView is not null");
                        }

                        textView.setText(object.get("R_name").toString()+"에 참여하시겠습니까?");

                        TextView cancle = (TextView) dig.findViewById(R.id.dialog_cancle);
                        cancle.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                ConnectThread connectThread = new ConnectThread();
                                connectThread.start();
                                connectThread.sendJoinNo();
                                dig.dismiss();
                            }
                        });

                        TextView ok = (TextView) dig.findViewById(R.id.dialog_ok);
                        ok.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                ConnectThread connectThread = new ConnectThread();
                                connectThread.start();
                                connectThread.sendJoinOk();
                                dig.dismiss();
                            }
                        });


                        dig.setTitle("1/N 참여 신청");

                        if(dig == null) {
                            Log.i("tag", "dig is null");
                        } else {
                            Log.i("tag", "dig is not null");
                        }
                        dig.show();
                    }

                }catch  (Exception e) {
                    Log.d("LENI-MainActivity", "핸들러 애러 + " + e.getMessage());
                    e.printStackTrace();
                }
            }
        };
    }

    @Override
    protected void onPause() {
        super.onPause();
    }
}
