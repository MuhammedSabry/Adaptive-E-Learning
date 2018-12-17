package com.eng.asu.adaptivelearning.data.service;

import com.eng.asu.adaptivelearning.model.User;

import io.reactivex.Observable;
import okhttp3.ResponseBody;

public interface UserService {

    Observable<ResponseBody> createUser(String email, String password, String name, int type);

    Observable<User> login(String email, String password);

    Observable<ResponseBody> createClassroom(String name, String category, int creator_id);
}
