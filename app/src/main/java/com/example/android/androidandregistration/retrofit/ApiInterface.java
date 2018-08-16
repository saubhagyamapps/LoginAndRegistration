package com.example.android.androidandregistration.retrofit;


import com.example.android.androidandregistration.model.LoginModel;
import com.example.android.androidandregistration.model.RegistrationModel;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;


public interface ApiInterface {
    //http://rajviinfotech.in/quotes/user_login
    @FormUrlEncoded
    @POST("user_login")
    Call<LoginModel> login(@Field("email") String email, @Field("password") String password);

    //http://rajviinfotech.in/quotes/form_register

    @FormUrlEncoded
    @POST("form_register")
    Call<RegistrationModel> registration(@Field("firstname") String firstname,
                                         @Field("lastname") String lastname,
                                         @Field("email") String email,
                                         @Field("password") String password,
                                         @Field("gender") String gender,
                                         @Field("contact") String contact);
    //http://rajviinfotech.in/quotes/forgatePassword
    @FormUrlEncoded
    @POST("")
    Call<LoginModel> forgatepassword(@Field("email") String email);

}