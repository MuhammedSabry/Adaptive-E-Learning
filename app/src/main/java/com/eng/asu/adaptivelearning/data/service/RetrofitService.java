package com.eng.asu.adaptivelearning.data.service;

import com.eng.asu.adaptivelearning.model.User;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface RetrofitService {
    @FormUrlEncoded
    @POST("/api/auth/register")
    Observable<ResponseBody> createUser(
            @Field("email") String email,
            @Field("password") String password,
            @Field("name") String name,
            @Field("type") int type);

    @GET("/api/auth/login")
    Observable<User> login(@Query("email") String email, @Query("password") String password);

    @FormUrlEncoded
    @POST("createClassroom")
    Observable<ResponseBody> createClassroom(
            @Field("name") String name,
            @Field("category") String category,
            @Field("type") int type);
}
