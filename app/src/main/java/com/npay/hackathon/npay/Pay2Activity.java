package com.npay.hackathon.npay;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class Pay2Activity extends AppCompatActivity {

    private int totalCnt;

    private RecyclerView mRecyclerView;
    private ItemUserAdapter mItemUserAdapter;
    List<User> mList = new ArrayList<>();

    private TextView mTextViewCnt;
    private ImageView mButtonNext;

    public static Handler handlerPay2;

    Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {

            mTextViewCnt.setText(""+mList.size()+" / " + totalCnt);
            if(mList.size() == totalCnt) {
                mButtonNext.setImageResource(R.drawable.arrow_right_allok);
            }

            return false;
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay2);

        Intent intent = getIntent();
        totalCnt = intent.getIntExtra("totalCnt", 0);

        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getBaseContext()));
        mItemUserAdapter = new ItemUserAdapter(getBaseContext(), mList, this);
        mRecyclerView.setAdapter(mItemUserAdapter);

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    Message message = handler.obtainMessage();
                    handler.sendMessage(message);
                    Thread.sleep(100);
                } catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
        thread.run();

        mTextViewCnt = (TextView) findViewById(R.id.pay2_people_cnt);
        mButtonNext = (ImageView) findViewById(R.id.button_next);



        handlerPay2 = new Handler(){
            @Override
            public void handleMessage(Message msg){
                super.handleMessage(msg);
                Bundle bundle = msg.getData();
                String message = bundle.getString("msg");

                try{
                    Log.i("tag", "receive message + " + message);

                    // JSONParser jsonParser = new JSONParser();
                    // JSON데이터를 넣어 JSON Object 로 만들어 준다.
                    JSONObject jsonObject = new JSONObject(message);
                    // books의 배열을 추출
                    JSONArray array = (JSONArray) jsonObject.get("Message");
                    JSONObject object = (JSONObject) array.get(0);

                    if(object.get("code").toString().equals("P_LIST_ADD")){
                        User user = new User(R.drawable.profile1, object.get("P_name").toString(), false, 2, false, object.getInt("number"));
                        mList.add(user);
                        mItemUserAdapter.notifyDataSetChanged();
                        mTextViewCnt.setText(""+mList.size()+" / " + totalCnt);
                        if(mList.size() == totalCnt) {
                            mButtonNext.setImageResource(R.drawable.arrow_right_allok);
                        }
                    }
                }catch  (Exception e) {
                    Log.d("LENI-MainActivity", "핸들러 애러");
                }
            }
        };
    }
}
