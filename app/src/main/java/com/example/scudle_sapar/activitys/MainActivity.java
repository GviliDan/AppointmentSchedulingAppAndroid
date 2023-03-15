package com.example.scudle_sapar.activitys;

import static com.example.scudle_sapar.addapters.MyData.admin;
import static com.example.scudle_sapar.addapters.MyData.conectedUser;
import static com.example.scudle_sapar.addapters.MyData.dataSetUsres;
import static com.example.scudle_sapar.addapters.MyData.torList;
import static com.example.scudle_sapar.services.Tor.torFromJson;
import static com.example.scudle_sapar.services.User.userFromJson;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.Navigation;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.scudle_sapar.R;
import com.example.scudle_sapar.services.Tor;
import com.example.scudle_sapar.services.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.time.*;
import java.util.HashMap;


public class MainActivity extends AppCompatActivity {

    public FirebaseAuth mAuth;
    public EditText emailText;
    public EditText passText;
    public int flag=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();
    }



    public void funcLogin(EditText phoneText, EditText passText, View view)  {

//        readAndWait(phoneText);
        String email=conectedUser.getEmail();
        System.out.println(email);
        String password= passText.getText().toString().trim();

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            conectedUser.setPhone(phoneText.getText().toString());
                            Navigation.findNavController(view).navigate(R.id.homeToHello);

                        } else {
                            Toast.makeText(MainActivity.this,"login fail", Toast.LENGTH_LONG).show();
                            System.out.println("cant got in ,email is : "+email);
                        }
                    }
                });
    }

    public void funcAdminLogin(EditText passText, View view)  {

        String email= admin.getEmail();
        System.out.println(email);
        String password= passText.getText().toString().trim();

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            conectedUser=admin;
                            Navigation.findNavController(view).navigate(R.id.homeToAdminPage);

                        } else {
                            Toast.makeText(MainActivity.this,"login fail", Toast.LENGTH_LONG).show();
                            System.out.println("cant got in ,email is : "+email);
                        }
                    }
                });
    }

    public void funcReg(EditText emailText, EditText passText, View view, EditText phoneText, EditText nameText){
        String email= emailText.getText().toString().trim();
        String password= passText.getText().toString().trim();

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            write(email, phoneText.getText().toString(), nameText.getText().toString());
                            conectedUser.setPhone(phoneText.getText().toString());
                            conectedUser.setEmail(email);
                            conectedUser.setFullName(nameText.getText().toString());
                            conectedUser.setNextTor(new Tor());
//                            FragmentRegDirections.RegisterToHello action= FragmentRegDirections.registerToHello(email);
//                            Navigation.findNavController(view).navigate(action);
                            Navigation.findNavController(view).navigate(R.id.registerToHello);
                        } else {
                            Toast.makeText(MainActivity.this,"register fail", Toast.LENGTH_LONG).show();
                            System.out.println(email+ " "+password);
                        }
                    }
                });
    }

    public void write(String emailText, String phoneText, String NameText) {
        User user= new User(emailText, phoneText, NameText);
        FirebaseDatabase database= FirebaseDatabase.getInstance();
        DatabaseReference myRef= database.getReference("users").child(phoneText);
        myRef.setValue(user.userToJson());
    }

    public void updateUserDao(User user) {
        FirebaseDatabase database= FirebaseDatabase.getInstance();
        DatabaseReference myRef= database.getReference("users").child(user.getPhone());
        myRef.setValue(user.userToJson());
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void writeDataDao(String nameOf, String phoneOf, LocalDate date, LocalTime startTime, LocalTime endTime, String purpose) {
        Tor tor= new Tor( nameOf, phoneOf, date, startTime,  endTime,  purpose);
//        LocalDateTime d1= LocalDateTime.of(date,startTime);
        FirebaseDatabase database= FirebaseDatabase.getInstance();
        DatabaseReference myRef= database.getReference("torim").child(date.toString()).child(startTime.toString());
        myRef.setValue(tor.torToJson());
        conectedUser.setNextTor(tor);
        updateUserDao(conectedUser);
        System.out.println(" ******* check: "+tor.torToJson());


//        myRef= database.getReference("users").child(conectedUser.getPhone());

    }


    public void readAndLogin( EditText serch, EditText passText, View view)   {
        FirebaseDatabase database= FirebaseDatabase.getInstance();
        DatabaseReference myRef= database.getReference("users").child(serch.getText().toString().trim());
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String json= (String) snapshot.getValue();
                System.out.println("json: "+ json);
                if (json!=null){
                    User value = userFromJson(json);
                    if (value !=null) {
                         conectedUser.setEmail(value.getEmail());
                         conectedUser.setFullName(value.getFullName());
                        if (value.getNextTor() != null) {
                            conectedUser.setPhone(serch.getText().toString());
                            conectedUser.setNextTor(value.getNextTor());
                        }
                    }
                }
                String email=" ";
                if (conectedUser.getEmail()!=null)
                     email= conectedUser.getEmail();
                String password=" ";
                System.out.println("finish take data: "+conectedUser.getEmail());
                if ((passText.getText().toString().trim()!=null)&&(!passText.getText().toString().trim().isEmpty()))
                    password= passText.getText().toString().trim();

                String finalEmail = email;
                mAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {

                                    Navigation.findNavController(view).navigate(R.id.homeToHello);
                                } else {
                                    Toast.makeText(MainActivity.this,"login fail", Toast.LENGTH_LONG).show();
                                    System.out.println("cant got in ,email is : "+ finalEmail);
                                }
                            }
                        });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }


    public void read( EditText serch)   {
        FirebaseDatabase database= FirebaseDatabase.getInstance();
        DatabaseReference myRef= database.getReference("users").child(serch.getText().toString().trim());
        myRef.addValueEventListener(new ValueEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                String json= (String) dataSnapshot.getValue();
                User value = userFromJson(json);
                if (value !=null) {
                    conectedUser.setEmail(value.getEmail());
                    conectedUser.setFullName(value.getFullName());
                    if (value.getNextTor()!=null)
                        conectedUser.setNextTor(value.getNextTor());

                }
                System.out.println("finish take data");
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
//                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
    }



    public void loadData(String dateChoosen) {
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



    @RequiresApi(api = Build.VERSION_CODES.O)
    public void cancelApp(User user, Tor tor) {
        writeDataDao("Avilable","*00", tor.getDate(), tor.getStartTime(), tor.getEndTime(), "--");
        torList[tor.getStartTime().getHour()-8] = new Tor("Avilable", "*00", tor.getStartTime(), tor.getEndTime(), "--");
        user.setNextTor(new Tor());
        updateUserDao(user);

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void cancelAppNoAVil(User user, Tor tor) {
        writeDataDao("Not-Avilable","*00", tor.getDate(), tor.getStartTime(), tor.getEndTime(), "--");
        torList[tor.getStartTime().getHour()-8] = new Tor("Not-Avilable", "*00", tor.getStartTime(), tor.getEndTime(), "--");
        user.setNextTor(new Tor());
        updateUserDao(user);

    }


    public void loadUsers() {
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





//    public void readData(ArrayList<Tor> dataSet, Date dateSelected, View view) {
//        FirebaseDatabase database= FirebaseDatabase.getInstance();
//        DatabaseReference myRef= database.getReference("Dates").child(String.valueOf(dateSelected));
//
//    }
}