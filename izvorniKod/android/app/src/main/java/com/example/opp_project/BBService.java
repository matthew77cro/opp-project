package com.example.opp_project;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface BBService {

    @FormUrlEncoded
    @POST("rest/login")
    Call<Void> login(@Field("username") String username, @Field("password") String password);

    @GET("rest/profil")
    Call<ProfilPodaci> profil(@Header("Cookie") String string);

    @GET("rest/logout")
    Call<Profil> logout();

}
