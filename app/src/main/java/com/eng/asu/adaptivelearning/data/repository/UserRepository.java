package com.eng.asu.adaptivelearning.data.repository;

import com.eng.asu.adaptivelearning.data.service.RetrofitService;
import com.eng.asu.adaptivelearning.data.service.UserService;
import com.eng.asu.adaptivelearning.model.User;
import com.facebook.stetho.okhttp3.StethoInterceptor;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class UserRepository implements UserService {

    private static final String BASE_URL = "https://graduation-server.herokuapp.com/";
    private final RetrofitService serviceApi;

    public UserRepository() {
        //Configuring the logcat to display request/response parameters
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        httpClient.addInterceptor(logging);
        httpClient.networkInterceptors().add(new StethoInterceptor());

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
    public Observable<ResponseBody> createUser(String email, String password, String name, int type) {
        return serviceApi.createUser(email, password, name, type)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<User> login(String email, String password) {
        return serviceApi.login(email, password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<ResponseBody> createClassroom(String name, String category, int creator_id) {
        return serviceApi.createClassroom(name, category, creator_id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

}
