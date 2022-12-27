package com.sp.navigationbartest;

public class Restaurant {
    private String userName ="";
    private String userAge = "";
    private String userLocation = "";
    private String userGender = "";
    private String userComp = "";
    private String userGenre= "";
    private String userPlatform = "";
    private String userImage ="";

    public String getUserName() {
        return userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }
    public String getUserAge() {
        return userAge;
    }
    public void setUserAge(String userAge) {
        this.userAge = userAge;
    }
    public String getUserLocation() {
        return userLocation;
    }
    public void setUserLocation(String userLocation) {
        this.userLocation = userLocation;
    }
    public String getUserGender() {
        return userGender;
    }
    public void setUserGender(String userGender) {
        this.userGender = userGender;
    }

    public String getUserComp() {
        return userComp;
    }

    public void setUserComp(String userComp) {
        this.userComp = userComp;
    }

    public String getUserGenre() {
        return userGenre;
    }

    public void setUserGenre(String userGenre) {
        this.userGenre = userGenre;
    }

    public String getUserPlatform() {
        return userPlatform;
    }

    public void setUserPlatform(String userPlatform) {
        this.userPlatform = userPlatform;
    }

    public String getUserImage() {
        return userImage;
    }

    public void setUserImage(String userImage) {
        this.userImage = userImage;
    }

    @Override
    public String toString(){
        return (getUserName());}

}
