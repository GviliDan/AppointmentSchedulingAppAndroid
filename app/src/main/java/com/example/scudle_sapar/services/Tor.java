package com.example.scudle_sapar.services;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.example.scudle_sapar.addapters.MyData;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.Serializable;
import java.time.*;



public class Tor implements Serializable {
    private String nameOf;

    private String phoneOf;

    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd")
    private LocalDate date;

    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="HH:mm")
    private LocalTime startTime;

    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="HH:mm")
    private LocalTime endTime;
    private String Purpose;

    public Tor() {
        this.nameOf=" ";
        this.date= LocalDate.of(0,1,1);
        this.phoneOf= "*00";
        this.startTime= LocalTime.of(0,0);
        this.endTime=LocalTime.of(0,0);
        this.Purpose = "No Appointment exsist";
    }

    public String getPhoneOf() {
        return phoneOf;
    }

    public void setPhoneOf(String phoneOf) {
        this.phoneOf = phoneOf;
    }

    public Tor(String name, String phoneOf, LocalDate date, LocalTime startTime, LocalTime endTime, String purpose) {
        this.nameOf = name;
        this.phoneOf = phoneOf;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        Purpose = purpose;
    }
    public Tor(String name, String phoneOf, LocalTime startTime, LocalTime endTime, String purpose) {
        this.nameOf = name;
        this.phoneOf = phoneOf;
        this.date = MyData.dateSelected;
        this.startTime = startTime;
        this.endTime = endTime;
        Purpose = purpose;
    }

    public String getNameOf() {
        return nameOf;
    }

    public void setNameOf(String name) {
        this.nameOf = name;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    public String getPurpose() {
        return Purpose;
    }

    public void setPurpose(String purpose) {
        Purpose = purpose;
    }

    @Override
    public String toString() {
        return "Tor{" +
                "nameOf='" + nameOf + '\'' +
                ", phoneOf='" + phoneOf + '\'' +
                ", date=" + date +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", Purpose='" + Purpose + '\'' +
                '}';
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public String torToJson(){
        Gson gson =new GsonBuilder()
                .registerTypeAdapter(LocalDate.class, new LocalDateSerializer())
                .registerTypeAdapter(LocalTime.class, new LocalTimeSerializer())
                .create();

        String json= ((Gson) gson).toJson(this);
        return json;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static Tor torFromJson(String json){
        Gson gson =new GsonBuilder()
                .registerTypeAdapter(LocalDate.class, new LocalDateSerializer())
                .registerTypeAdapter(LocalTime.class, new LocalTimeSerializer())
                .create();
        return ((Gson) gson).fromJson(json, Tor.class);
    }



}
