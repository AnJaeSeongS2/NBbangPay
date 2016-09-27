package com.npay.hackathon.npay;

import android.app.Dialog;
import android.content.Context;
import android.util.Log;
import android.widget.TextView;

import org.json.JSONObject;
import org.json.JSONStringer;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Created by Administrator on 2016-09-08.
 */

public class SocketConnect extends Thread {

    public final static String hostname = "221.162.153.13";
    public final static int port = 2426;

    private static SocketConnect socketConnect;

    public Socket socket;
    public ObjectOutputStream out;
    public ObjectInputStream in;
    public String message;

    public Context mContext;

    public static SocketConnect getInstance() {
        if(socketConnect==null) {
            socketConnect = new SocketConnect();
        }
        return socketConnect;
    }

    public void setContext(Context context) {
        mContext = context;
    }

    public Context getContext() {
        return mContext;
    }

    private SocketConnect(){
    }

    public void run(){
        try{
            message = " ";
            //1. creating a socket to connect to the server
            socket = new Socket(hostname, port);
            //2. get Input and Output streams
            Log.i("tag", "out is create");
            out = new ObjectOutputStream(socket.getOutputStream());
            out.flush();
            in = new ObjectInputStream(socket.getInputStream());
            //3: Communicating with the server
            do{
                try{
                    message = (String)in.readObject();
                    Log.i("tag", "server>" + message);

                    readMessage(message);
                }
                catch(ClassNotFoundException classNot){
                    Log.i("tag", "data received in unknown format");
                }
            }while(!message.equals("bye"));
        }
        catch(UnknownHostException unknownHost){
            System.err.println("You are trying to connect to an unknown host!");
        }
        catch(IOException ioException){
            ioException.printStackTrace();
        }
        finally{
            //4: Closing connection
            try{
                Log.i("tag", "in out close");
                in.close();
                out.close();
                socket.close();
            }
            catch(IOException ioException){
                ioException.printStackTrace();
            }
        }

        /*
        try{
            socket = new Socket(hostname, port);
            socket.close();
        }catch (Exception ex){
            ex.printStackTrace();
        }
        */
    }

    void sendMessage(String msg)
    {
        try{
            Log.i("tag", "client>" + msg);
            out.writeObject(msg);
            out.flush();
        }
        catch(IOException ioException){
            ioException.printStackTrace();
        }
    }

    void readMessage(String msg) {
        Dialog dig = new Dialog(mContext);
        dig.setContentView(R.layout.dialog);
        TextView textView = (TextView) dig.findViewById(R.id.dialog_text);
        textView.setText(msg);
        dig.setTitle("알람");
        dig.show();
    }
}
