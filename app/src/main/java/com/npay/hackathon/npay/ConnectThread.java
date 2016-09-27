package com.npay.hackathon.npay;


import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.Socket;

/**
 * Created by Administrator on 2016-09-09.
 */

public class ConnectThread extends Thread {


    public static String IP = "221.162.153.13";
    public static int PORT = 2426;

    private static Socket socket = null;
    private static ClientThread client;

    private static String roomName;

    private int ID = 2;
    private String name = "jaesung";

    public static String getRoomName() {
        return roomName;
    }

    public static void setRoomName(String roomName) {
        ConnectThread.roomName = roomName;
    }

    public void run() {
        Log.i("LENI-ConnectThread", "run In");
        check();
    }

    private void check(){
        if(socket!=null){ Log.i("LENI-ConnectThread", "Socket already start"); }
        else { connect(); }
    }

    private String makeMassage() {
        JSONObject obj = new JSONObject();
        try {
            JSONArray contents = new JSONArray();

            JSONObject content = new JSONObject();

            content.put("code", "R_ADD");
            content.put("R_name", roomName);
            content.put("number", ID);

            contents.put(content);
            obj.put("Message", contents);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return obj.toString();
    }

    private void connect() {
        try {
            Log.i("LENI-ConnectThread", "Client Start, Try Server connection");
            socket = new Socket(IP, PORT);

            client = new ClientThread(socket);
            client.start();

            client.sendFirst(ID);

        } catch (Exception e) {
            Log.d("LENI-ConnectThread", "Socket connection error");
        }
    }

    public void createRoom() {
        client.send(makeMassage());
    }

    public void sendFriend(String str) {
        client.send(str);
    }

    public void sendJoinOk() {
        client.sendJoinOk(ID, name);
    }

    public void sendJoinNo() {
        client.sendJoinNo(ID);
    }

   /* public static void sendMessage(String msg){
        client.send(msg);
        Log.i("LENI-ConnectThread", "sendMessage 사용");
    }*/

}
