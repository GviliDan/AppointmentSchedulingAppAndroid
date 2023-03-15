package com.example.scudle_sapar.addapters;

import com.example.scudle_sapar.services.Tor;
import com.example.scudle_sapar.services.User;

import java.util.ArrayList;
import java.time.*;

public class MyData {
    public static User conectedUser= new User();
    public static LocalDate dateSelected;
    public static LocalTime timeSelected;


    public static User admin= new User("admin@g.com" ,  "055", "Barbar", new Tor());

    static Tor tor1= new Tor("Avilable", "*00", LocalTime.of(8,0), LocalTime.of(9,0), "--");
    static Tor tor2= new Tor("Avilable", "*00", LocalTime.of(9,0), LocalTime.of(10,0), "--");
    static Tor tor3= new Tor("Avilable", "*00", LocalTime.of(10,0), LocalTime.of(11,0), "--");
    static Tor tor4= new Tor("Avilable", "*00", LocalTime.of(11,0),LocalTime.of(12,0), "--");
    static Tor tor5= new Tor("Avilable", "*00", LocalTime.of(12,0), LocalTime.of(13,0), "--");
    static Tor tor6= new Tor("Avilable", "*00", LocalTime.of(13,0), LocalTime.of(14,0), "--");
    static Tor tor7= new Tor("Avilable", "*00", LocalTime.of(14,0), LocalTime.of(15,0), "--");
    static Tor tor8= new Tor("Avilable", "*00", LocalTime.of(15,0), LocalTime.of(16,0), "--");
    static Tor tor9= new Tor("Avilable", "*00", LocalTime.of(16,0), LocalTime.of(17,0), "--");
    static Tor tor10=new Tor("Avilable", "*00", LocalTime.of(17,0), LocalTime.of(18,0), "--");

    public static  Tor[] torList = {tor1, tor2,tor3,tor4,tor5,tor6,tor7,tor8,tor9,tor10};

    public static ArrayList<User> dataSetUsres = new ArrayList<User>();














}
