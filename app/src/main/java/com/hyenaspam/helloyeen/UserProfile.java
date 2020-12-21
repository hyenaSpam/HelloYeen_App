package com.hyenaspam.helloyeen;

import android.util.Log;

public class UserProfile {

    private String email;
    private String firstName;
    private String lastName;


    private String role;
    private String favoriteCoffee;
    private String yeenGang;
    private String phoneNumber;
    private boolean isVerified;

    private static UserProfile user = null;

    protected UserProfile() {
        email = "";
        firstName = "";
        lastName = "";
        role = "";
        favoriteCoffee = "";
        yeenGang = "";
        phoneNumber = "1";
        isVerified = false;
    }

    public static UserProfile getInstance(){
        if (user == null){
            user = new UserProfile();
        }
        return user;
    }

    public String getUser(){
        String s;
        s = String.format(
                "email: %s\nfirstName: %s\nlastName: %s\nrole: %s\nfavoriteCoffee: %s\nyeenGang: %s\nphone: %s\nisVerified: %s",
                this.getEmail(),
                this.getFirstName(),
                this.getLastName(),
                this.getRole(),
                this.getFavoriteCoffee(),
                this.getYeenGang(),
                this.getPhoneNumber(),
                this.getIsVerified()
        );
        return s;
    }

    public String getEmail(){
        return email;
    }

    public void setEmail(String s){
        email = s;
    }


    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String s){
        this.firstName = s;
    }

    public void setLastName(String s) {
        this.lastName = s;
    }

    public String getLastName() {
        return lastName;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void setPhoneNumber(String s){
        if (s.contains("+")){
            // remove + sign when receiving from Iterable getUser API
            // could also be helpful for cleaning up user input later?
            s = s.replace("+","");
        }
        if (s.length() == 10) {
            this.phoneNumber = "1" + s;
        } else if (s.length() == 11){
            this.phoneNumber = s;
        } else {
            this.phoneNumber = s;
        }
        Log.i("YEEN_phone", this.phoneNumber);
    }

    public String getPhoneNumber(){
        return this.phoneNumber;
    }

    public void setIsVerified(Boolean bool){
        this.isVerified = bool;
    }

    public Boolean getIsVerified(){
        return this.isVerified;
    }

    public String getYeenGang() {
        return yeenGang;
    }

    public void setYeenGang(String yeenGang) {
        this.yeenGang = yeenGang;
    }

    public void setFavoriteCoffee(String s) {
        this.favoriteCoffee = s;
    }

    public String getFavoriteCoffee(){
        return this.favoriteCoffee;
    }
}
