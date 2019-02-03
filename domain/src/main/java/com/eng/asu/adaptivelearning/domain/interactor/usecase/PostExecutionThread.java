package com.eng.asu.adaptivelearning.domain.interactor.usecase;

import io.reactivex.Scheduler;

/**
 * The thread the data is supposed to be published to
 *
 * @author muhammed
 */
public interface PostExecutionThread {
    Scheduler getScheduler();
}