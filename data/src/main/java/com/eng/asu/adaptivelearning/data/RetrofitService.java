package com.eng.asu.adaptivelearning.data;

import com.eng.asu.adaptivelearning.domain.model.Classroom;
import com.eng.asu.adaptivelearning.domain.model.User;

import java.util.List;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.Response;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Query;

public interface RetrofitService {

    @FormUrlEncoded
    @POST("register")
    Observable<Response<ResponseBody>> register(@Field("firstname") String firstName,
                                                @Field("lastname") String lastName,
                                                @Field("email") String email,
                                                @Field("username") String userName,
                                                @Field("password") String password,
                                                @Field("gender") int gender,
                                                @Field("date_of_birth") String dateOfBirth);

    @GET("login")
    Observable<User> loginByEmail(@Query("email") String email,
                                  @Query("password") String password);

    @GET("login")
    Observable<User> loginByUsername(@Query("username") String userName,
                                     @Query("password") String password);

    @FormUrlEncoded
    @POST("parent/addchild")
    Observable<Response<ResponseBody>> addChild(@Field("token") String token,
                                                @Field("firstname") String firstName,
                                                @Field("lastname") String lastName,
                                                @Field("email") String email,
                                                @Field("username") String userName,
                                                @Field("password") String password,
                                                @Field("gender") int gender,
                                                @Field("date_of_birth") String dateOfBirth);

    @GET("profile")
    Observable<User> getUserData(@Query("token") String token);

    @FormUrlEncoded
    @POST
    Observable<Response<ResponseBody>> logout(@Field("token") String token);

    @GET("teacher/classrooms")
    Observable<List<Classroom>> getCreatedClassRooms(@Query("token") String token);

    @FormUrlEncoded
    @DELETE("teacher/classrooms")
    Observable<Response<ResponseBody>> deleteClassroom(@Field("token") String token,
                                                       @Field("id") int id);

    @GET("student/classrooms")
    Observable<List<Classroom>> getEnrolledClassrooms(@Query("token") String token);

    @FormUrlEncoded
    @POST("student/enroll")
    Observable<Response<ResponseBody>> joinClass(@Field("token") String token,
                                                 @Field("passcode") String passcode);

    @FormUrlEncoded
    @POST("student/enroll")
    Observable<Response<ResponseBody>> addChildToClass(@Field("token") String token,
                                                       @Field("child_id") int childId,
                                                       @Field("passcode") String passcode);

    @GET("parent/children")
    Observable<List<User>> getChildren(@Query("token") String token);

    @GET("parent/child")
    Observable<User> getChild(@Query("token") String token, int id);

    @FormUrlEncoded
    @POST("teacher/courses")
    Observable<Response<ResponseBody>> createCourse(@Field("token") String token,
                                                    @Field("title") String title,
                                                    @Field("detailed_title") String detailedTitle,
                                                    @Field("description") String description,
                                                    @Field("level") String level);

    @FormUrlEncoded
    @POST("parent/enrollchild_course")
    Observable<Response<ResponseBody>> enrollChildInCourse(@Field("token") String token,
                                                           @Field("child_id") int childId,
                                                           @Field("courseId") int courseId);

    @FormUrlEncoded
    @POST("student/enroll_course")
    Observable<Response<ResponseBody>> enrollInCourse(@Field("token") String token,
                                                      @Field("courseId") int courseId);

    @FormUrlEncoded
    @POST("teacher/section")
    Observable<Response<ResponseBody>> addSection(@Field("token") String token,
                                                  @Field("section_title") String title,
                                                  @Field("course_id") int courseId);

    @FormUrlEncoded
    @PUT("teacher/section")
    Observable<Response<ResponseBody>> updateSectionInfo(@Field("token") String token,
                                                         @Field("section_title") String title,
                                                         @Field("section_id") int sectionId);

    @FormUrlEncoded
    @PUT("teacher/section")
    Observable<Response<ResponseBody>> deleteSection(@Field("token") String token,
                                                     @Field("section_id") int sectionId);

    @FormUrlEncoded
    @POST("createClassroom")
    Observable<Response<ResponseBody>> createClassroom(@Field("token") String token,
                                                       @Field("name") String name,
                                                       @Field("category") String category,
                                                       @Field("type") int type);

}
