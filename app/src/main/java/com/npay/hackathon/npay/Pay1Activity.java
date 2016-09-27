package com.npay.hackathon.npay;

import android.app.Dialog;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Pay1Activity extends AppCompatActivity {

    private RecyclerView mRecyclerViewRecent;
    private RecyclerView mRecyclerViewOrder;

    private ImageView mButtonNext;

    private ItemUserAdapter itemUserAdapter1;
    private ItemUserAdapter itemUserAdapter2;

    public static Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay1);

        mRecyclerViewRecent = (RecyclerView) findViewById(R.id.recyclerview_recent);
        mRecyclerViewRecent.setLayoutManager(new LinearLayoutManager(getBaseContext()));
        itemUserAdapter1 = new ItemUserAdapter(getBaseContext(), getUserDataRecent(), this);
        mRecyclerViewRecent.setAdapter(itemUserAdapter1);

        mRecyclerViewOrder = (RecyclerView) findViewById(R.id.recyclerview_order);
        mRecyclerViewOrder.setLayoutManager(new LinearLayoutManager(getBaseContext()));
        itemUserAdapter2 = new ItemUserAdapter(getBaseContext(), getUserDataOrder(), this);
        mRecyclerViewOrder.setAdapter(itemUserAdapter2);

        mButtonNext = (ImageView) findViewById(R.id.button_next);
        mButtonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<User> list =  calPeopleCnt(itemUserAdapter2);

                ConnectThread connectThread = new ConnectThread();
                connectThread.start();

                try {

                    for(User u : list) {

                        JSONObject obj = new JSONObject();

                        JSONArray contents = new JSONArray();


                        JSONObject content = new JSONObject();

                        content.put("code", "P_ADD");
                        content.put("number", u.getPk());
                        contents.put(content);
                        obj.put("Message", contents);

                        connectThread.sendFriend(obj.toString());
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                Intent intent = new Intent(Pay1Activity.this, Pay2Activity.class);
                intent.putExtra("totalCnt", list.size());
                startActivity(intent);
            }
        });

        handler = new Handler(){
            @Override
            public void handleMessage(Message msg){
                super.handleMessage(msg);
                Bundle bundle = msg.getData();
                String message = bundle.getString("msg");

                try{
                    Log.i("tag", "receive message");




                    // JSONParser jsonParser = new JSONParser();
                    // JSON데이터를 넣어 JSON Object 로 만들어 준다.
                    JSONObject jsonObject = new JSONObject(message);
                    // books의 배열을 추출
                    JSONArray array = (JSONArray) jsonObject.get("Message");
                    JSONObject object = (JSONObject) array.get(0);

                    if(object.get("code").toString().equals("JOIN_US")){
                        Dialog dig = new Dialog(getBaseContext());
                        dig.setContentView(R.layout.dialog);
                        TextView textView = (TextView) dig.findViewById(R.id.dialog_text);
                        textView.setText(((JSONObject) array.get(0)).toString()+"에 참여하시겠습니까?");
                        dig.setTitle("1/N 참여 신청");
                        dig.show();
                    }
                }catch  (Exception e) {
                    Log.d("LENI-MainActivity", "핸들러 애러");
                }
            }
        };

    }

    public List<User> getUserDataRecent() {
        List<User> list = new ArrayList<>();

        /*
        list.add(new User(R.drawable.profile2, "김태우", false, 2));
        list.add(new User(R.drawable.profile3, "박희진", false, 2));
        list.add(new User(R.drawable.profile4, "진나리", false, 2));
        list.add(new User(R.drawable.profile2, "김태우", false, 2));
        */

        return list;
    }

    public List<User> getUserDataOrder() {
        List<User> list = new ArrayList<>();

        list.add(new User(R.drawable.profile3, "chaemin", false, 2, false, 1));
        //list.add(new User(R.drawable.profile2, "Jaesung", false, 2, false, 2));
        list.add(new User(R.drawable.profile4, "jaekwon", false, 2, false, 3));
        list.add(new User(R.drawable.profile1, "dahae", false, 2, false, 4));

        return list;
    }

    public List<User> calPeopleCnt(ItemUserAdapter itemUserAdapter) {
        return itemUserAdapter.getSelectedUser();
    }
}
