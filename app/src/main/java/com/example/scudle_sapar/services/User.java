package com.example.scudle_sapar.services;

import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.RequiresApi;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class User  {

    private String email;
    private String phone;
    private String fullName;
    private Tor nextTor;

    public User() {
        this.email= "@com";
        this.phone= "0000000000";
        this.fullName= "no Name";
        this.nextTor= new Tor();
    }

    public User(String email, String phone, String fullName) {
        this.email = email;
        this.phone = phone;
        this.fullName = fullName;
        this.nextTor=new Tor();
    }

    public User(String email, String phone, String fullName, Tor nextTor) {
        this.email = email;
        this.phone = phone;
        this.fullName = fullName;
        this.nextTor = nextTor;
    }

    public Tor getNextTor() {
        return nextTor;
    }

    public void setNextTor(Tor nextTor) {
        this.nextTor = nextTor;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    @Override
    public String toString() {
        return "User{" +
                "email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", fullName='" + fullName + '\'' +
                ", nextTor=" + nextTor +
                '}';
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public String userToJson(){
        Gson gson =new GsonBuilder()
                .registerTypeAdapter(LocalDate.class, new LocalDateSerializer())
                .registerTypeAdapter(LocalTime.class, new LocalTimeSerializer())
                .create();

        String json= ((Gson) gson).toJson(this);
        return json;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static User userFromJson(String json){
        Gson gson =new GsonBuilder()
                .registerTypeAdapter(LocalDate.class, new LocalDateSerializer())
                .registerTypeAdapter(LocalTime.class, new LocalTimeSerializer())
                .create();

        return ((Gson) gson).fromJson(json, User.class);
    }

}





