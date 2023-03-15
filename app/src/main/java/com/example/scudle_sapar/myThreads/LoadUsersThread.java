package com.example.scudle_sapar.myThreads;

import static android.content.ContentValues.TAG;
import static com.example.scudle_sapar.addapters.MyData.dataSetUsres;
import static com.example.scudle_sapar.services.User.userFromJson;

import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import com.example.scudle_sapar.activitys.MainActivity;
import com.example.scudle_sapar.services.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class LoadUsersThread implements Runnable {

    public LoadUsersThread() {

    }


    @Override
    public void run() {
        Log.i( TAG, "Thread Name: "+ Thread.currentThread().getName());
        FirebaseDatabase database= FirebaseDatabase.getInstance();
        DatabaseReference myRef= database.getReference("users");
        myRef.addValueEventListener(new ValueEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                HashMap<String,String> map= (HashMap<String,String>) dataSnapshot.getValue();

                System.out.println(" ****   loadin users.....");
                String json;
                if (map!=null) {
                    for (String key: map.keySet()) {
                        json= map.get(key);
                        User ui=userFromJson(json.trim());
                        dataSetUsres.add(ui);
                    }

                }
            }
            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
//                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });

    }
}
