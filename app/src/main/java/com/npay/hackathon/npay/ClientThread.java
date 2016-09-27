package com.npay.hackathon.npay;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

/**
 * Created by Administrator on 2016-09-09.
 */

public class ClientThread extends Thread {

    private BufferedReader reader;
    private BufferedWriter writer;

    // 안쓰는거 같지만 안쓰는게 아니다.. 이게없으면 안된다... 이상하다..
    private Socket client;
    private Handler handler;


    public ClientThread(Socket client) {
        try {
            this.client = client;
            // 연결된 소켓으로부터 대화를 나눌 스트림을 얻음
            reader = new BufferedReader(new InputStreamReader(client.getInputStream()));
            writer = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
        } catch (IOException e) { Log.i("LENI-ClientThread", "socket client error"); }
    }

    // 보내기
    public void send(String message) {
        try {
            Log.i("LENI-ClientThread", "send 메소드 들어옴 date : "+message);
            writer.write(message + "\n");
            writer.flush();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void sendFirst(int ID) {

        Log.i("tag", "sendFirst");

        JSONObject obj = new JSONObject();
        try {
            JSONArray contents = new JSONArray();

            JSONObject content = new JSONObject();

            content.put("code", "LOGIN");
            content.put("number", ID);

            contents.put(content);
            obj.put("Message", contents);


            writer.write(obj.toString() + "\n");
            writer.flush();
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void sendJoinOk(int ID, String name) {
        JSONObject obj = new JSONObject();
        try {
            JSONArray contents = new JSONArray();

            JSONObject content = new JSONObject();

            content.put("code", "JOIN_US_OK");
            content.put("number", ID);
            content.put("name", name);

            contents.put(content);
            obj.put("Message", contents);

            Log.i("tag", "sendJOINOK " + obj.toString());
            writer.write(obj.toString() + "\n");
            writer.flush();
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void sendJoinNo(int ID) {
        JSONObject obj = new JSONObject();
        try {
            JSONArray contents = new JSONArray();

            JSONObject content = new JSONObject();

            content.put("code", "JOIN_US_NO");

            contents.put(content);
            obj.put("Message", contents);

            writer.write(obj.toString() + "\n");
            writer.flush();
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    // 받기
    public String listen() {

        String msg = null;

        try {
            while (true) {
                Log.i("LENI-ClientThread", "Listen 메소드 들어옴 : "+msg);
                msg = reader.readLine();
                checkCommand(msg);
            }
        } catch (IOException e) { e.printStackTrace(); }
        return msg;
    }

    // 핸들러 분기점 주기
    public void checkCommand(String msg) {
        Log.i("tag", "checkCommand");

        String message = msg;
        try {

            //JSON 사용하여 받은 메시지 커맨드에 따른 클래스로 핸들러 메시지 보내기
            JSONObject jsonObject = new JSONObject(msg);
            Message m = new Message();
            Bundle bundle = new Bundle();
            bundle.putString("msg", message);
            m.setData(bundle);
            JSONArray jsonArray = (JSONArray) jsonObject.get("Message");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject object = (JSONObject) jsonArray.get(i);

                // MainActivity에 있는 handler로 받아온 메시지를 전송한다.
                if(object.get("code").toString().equals("JOIN_US")) {
                    Log.i("LENI-ClientThread", "JOIN_US");
                    MainActivity.handler.sendMessage(m);
                    Pay1Activity.handler.sendMessage(m);
                    Pay2Activity.handlerPay2.sendMessage(m);
                } else if(object.get("code").toString().equals("P_LIST_ADD")) {
                    Log.i("LENI-ClientThread", "P_LIST_ADD");
                    Pay2Activity.handlerPay2.sendMessage(m);
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void run() {
        super.run();
        listen();
    }

}
