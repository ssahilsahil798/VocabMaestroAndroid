package com.master.vocab.vocabmaster.Api;

import retrofit.Callback;
import retrofit.client.Request;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.Headers;
import retrofit.http.POST;
import retrofit.http.Part;
import retrofit.http.Path;
import retrofit.http.Query;

/**
 * Created by sahildeswal on 02/09/16.
 */
public interface FeedApiInterface {

    @Headers("content-type: application/x-www-form-urlencoded")
    @FormUrlEncoded
    @POST("user/auth/login")
    public void manualLogin(@Field("username") String username, @Field("password") String password, Callback<String> cb);

    @Headers("content-type: application/x-www-form-urlencoded")
    @FormUrlEncoded
    @POST("user/register")
    public void manualRegister(@Field("username") String username, @Field("password") String password, Callback<String> cb);

    @GET("/cardstatus")
    public void getUserCardStatus(Callback<String> cb);

    @GET("/wordstatus")
    public void getCategoryWordStatus(@Query("card_status") int card_status, @Query("limit") int limit,
                                      @Query("offset") int offset, Callback<String> cb);

    @Headers("content-type: application/x-www-form-urlencoded")
    @FormUrlEncoded
    @POST("/wordstatus/change/")
    public void changeWordStatus(@Field("id") String word_id, @Field("word_status") String word_status, Callback<String> cb);


}
