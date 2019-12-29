package com.example.opp_project;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface BBService {

    @FormUrlEncoded
    @POST("rest/login")
    Call<Void> login(@Field("username") String username, @Field("password") String password);

    @GET("rest/profil")
    Call<ProfilPodaci> profil(@Header("Cookie") String string);

    @GET("rest/profil/slika")
    Call<ResponseBody> slika(@Header("Cookie") String string);

    @POST("rest/logout")
    Call<Void> logout(@Header("Cookie") String string);

    @GET("rest/profil/racuni")
    Call<List<RacuniPodaci>> racuni(@Header("Cookie") String string);

    @GET("rest/profil/kartice")
    Call<List<KarticaPodaci>> kartice(@Header("Cookie") String string);

}
