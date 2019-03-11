package com.eng.asu.adaptivelearning.domain;

import io.reactivex.Observable;

public interface ClassroomService {

    Observable<Boolean> createClassroom(String name, String category);

}
