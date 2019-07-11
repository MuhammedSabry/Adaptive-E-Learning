package com.eng.asu.adaptivelearning.data;

import com.adaptivelearning.server.FancyModel.FancyMediaFile;
import com.adaptivelearning.server.FancyModel.FancyQuiz;
import com.adaptivelearning.server.FancyModel.FancyStudentQuiz;
import com.adaptivelearning.server.FancyModel.FancyUser;
import com.eng.asu.adaptivelearning.domain.ClassroomService;
import com.eng.asu.adaptivelearning.domain.CourseService;
import com.eng.asu.adaptivelearning.domain.StudentAnswer;
import com.eng.asu.adaptivelearning.domain.User;
import com.eng.asu.adaptivelearning.domain.UserService;
import com.eng.asu.adaptivelearning.domain.UserStorage;
import com.eng.asu.adaptivelearning.domain.model.Classroom;
import com.eng.asu.adaptivelearning.domain.model.Course;
import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Single;
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

        userStorage.setOnTokenChangeListener(token -> {
            if (token != null)
                authToken = token;
        });

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
                .addConverterFactory(GsonConverterFactory.create(new GsonBuilder()
                        .setFieldNamingPolicy(FieldNamingPolicy.IDENTITY)
                        .create()))
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
                .map(this::stringResponseMapper)
                .doOnNext(s -> this.authToken = s);
    }

    @Override
    public Observable<String> loginWithUserName(String userName, String password) {
        return serviceApi.loginByUsername(userName, password)
                .map(this::stringResponseMapper)
                .doOnNext(s -> this.authToken = s);

    }

    @Override
    public Observable<FancyUser> getUserProfile() {
        return serviceApi.getUserData(authToken);
    }

    @Override
    public Observable<User> getUserProfile2() {
        return serviceApi.getUserData2(authToken);
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
    public Observable<List<Course>> getSavedCourses() {
        return serviceApi.getSavedCourses(authToken);
    }

    @Override
    public Observable<List<Course>> getStudentCourses() {
        return serviceApi.getStudentCourses(authToken);
    }

    @Override
    public Observable<List<FancyUser>> getChildren() {
        return serviceApi.getChildren(authToken);
    }

    @Override
    public Observable<List<Course>> getTeacherCourses() {
        return serviceApi.getTeacherCourses(authToken);
    }

    @Override
    public Completable addChild(String firstName, String lastName, String email, String userName, String password, int gender, String dateOfBirth) {
        return serviceApi.addChild(authToken, firstName, lastName, email, userName, password, gender, dateOfBirth)
                .flatMapCompletable(this::completableSourceMapper);
    }

    @Override
    public Observable<List<Classroom>> getTeacherClassrooms() {
        return serviceApi.getTeacherClassrooms(authToken);
    }

    @Override
    public Observable<List<Classroom>> getStudentClassrooms() {
        return serviceApi.getStudentClassrooms(authToken);
    }

    @Override
    public Completable joinClassroom(String passcode) {
        return serviceApi.joinClassroom(authToken, passcode)
                .flatMapCompletable(this::completableSourceMapper);
    }

    @Override
    public Completable joinChildToClassroom(String firstName, String passcode) {
        return serviceApi.joinChildToClassroom(authToken, firstName, passcode)
                .flatMapCompletable(this::completableSourceMapper);
    }

    @Override
    public Completable joinChildToCourse(String firstName, String courseID) {
        return serviceApi.joinChildToCourse(authToken, firstName, courseID)
                .flatMapCompletable(this::completableSourceMapper);
    }

    @Override
    public Observable<List<Course>> getAllCourses() {
        return serviceApi.getAllCourses();
    }

    @Override
    public Completable sendTeachingRequest() {
        return serviceApi.sendTeachingRequest(authToken);
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
    public Observable<List<Course>> getCoursesByCategory(String category) {
        return serviceApi.getCoursesByCategory(category);
    }

    @Override
    public Observable<Course> getCourse(Long courseId) {
        return serviceApi.getCourse(authToken, courseId);
    }

    @Override
    public Observable<FancyMediaFile> getMediaFile(Long mediaContentId) {
        return serviceApi.getMedia(authToken, mediaContentId);
    }

    @Override
    public Observable<FancyQuiz> getQuiz(Long quizId) {
        return serviceApi.getQuiz(authToken, quizId);
    }

    @Override
    public Single<FancyQuiz> startQuiz(Long quizID) {
        return serviceApi.startQuiz(authToken, quizID);
    }

    @Override
    public Completable submitQuizAnswers(Long quizId, List<StudentAnswer> answers) {
        return serviceApi.submitQuiz(authToken,
                quizId,
                new QuizSubmission(answers))
                .flatMapCompletable(this::completableSourceMapper);
    }

    @Override
    public Single<FancyStudentQuiz> getSubmittedQuiz(Long quizId) {
        return serviceApi.getSubmittedQuiz(authToken, quizId);
    }

    public static class QuizSubmission {
        private List<StudentAnswer> questions;

        QuizSubmission(List<StudentAnswer> questions) {
            this.questions = questions;
        }

        public List<StudentAnswer> getQuestions() {
            return questions;
        }
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
