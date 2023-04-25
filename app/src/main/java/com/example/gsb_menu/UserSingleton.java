package com.example.gsb_menu;

public class UserSingleton {

    private static UserSingleton instance;
    private String userId;
    private boolean isLoggedIn;

    private UserSingleton() {
    }

    public static synchronized UserSingleton getInstance() {
        if (instance == null) {
            instance = new UserSingleton();
        }
        return instance;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public boolean isLoggedIn() {
        return isLoggedIn;
    }

    public void setLoggedIn(boolean isLoggedIn) {
        this.isLoggedIn = isLoggedIn;
    }
}
