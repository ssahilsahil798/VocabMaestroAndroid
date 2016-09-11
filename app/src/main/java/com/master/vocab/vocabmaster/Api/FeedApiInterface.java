package com.master.vocab.vocabmaster.Api;

import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Path;

/**
 * Created by sahildeswal on 02/09/16.
 */
public interface FeedApiInterface {

    @FormUrlEncoded
    @POST("user/auth/login")
    public void manualLogin(@Field("username") String username, @Field("password") String password);

    @FormUrlEncoded
    @POST("user/register")
    public void manualRegister(@Field("username") String username, @Field("password") String password);

    @GET("/cardstatus")
    public void getUserCardStatus();

    @GET("/wordstatus")
    public void getCategoryWordStatus(@Path("card_status") String card_status);

    @FormUrlEncoded
    @POST("/wordstatus")
    public void changeWordStatus(@Field("id") String word_id, @Field("word_status") String word_status);


}
