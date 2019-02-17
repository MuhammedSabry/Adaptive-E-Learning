package com.eng.asu.adaptivelearning.data;

import com.adaptivelearning.server.constants.Mapping;
import com.adaptivelearning.server.constants.Param;
import com.eng.asu.adaptivelearning.domain.model.Course;
import com.eng.asu.adaptivelearning.domain.model.User;

import java.util.List;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.Response;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface RetrofitService {

    //Authentication related APIs
    @FormUrlEncoded
    @POST(Mapping.REGISTER)
    Observable<Response<ResponseBody>> register(@Field(Param.FIRST_NAME) String FIRST_NAME,
                                                @Field(Param.LAST_NAME) String LAST_NAME,
                                                @Field(Param.EMAIL) String email,
                                                @Field(Param.USERNAME) String userName,
                                                @Field(Param.PASSWORD) String password,
                                                @Field(Param.GENDER) int gender,
                                                @Field(Param.DATE_OF_BIRTH) String dateOfBirth);

    @GET(Mapping.LOGIN)
    Observable<Response<ResponseBody>> loginByEmail(@Query(Param.EMAIL) String email,
                                                    @Query(Param.PASSWORD) String password);

    @GET(Mapping.LOGIN)
    Observable<Response<ResponseBody>> loginByUsername(@Query(Param.USERNAME) String email,
                                                       @Query(Param.PASSWORD) String password);

    //User profile
    @GET(Mapping.PROFILE)
    Observable<User> getUserData(@Query(Param.ACCESS_TOKEN) String token);

    //General APIs
    @GET(Mapping.NEW_COURSES)
    Observable<List<Course>> getNewCourses();

    @GET(Mapping.HOT_COURSES)
    Observable<List<Course>> getHotCourses();

    @GET(Mapping.CATEGORY_COURSES)
    Observable<List<Course>> getCoursesByCategory(@Query(Param.CATEGORY) String category);

    //Parent APIs
    @FormUrlEncoded
    @POST(Mapping.ADD_CHILD)
    Observable<Response<ResponseBody>> addChild(@Field(Param.ACCESS_TOKEN) String token,
                                                @Field(Param.FIRST_NAME) String FIRST_NAME,
                                                @Field(Param.LAST_NAME) String LAST_NAME,
                                                @Field(Param.EMAIL) String email,
                                                @Field(Param.USERNAME) String userName,
                                                @Field(Param.PASSWORD) String password,
                                                @Field(Param.GENDER) int gender,
                                                @Field(Param.DATE_OF_BIRTH) String dateOfBirth);

    @GET(Mapping.CHILDREN)
    Observable<List<User>> getChildren(@Query(Param.ACCESS_TOKEN) String token);

    @FormUrlEncoded
    @POST("createClassroom")
    Observable<Response<ResponseBody>> createClassroom(@Field(Param.ACCESS_TOKEN) String token,
                                                       @Field(Param.CLASSROOM_NAME) String name,
                                                       @Field(Param.CATEGORY) String category);

    //Student APIs
    @FormUrlEncoded
    @POST(Mapping.ENROLL_COURSE)
    Observable<Response<ResponseBody>> enrollInCourse(@Field(Param.ACCESS_TOKEN) String token,
                                                      @Field(Param.COURSE_ID) int courseId);

    @GET(Mapping.STUDENT_COURSES)
    Observable<List<Course>> getStudentCourses(@Query(Param.ACCESS_TOKEN) String token);
}
