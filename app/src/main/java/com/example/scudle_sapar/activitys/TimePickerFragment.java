package com.example.scudle_sapar.activitys;

import static com.example.scudle_sapar.addapters.MyData.conectedUser;
import static com.example.scudle_sapar.addapters.MyData.dateSelected;
import static com.example.scudle_sapar.addapters.MyData.timeSelected;
import static com.example.scudle_sapar.addapters.MyData.torList;

import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.scudle_sapar.R;
import com.example.scudle_sapar.addapters.CustomAdapter;
import com.example.scudle_sapar.addapters.MyData;
import com.example.scudle_sapar.myThreads.LoadTorsThread;
import com.example.scudle_sapar.services.Tor;

import java.time.*;
import java.util.ArrayList;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TimePickerFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TimePickerFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public TimePickerFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TimePickerFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TimePickerFragment newInstance(String param1, String param2) {
        TimePickerFragment fragment = new TimePickerFragment();
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
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//        SimpleDateFormat dateFormat= new SimpleDateFormat("dd/MM/yy");
        TextView dateChoosen= view.findViewById(R.id.date_text_timepickPage);
        dateChoosen.setText( dateSelected.toString());
        TextView dayString= view.findViewById(R.id.dattextViewTPP);
        dayString.setText( dateSelected.getDayOfWeek().toString());


        recycleActionTimePick(view);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_time_picker, container, false);
        Button timePickBut=view.findViewById(R.id.time_picker_buttn);
        timeSelected= LocalTime.of(8,0);
        timePickBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog.OnTimeSetListener onTimeSetListener=new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int selectedMinute) {
                        timeSelected= LocalTime.of(hourOfDay, selectedMinute);
//                        timePickBut.setText(String.format(Locale.getDefault(),"%02d:%02d", hourOfDay, selectedMinute));
                        timePickBut.setText( timeSelected.toString());
                    }
                };
                int style= AlertDialog.THEME_HOLO_DARK;
                TimePickerDialog timePickerDialog= new TimePickerDialog(v.getContext(),style, onTimeSetListener, timeSelected.getHour(),timeSelected.getMinute(),true);
                timePickerDialog.setTitle("Select Time");
                timePickerDialog.show();
            }
        });

        RecyclerView recyclerView1=view.findViewById(R.id.my_recycler_view);
        recyclerView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timePickBut.setText( timeSelected.toString());
            }
        });

        Button btnH = view.findViewById(R.id.hair);
        Button btnB = view.findViewById(R.id.beard);
        Button btnHB = view.findViewById(R.id.hairbeard);
        final String[] purpose={""};
        btnH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                purpose[0] = "Hair cut";
                btnH.setBackgroundResource(R.drawable.button_design_gray);
                btnB.setBackgroundResource(R.drawable.button_design2);
                btnHB.setBackgroundResource(R.drawable.button_design2);
            }
        });
        btnB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                purpose[0] = "Beard";
                btnH.setBackgroundResource(R.drawable.button_design2);
                btnB.setBackgroundResource(R.drawable.button_design_gray);
                btnHB.setBackgroundResource(R.drawable.button_design2);
            }
        });
        btnHB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                purpose[0] = "Hair cut and beard";
                btnH.setBackgroundResource(R.drawable.button_design2);
                btnB.setBackgroundResource(R.drawable.button_design2);
                btnHB.setBackgroundResource(R.drawable.button_design_gray);
            }
        });


        Button sceduleBut=view.findViewById(R.id.scrdule_button);
        sceduleBut.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                MainActivity mainActivity = (MainActivity) getActivity();
                if ((timeSelected.getHour() < 8) || (timeSelected.getHour() > 17)) { // ׳‘׳“׳™׳§׳” ׳©׳”׳–׳׳ ׳”׳ ׳‘׳—׳¨ ׳”׳•׳ ׳¢׳ ׳₪׳™ ׳”׳׳•׳– ׳©׳ ׳”׳¡׳₪׳¨
                    Toast.makeText(v.getContext(), "Not during business hours", Toast.LENGTH_LONG).show();
                } else {
                    if (timeSelected.getMinute() != 0) { // ׳‘׳“׳™׳§׳” ׳©׳”׳–׳׳ ׳‘׳©׳¢׳” ׳¢׳’׳•׳׳”
                        Toast.makeText(v.getContext(), "Not suitable time ", Toast.LENGTH_LONG).show();
                    } else {
                        if (purpose[0] == "") { // ׳‘׳“׳™׳§׳” ׳©׳ ׳‘׳—׳¨׳” ׳׳˜׳¨׳” ׳׳₪׳’׳™׳©׳”
                            Toast.makeText(v.getContext(), "No purpose selected ", Toast.LENGTH_LONG).show();
                        } else {
                            if (LocalDateTime.of(dateSelected,timeSelected).isBefore(LocalDateTime.now()))
                            {
                                Toast.makeText(v.getContext(), "this meeting has passed ", Toast.LENGTH_LONG).show();
                            } else {
                                if (torList[timeSelected.getHour() - 8].getNameOf().equals("Avilable")) {
                                    mainActivity.writeDataDao(conectedUser.getFullName(), conectedUser.getPhone(), dateSelected, timeSelected, timeSelected.plusHours(1), purpose[0]);
                                    Navigation.findNavController(view).navigate(R.id.timeBackToHello);
                                } else {
                                    Toast.makeText(v.getContext(), "not avilabile", Toast.LENGTH_LONG).show();
                                }
                            }
                        }
                    }
                }
            }
        });

        Button forwordBut=view.findViewById(R.id.forwordDateBut);
        forwordBut.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                dateSelected=dateSelected.plusDays(1);
                if (dateSelected.getDayOfWeek().toString().equals("SATURDAY")) { // cant go on saturday
                    dateSelected=dateSelected.plusDays(1);
                }
                TextView dateChoosen= view.findViewById(R.id.date_text_timepickPage);
                dateChoosen.setText( dateSelected.toString());
                TextView dayString= view.findViewById(R.id.dattextViewTPP);
                dayString.setText( dateSelected.getDayOfWeek().toString());
                Executor executor= Executors.newFixedThreadPool(3);
                executor.execute(new LoadTorsThread(dateSelected.toString()));
                Handler handler=new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        recycleActionTimePick(view);
                    }
                },200);
            }
        });

        Button priviousBut=view.findViewById(R.id.priviousDateBut);
        priviousBut.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                dateSelected=dateSelected.minusDays(1);
                if (dateSelected.getDayOfWeek().toString().equals("SATURDAY")) // cant go on saturday
                    dateSelected=dateSelected.minusDays(1);
                TextView dateChoosen= view.findViewById(R.id.date_text_timepickPage);
                dateChoosen.setText( dateSelected.toString());
                TextView dayString= view.findViewById(R.id.dattextViewTPP);
                dayString.setText( dateSelected.getDayOfWeek().toString());
                Executor executor= Executors.newFixedThreadPool(3);
                executor.execute(new LoadTorsThread(dateSelected.toString()));
                Handler handler=new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        recycleActionTimePick(view);
                    }
                },200);
            }
        });


        return view;
    }

    public void recycleActionTimePick(View view){
        RecyclerView recyclerView= (RecyclerView) view.findViewById(R.id.my_recycler_view);
        LinearLayoutManager layoutManager= new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        recyclerView.setItemAnimator(new DefaultItemAnimator());

        ArrayList<Tor> dataSet = new ArrayList<Tor>();

        MainActivity mainActivity= (MainActivity) getActivity();
//        mainActivity.readData(dataSet,dateSelected,view);
        for (int i=0; i<MyData.torList.length; i++)
        {
            dataSet.add(new Tor(MyData.torList[i].getNameOf(),
                    torList[i].getPhoneOf(),
                    MyData.torList[i].getDate(),
                    MyData.torList[i].getStartTime(),
                    MyData.torList[i].getEndTime(),
                    MyData.torList[i].getPurpose()
//                    String name, LocalDate date, LocalTime startTime, LocalTime endTime, String purpose
            ));
        }

        CustomAdapter addapter = new CustomAdapter(dataSet);
        recyclerView.setAdapter(addapter);
    }
}