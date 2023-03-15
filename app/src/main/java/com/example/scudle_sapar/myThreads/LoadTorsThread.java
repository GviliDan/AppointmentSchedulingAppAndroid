package com.example.scudle_sapar.myThreads;

import static android.content.ContentValues.TAG;
import static com.example.scudle_sapar.addapters.MyData.torList;
import static com.example.scudle_sapar.services.Tor.torFromJson;

import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import com.example.scudle_sapar.services.Tor;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.core.Tag;

import java.time.LocalTime;
import java.util.HashMap;

public class LoadTorsThread implements Runnable{

    String dateChoosen;

    public LoadTorsThread(String dateChoosen) {
        this.dateChoosen = dateChoosen;
    }

    @Override
    public void run() {
        Log.i(TAG, "Thread Name: "+ Thread.currentThread().getName());
        FirebaseDatabase database= FirebaseDatabase.getInstance();
        DatabaseReference myRef= database.getReference("torim").child(dateChoosen);
        myRef.addValueEventListener(new ValueEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                HashMap<String,String> map= (HashMap<String,String>) dataSnapshot.getValue();
//                Tor value = torFromJson(json);
                if (map!=null) {
                    for (int i = 8; i < 10; i++){
                        System.out.println(i-8+"  "+"0" + i + ":00  :" + map.get("0" + i + ":00"));
                        if (map.get("0" + i + ":00")!=null)
                            torList[i-8]= torFromJson(map.get("0" + i + ":00"));
                        else
                            torList[i-8]= new Tor("Avilable", "*00", LocalTime.of(i,0), LocalTime.of(i+1,0), "--");
                    }

                    for (int i = 10; i < 18; i++){
                        System.out.println(i-8+"  "+ i + ":00  :" + map.get( i + ":00"));
                        if (map.get( i + ":00")!=null)
                            torList[i-8]= torFromJson(map.get( i + ":00"));
                        else
                            torList[i-8]= new Tor("Avilable", "*00", LocalTime.of(i,0), LocalTime.of(i+1,0), "--");
                    }

                }

            }
            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
//                Log.w(TAG, "Failed to read value.", error.toException());
                System.out.println("******** didnt get torim ********");
            }
        });


    }
}
