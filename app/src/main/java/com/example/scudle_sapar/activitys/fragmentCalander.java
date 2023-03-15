package com.example.scudle_sapar.activitys;

import static com.example.scudle_sapar.addapters.MyData.conectedUser;
import static com.example.scudle_sapar.addapters.MyData.dateSelected;
import static com.example.scudle_sapar.addapters.MyData.timeSelected;
import static com.example.scudle_sapar.services.Tor.torFromJson;
import static com.example.scudle_sapar.services.User.userFromJson;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.scudle_sapar.R;
import com.example.scudle_sapar.myThreads.LoadTorsThread;
import com.example.scudle_sapar.services.Tor;
import com.example.scudle_sapar.services.User;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.time.*;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link fragmentCalander#newInstance} factory method to
 * create an instance of this fragment.
 */
public class fragmentCalander extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public fragmentCalander() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment fragmentCalander.
     */
    // TODO: Rename and change types and number of parameters
    public static fragmentCalander newInstance(String param1, String param2) {
        fragmentCalander fragment = new fragmentCalander();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_calander, container, false);
        dateSelected= LocalDate.now();
//        SimpleDateFormat dateFormat= new SimpleDateFormat("dd/MM/Y");
        TextView dateChoosen= view.findViewById(R.id.choosenDateTextPickPage);
//        dateChoosen.setText( dateFormat.format(dateSelected));
        dateChoosen.setText( dateSelected.toString());

        CalendarView calendarView = view.findViewById(R.id.calendarView);
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {

                dateSelected= LocalDate.of(year,month+1,dayOfMonth);
                dateChoosen.setText(dateSelected.toString());
                Executor executor= Executors.newFixedThreadPool(1);
                executor.execute(new LoadTorsThread(dateChoosen.getText().toString()));

            }

        });



        Button nextBut=view.findViewById(R.id.nextBut_calanderPage);
        nextBut.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                if(dateSelected.isBefore(LocalDate.now())){
                    Toast.makeText(v.getContext(),"That day has already passed ", Toast.LENGTH_LONG).show();
                }
                else
                    if (dateSelected.getDayOfWeek().toString().equals("SATURDAY")){
                         Toast.makeText(v.getContext(),"Not a working day ", Toast.LENGTH_LONG).show();
                    }
                    else {
                        System.out.println("**** " + dateSelected.getDayOfWeek());
                        Navigation.findNavController(view).navigate(R.id.calendarToTimePicker);
                    }

            }
        });

        return view;
    }


}

