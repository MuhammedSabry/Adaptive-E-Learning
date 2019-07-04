package com.eng.asu.adaptivelearning.data;

import com.adaptivelearning.server.FancyModel.FancyCategory;
import com.adaptivelearning.server.FancyModel.FancyClassroom;
import com.adaptivelearning.server.FancyModel.FancyCourse;
import com.adaptivelearning.server.FancyModel.FancyMediaFile;
import com.adaptivelearning.server.FancyModel.FancyQuiz;
import com.adaptivelearning.server.FancyModel.FancyStudentQuiz;
import com.adaptivelearning.server.FancyModel.FancyUser;
import com.adaptivelearning.server.constants.Mapping;
import com.adaptivelearning.server.constants.Param;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Single;
import okhttp3.ResponseBody;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
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

    //User
    @GET(Mapping.PROFILE)
    Observable<FancyUser> getUserData(@Query(Param.ACCESS_TOKEN) String token);

    @DELETE(Mapping.SAVED_COURSES)
    Observable<Response<ResponseBody>> removeSavedCourse(@Query(Param.COURSE_ID) int courseId);

    @GET(Mapping.SAVED_COURSES)
    Observable<List<FancyCourse>> getSavedCourses(@Query(Param.ACCESS_TOKEN) String token);

    @GET(Mapping.COURSE)
    Observable<FancyCourse> getCourse(@Query(Param.ACCESS_TOKEN) String token,
                                      @Query(Param.COURSE_ID) Long courseId);

    //General APIs
    @GET(Mapping.NEW_COURSES)
    Observable<List<FancyCourse>> getNewCourses();

    @GET(Mapping.HOT_COURSES)
    Observable<List<FancyCourse>> getHotCourses();

    @GET(Mapping.TOP_RATED_COURSES)
    Observable<List<FancyCourse>> getTopRatedCourses();

    @GET(Mapping.CATEGORY_COURSES)
    Observable<List<FancyCourse>> getCoursesByCategory(@Query(Param.CATEGORY_ID) long categoryId);

    @GET(Mapping.CATEGORY_COURSES)
    Observable<List<FancyCourse>> getCoursesByCategory(@Query(Param.CATEGORY) String category);

    @GET(Mapping.CATEGORIES)
    Observable<List<FancyCategory>> getCategories();

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
    Observable<List<FancyUser>> getChildren(@Query(Param.ACCESS_TOKEN) String token);

    @FormUrlEncoded
    @POST("createClassroom")
    Observable<Response<ResponseBody>> createClassroom(@Field(Param.ACCESS_TOKEN) String token,
                                                       @Field(Param.CLASSROOM_NAME) String name,
                                                       @Field(Param.CATEGORY) String category);

    //Student APIs
    @FormUrlEncoded
    @POST(Mapping.ENROLL_COURSE)
    Observable<Response<ResponseBody>> enrollInCourse(@Field(Param.ACCESS_TOKEN) String token,
                                                      @Field(Param.COURSE_ID) long courseId);

    @FormUrlEncoded
    @POST(Mapping.SAVED_COURSES)
    Observable<Response<ResponseBody>> saveCourse(@Field(Param.ACCESS_TOKEN) String token,
                                                  @Field(Param.COURSE_ID) long courseId);

    @FormUrlEncoded
    @POST(Mapping.JOIN_CLASSROOM)
    Observable<Response<ResponseBody>> joinClassroom(@Field(Param.ACCESS_TOKEN) String token,
                                                     @Field(Param.PASSCODE) String passcode);


    @GET(Mapping.STUDENT_COURSES)
    Observable<List<FancyCourse>> getStudentCourses(@Query(Param.ACCESS_TOKEN) String token);

    @GET(Mapping.TEACHER_COURSES)
    Observable<List<FancyCourse>> getTeacherCourses(@Query(Param.ACCESS_TOKEN) String token);

    @GET(Mapping.TEACHER_CLASSROOM)
    Observable<List<FancyClassroom>> getTeacherClassrooms(@Query(Param.ACCESS_TOKEN) String token);

    @GET(Mapping.STUDENT_CLASSROOMS)
    Observable<List<FancyClassroom>> getStudentClassrooms(@Query(Param.ACCESS_TOKEN) String token);

    @GET(Mapping.STUDENT_QUIZ_INFO)
    Observable<FancyQuiz> getQuiz(@Query(Param.ACCESS_TOKEN) String token,
                                  @Query(Param.QUIZ_ID) long quizId);


    @GET(Mapping.STUDENT_START_QUIZ)
    Single<FancyQuiz> startQuiz(@Query(Param.ACCESS_TOKEN) String token,
                                @Query(Param.QUIZ_ID) long quizId);

    @POST(Mapping.STUDENT_SUBMIT_QUIZ)
    Observable<Response<ResponseBody>> submitQuiz(@Query(Param.ACCESS_TOKEN) String token,
                                                  @Query(Param.QUIZ_ID) long quizId,
                                                  @Body NetworkRepository.QuizSubmission answers);

    @GET(Mapping.TEACHER_MEDIA)
    Observable<FancyMediaFile> getMedia(@Query(Param.ACCESS_TOKEN) String token,
                                        @Query(Param.FILE_ID) long mediaId);

    @GET(Mapping.STUDENT_QUIZ_SCORE)
    Single<FancyStudentQuiz> getSubmittedQuiz(@Query(Param.ACCESS_TOKEN) String authToken,
                                              @Query(Param.QUIZ_ID) Long quizId);
}
