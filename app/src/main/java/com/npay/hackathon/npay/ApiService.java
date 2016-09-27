package com.npay.hackathon.npay;

import android.util.TypedValue;

import org.json.JSONObject;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by Administrator on 2016-09-08.
 */

public interface ApiService {

    public static final String API_URL = "http://221.162.153.13:2426";

    @POST("/")
    Call<String> createRoom (@Body String body);

}
