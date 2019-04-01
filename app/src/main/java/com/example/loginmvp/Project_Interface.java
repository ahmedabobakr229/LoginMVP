package com.example.loginmvp;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface Project_Interface {


    @POST("register")
    @FormUrlEncoded
    Call<ResponseBody> setUser(@Field("username") String username ,@Field("name") String name, @Field("email") String email,
                               @Field("password") String password , @Field("address") String address,
                               @Field("region") String region ,@Field("country") String country ,
                               @Field("phone_number") String phone);

    @POST("login")
    @FormUrlEncoded
    Call<ResponseBody> setLogin(@Field("email") String email,
                                @Field("password") String password);
}
