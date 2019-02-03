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

import io.reactivex.Flowable;
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
    public Observable<Boolean> createUser(String firstName, String lastName, String email, String userName, String password, int gender, String dateOfBirth) {
        return serviceApi.register(firstName, lastName, email, userName, password, gender, dateOfBirth)
                .map(this::responseMapper);
    }

    @Override
    public Observable<User> loginWithEmail(String email, String password) {
        return serviceApi.loginByEmail(email, password);
    }

    @Override
    public Observable<User> loginWithUserName(String userName, String password) {
        return serviceApi.loginByUsername(userName, password);
    }

    @Override
    public Observable<Boolean> createClassroom(String token, String name, String category, int type) {
        return serviceApi.createClassroom(token, name, category, type)
                .map(this::responseMapper);
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
        return response.code() == 200;
    }

    @Override
    public Flowable<List<Course>> getAllCourses() {
        //TODO get all courses
        return Flowable.never();
    }
}
