package com.eng.asu.adaptivelearning.domain;

import com.adaptivelearning.server.FancyModel.FancyUser;

public interface UserStorage {
    FancyUser getUser();

    void setUser(FancyUser user);

    void removeUser();

    String getAuthToken();

    void setAuthToken(String token);

    void removeToken();
}
