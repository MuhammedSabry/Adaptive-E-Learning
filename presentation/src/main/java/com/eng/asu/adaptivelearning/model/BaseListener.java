package com.eng.asu.adaptivelearning.model;

public interface BaseListener {

    void onSuccess(String message);

    void onFail(String message);

    void onFallBack();
}
