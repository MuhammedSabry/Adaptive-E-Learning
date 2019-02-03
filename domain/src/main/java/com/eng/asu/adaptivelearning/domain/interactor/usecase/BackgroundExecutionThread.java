package com.eng.asu.adaptivelearning.domain.interactor.usecase;

import io.reactivex.Scheduler;

/**
 * Interface for providing the background thread for the application
 */
public interface BackgroundExecutionThread {
    Scheduler getScheduler();
}
