package com.eng.asu.adaptivelearning.data;

import com.adaptivelearning.server.FancyModel.FancyClassroom;
import com.adaptivelearning.server.FancyModel.FancyCourse;
import com.adaptivelearning.server.FancyModel.FancyUser;
import com.eng.asu.adaptivelearning.domain.ClassroomService;
import com.eng.asu.adaptivelearning.domain.CourseService;
import com.eng.asu.adaptivelearning.domain.UserService;
import com.eng.asu.adaptivelearning.domain.UserStorage;
import com.facebook.stetho.okhttp3.StethoInterceptor;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.Observable;
import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.HttpException;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetworkRepository implements UserService,
        ClassroomService,
        CourseService {

    private static final String BASE_URL = "https://graduation-server.herokuapp.com/";
    private final RetrofitService serviceApi;
    private String authToken;

    @Inject
    NetworkRepository(UserStorage userStorage) {
        this.authToken = userStorage.getAuthToken();

        userStorage.setOnTokenChangeListener(token -> authToken = token);

        //Configuring the logcat to display request/response parameters
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        httpClient.addInterceptor(logging);
        httpClient.networkInterceptors().add(new StethoInterceptor());

        //10 Seconds timeout for reading , writing and connection establishing
        httpClient.connectTimeout(10, TimeUnit.SECONDS);
        httpClient.readTimeout(10, TimeUnit.SECONDS);
        httpClient.writeTimeout(10, TimeUnit.SECONDS);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(httpClient.build())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        serviceApi = retrofit.create(RetrofitService.class);
    }

    @Override
    public Completable createUser(String firstName, String lastName, String email, String userName, String password, int gender, String dateOfBirth) {
        return serviceApi.register(firstName, lastName, email, userName, password, gender, dateOfBirth)
                .flatMapCompletable(this::completableSourceMapper);
    }

    @Override
    public Observable<String> loginWithEmail(String email, String password) {
        return serviceApi.loginByEmail(email, password)
                .map(this::stringResponseMapper);
    }

    @Override
    public Observable<String> loginWithUserName(String userName, String password) {
        return serviceApi.loginByUsername(userName, password)
                .map(this::stringResponseMapper);
    }

    @Override
    public Observable<FancyUser> getUserProfile() {
        return serviceApi.getUserData(authToken);
    }

    @Override
    public Completable enrollInCourse(long courseId) {
        return serviceApi.enrollInCourse(authToken, courseId)
                .flatMapCompletable(this::completableSourceMapper);
    }

    @Override
    public Completable saveCourse(long courseId) {
        return serviceApi.saveCourse(authToken, courseId)
                .flatMapCompletable(this::completableSourceMapper);
    }

    @Override
    public Observable<List<FancyCourse>> getSavedCourses() {
        return serviceApi.getSavedCourses(authToken);
    }

    @Override
    public Observable<List<FancyCourse>> getStudentCourses() {
        return serviceApi.getStudentCourses(authToken);
    }

    @Override
    public Observable<List<FancyUser>> getChildren() {
        return serviceApi.getChildren(authToken);
    }

    @Override
    public Observable<List<FancyCourse>> getTeacherCourses() {
        return serviceApi.getTeacherCourses(authToken);
    }

    @Override
    public Completable addChild(String firstName, String lastName, String email, String userName, String password, int gender, String dateOfBirth) {
        return serviceApi.addChild(authToken, firstName, lastName, email, userName, password, gender, dateOfBirth)
                .flatMapCompletable(this::completableSourceMapper);
    }

    @Override
    public Observable<List<FancyClassroom>> getTeacherClassrooms() {
        return serviceApi.getTeacherClassrooms(authToken);
    }

    @Override
    public Observable<List<FancyClassroom>> getStudentClassrooms() {
        return serviceApi.getStudentClassrooms(authToken);
    }

    @Override
    public Completable joinClassroom(String passcode) {
        return serviceApi.joinClassroom(authToken, passcode)
                .flatMapCompletable(this::completableSourceMapper);
    }

    @Override
    public Observable<List<FancyCourse>> getHotCourses() {
        return serviceApi.getHotCourses();
    }

    @Override
    public Observable<List<FancyCourse>> getNewCourses() {
        return serviceApi.getNewCourses();
    }

    @Override
    public Observable<List<FancyCourse>> getCoursesByCategory(String category) {
        return serviceApi.getCoursesByCategory(category);
    }

    @Override
    public Observable<FancyCourse> getCourse(Long courseId) {
        return serviceApi.getCourse(authToken, courseId);
    }

    @Override
    public Observable<Boolean> createClassroom(String name, String category) {
        return serviceApi.createClassroom(authToken, name, category)
                .map(this::responseMapper);
    }

    private Completable completableSourceMapper(Response<ResponseBody> response) {
        try {
            responseMapper(response);
            return Completable.complete();
        } catch (Exception e) {
            return Completable.error(e);
        }
    }

    private String stringResponseMapper(Response<ResponseBody> response) throws IOException {

        //If response code is 200 and it contains a body
        if (response.isSuccessful() && response.body() != null)
            return response.body().string();
        else {
            if (response.errorBody() != null)
                throw new RuntimeException(response.errorBody().string());
            else if (response.body() != null)
                throw new RuntimeException(response.body().string());
            else
                throw new HttpException(response);
        }
    }

    private boolean responseMapper(Response<ResponseBody> response) throws IOException {
        if (isSuccessful(response)) {
            return true;
        } else {
            if (response.errorBody() != null)
                throw new RuntimeException(response.errorBody().string());
            else if (response.body() != null)
                throw new RuntimeException(response.body().string());
            else
                throw new HttpException(response);
        }
    }

    private boolean isSuccessful(Response response) {
        return response.isSuccessful();
    }
}
