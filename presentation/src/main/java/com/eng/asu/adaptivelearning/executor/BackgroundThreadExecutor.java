package com.eng.asu.adaptivelearning.executor;

import com.eng.asu.adaptivelearning.domain.interactor.usecase.BackgroundExecutionThread;

import io.reactivex.Scheduler;
import io.reactivex.schedulers.Schedulers;

/**
 * @author Muhammed Sabry
 */
public class BackgroundThreadExecutor implements BackgroundExecutionThread {
    @Override
    public Scheduler getScheduler() {
        return Schedulers.io();
    }
}
