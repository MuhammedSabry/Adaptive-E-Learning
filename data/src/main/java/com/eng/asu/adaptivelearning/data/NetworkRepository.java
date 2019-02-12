package com.eng.asu.adaptivelearning.data;

import com.eng.asu.adaptivelearning.domain.ClassroomService;
import com.eng.asu.adaptivelearning.domain.CourseService;
import com.eng.asu.adaptivelearning.domain.UserService;
import com.eng.asu.adaptivelearning.domain.model.Course;
import com.eng.asu.adaptivelearning.domain.model.User;
import com.facebook.stetho.okhttp3.StethoInterceptor;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

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

    public NetworkRepository() {

        //Configuring the logcat to display request/response parameters
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        httpClient.addInterceptor(logging);
        httpClient.networkInterceptors().add(new StethoInterceptor());

        //1 Minute timeout for reading , writing and connection establishing
        httpClient.connectTimeout(1, TimeUnit.MINUTES);
        httpClient.readTimeout(1, TimeUnit.MINUTES);
        httpClient.writeTimeout(1, TimeUnit.MINUTES);

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
    public Observable<User> getUserProfile(String token) {
        return serviceApi.getUserData(token);
    }

    @Override
    public Observable<List<User>> getChildren(String token) {
        return serviceApi.getChildren(token);
    }

    @Override
    public Completable addChild(String token, String firstName, String lastName, String email, String userName, String password, int gender, String dateOfBirth) {
        return serviceApi.addChild(token, firstName, lastName, email, userName, password, gender, dateOfBirth)
                .flatMapCompletable(this::completableSourceMapper);
    }

    @Override
    public Observable<List<Course>> getHotCourses() {
        return serviceApi.getHotCourses();
    }

    @Override
    public Observable<List<Course>> getNewCourses() {
        return serviceApi.getNewCourses();
    }

    @Override
    public Observable<Boolean> createClassroom(String token, String name, String category) {
        return serviceApi.createClassroom(token, name, category)
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
