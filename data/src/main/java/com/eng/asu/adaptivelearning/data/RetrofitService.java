package com.eng.asu.adaptivelearning.data;

import com.adaptivelearning.server.constants.Mapping;
import com.adaptivelearning.server.constants.Param;
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
    @POST(Mapping.REGISTER)
    Observable<Response<ResponseBody>> register(@Field(Param.FIRSTNAME) String firstName,
                                                @Field(Param.LASTNAME) String lastName,
                                                @Field(Param.EMAIL) String email,
                                                @Field(Param.USERNAME) String userName,
                                                @Field(Param.PASSWORD) String password,
                                                @Field(Param.GENDRE) int gender,
                                                @Field(Param.DATEOFBIRTH) String dateOfBirth);

    @GET(Mapping.LOGIN)
    Observable<User> loginByEmail(@Query(Param.EMAIL) String email,
                                  @Query(Param.PASSWORD) String password);

    @GET(Mapping.LOGIN)
    Observable<User> loginByUsername(@Query(Param.EMAIL) String email,
                                     @Query(Param.PASSWORD) String password);

    @FormUrlEncoded
    @POST(Mapping.AddChild)
    Observable<Response<ResponseBody>> addChild(@Field(Param.ACCESSTOKEN) String token,
                                                @Field(Param.FIRSTNAME) String firstName,
                                                @Field(Param.LASTNAME) String lastName,
                                                @Field(Param.EMAIL) String email,
                                                @Field(Param.USERNAME) String userName,
                                                @Field(Param.PASSWORD) String password,
                                                @Field(Param.GENDRE) int gender,
                                                @Field(Param.DATEOFBIRTH) String dateOfBirth);

    @GET(Mapping.PROFILE)
    Observable<User> getUserData(@Field(Param.ACCESSTOKEN) String token);

    @FormUrlEncoded
    @POST(Mapping.LOGOUT)
    Observable<Response<ResponseBody>> logout(@Field(Param.ACCESSTOKEN) String token);

    @GET(Mapping.CLASSROOMS)
    Observable<List<Classroom>> getCreatedClassRooms(@Query(Param.ACCESSTOKEN) String token);

    @FormUrlEncoded
    @DELETE(Mapping.CLASSROOMS)
    Observable<Response<ResponseBody>> deleteClassroom(@Field(Param.ACCESSTOKEN) String token,
                                                       @Field(Param.CLASSROOM_ID) int id);

    @GET(Mapping.CLASSROOMS)
    Observable<List<Classroom>> getEnrolledClassrooms(@Query(Param.ACCESSTOKEN) String token);

    @FormUrlEncoded
    @POST(Mapping.EnrollStudent)
    Observable<Response<ResponseBody>> joinClass(@Field(Param.ACCESSTOKEN) String token,
                                                 @Field(Param.PASSCODE) String passCode);

    @FormUrlEncoded
    @POST(Mapping.ENROLLCHILD)
    Observable<Response<ResponseBody>> addChildToClass(@Field(Param.ACCESSTOKEN) String token,
                                                       @Field("child_id") int childId,
                                                       @Field(Param.PASSCODE) String passCode);

    @GET("parent/children")
    Observable<List<User>> getChildren(@Query(Param.ACCESSTOKEN) String token);

    @GET("parent/child")
    Observable<User> getChild(@Query(Param.ACCESSTOKEN) String token, int id);

    @FormUrlEncoded
    @POST("teacher/courses")
    Observable<Response<ResponseBody>> createCourse(@Field(Param.ACCESSTOKEN) String token,
                                                    @Field("title") String title,
                                                    @Field("detailed_title") String detailedTitle,
                                                    @Field("description") String description,
                                                    @Field("level") String level);

    @FormUrlEncoded
    @POST("parent/enrollchild_course")
    Observable<Response<ResponseBody>> enrollChildInCourse(@Field(Param.ACCESSTOKEN) String token,
                                                           @Field("child_id") int childId,
                                                           @Field("courseId") int courseId);

    @FormUrlEncoded
    @POST("student/enroll_course")
    Observable<Response<ResponseBody>> enrollInCourse(@Field(Param.ACCESSTOKEN) String token,
                                                      @Field("courseId") int courseId);

    @FormUrlEncoded
    @POST("teacher/section")
    Observable<Response<ResponseBody>> addSection(@Field(Param.ACCESSTOKEN) String token,
                                                  @Field("section_title") String title,
                                                  @Field("course_id") int courseId);

    @FormUrlEncoded
    @PUT("teacher/section")
    Observable<Response<ResponseBody>> updateSectionInfo(@Field(Param.ACCESSTOKEN) String token,
                                                         @Field("section_title") String title,
                                                         @Field("section_id") int sectionId);

    @FormUrlEncoded
    @PUT("teacher/section")
    Observable<Response<ResponseBody>> deleteSection(@Field(Param.ACCESSTOKEN) String token,
                                                     @Field("section_id") int sectionId);

    @FormUrlEncoded
    @POST("createClassroom")
    Observable<Response<ResponseBody>> createClassroom(@Field(Param.ACCESSTOKEN) String token,
                                                       @Field("name") String name,
                                                       @Field("category") String category,
                                                       @Field("type") int type);

}
