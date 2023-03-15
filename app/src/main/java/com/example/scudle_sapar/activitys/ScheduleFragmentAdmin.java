package com.example.scudle_sapar.activitys;

import static com.example.scudle_sapar.addapters.MyData.dataSetUsres;
import static com.example.scudle_sapar.addapters.MyData.dateSelected;
import static com.example.scudle_sapar.addapters.MyData.timeSelected;
import static com.example.scudle_sapar.addapters.MyData.torList;

import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
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
import com.example.scudle_sapar.services.User;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ScheduleFragmentAdmin#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ScheduleFragmentAdmin extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ScheduleFragmentAdmin() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ScheduleFragmentAdmin.
     */
    // TODO: Rename and change types and number of parameters
    public static ScheduleFragmentAdmin newInstance(String param1, String param2) {
        ScheduleFragmentAdmin fragment = new ScheduleFragmentAdmin();
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

        TextView dateChoosen= view.findViewById(R.id.DateViewAdmin_schedulePage);
        dateChoosen.setText( dateSelected.toString());
        TextView dayString= view.findViewById(R.id.dayTextAdminTime);
        dayString.setText( dateSelected.getDayOfWeek().toString());

        recycleAction(view);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_schedule_admin, container, false);

        Button backHomeBut=view.findViewById(R.id.backBut_SchedulePage);
        backHomeBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity mainActivity = (MainActivity) getActivity();
                mainActivity.loadData(LocalDate.now().toString());
                Navigation.findNavController(view).navigate(R.id.schedule_back_home);
            }
        });

        Button backBut=view.findViewById(R.id.backButToSchedule);
        backBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity mainActivity = (MainActivity) getActivity();
                mainActivity.loadData(LocalDate.now().toString());
                Navigation.findNavController(view).navigate(R.id.action_scheduleFragmentAdmin_to_calendarAdminFragment);
            }
        });

        Button timePicker = view.findViewById(R.id.time_picker_but_SchedulePage);
        timeSelected= LocalTime.of(8,0);
        timePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog.OnTimeSetListener onTimeSetListener=new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int selectedMinute) {
                        timeSelected= LocalTime.of(hourOfDay, selectedMinute);
                        timePicker.setText( timeSelected.toString());
                    }
                };
                int style= AlertDialog.THEME_HOLO_DARK;
                TimePickerDialog timePickerDialog= new TimePickerDialog(v.getContext(),style, onTimeSetListener, timeSelected.getHour(),timeSelected.getMinute(),true);
                timePickerDialog.setTitle("Select Time");
                timePickerDialog.show();
            }
        });

        final User[] selectedUser = {new User()};

        Button cancelBut=view.findViewById(R.id.cancel_scheduleButt);
        cancelBut.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                int hour=timeSelected.getHour();
                if ((!torList[hour-8].getNameOf().equals("Avilable"))&&(!torList[hour-8].getNameOf().equals("Not-Avilable"))){
                    torList[hour-8].setNameOf("Avilable");
                    torList[hour-8].setPurpose("--");
                    for (User user: dataSetUsres) {
                        if (torList[hour-8].getPhoneOf().equals(user.getPhone())){
                            selectedUser[0] =user;
                            break;
                        }
                    }
                    torList[hour-8].setPhoneOf("*00");
                    MainActivity mainActivity= (MainActivity) getActivity();
                    mainActivity.cancelApp(selectedUser[0], torList[hour-8]);
                    Toast.makeText(v.getContext(),"Appointment has cancelled ", Toast.LENGTH_LONG).show();
                    recycleAction(view);
                    v.refreshDrawableState();
                }
            }
        });

        Button notAvilBut=view.findViewById(R.id.notAvilButSchedule);
        notAvilBut.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                int hour=timeSelected.getHour();
                if (torList[hour-8].getNameOf().equals("Avilable")){
                    torList[hour-8].setNameOf("Not-Avilable");
                    torList[hour-8].setPurpose("--");
                    MainActivity mainActivity= (MainActivity) getActivity();
                    mainActivity.writeDataDao("Not-Avilable","*00",dateSelected,torList[hour-8].getStartTime(),torList[hour-8].getEndTime(),"--");
                    Toast.makeText(v.getContext(),"Marked as not avilable ", Toast.LENGTH_LONG).show();
                    recycleAction(view);
                    v.refreshDrawableState();
                }
            }
        });

        Button markAvilBut=view.findViewById(R.id.markAvilButSchedule);
        markAvilBut.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                int hour=timeSelected.getHour();
                if (torList[hour-8].getNameOf().equals("Not-Avilable")){
                    torList[hour-8].setNameOf("Avilable");
                    MainActivity mainActivity= (MainActivity) getActivity();
                    mainActivity.writeDataDao("Avilable","*00",dateSelected ,torList[hour-8].getStartTime(),torList[hour-8].getEndTime(),"--");
                    Toast.makeText(v.getContext(),"Marked as not avilable ", Toast.LENGTH_LONG).show();
                    recycleAction(view);
                    v.refreshDrawableState();
                }
            }
        });

        Button takeDayOffBut= view.findViewById(R.id.makeDayoff_SchedulePage);
        takeDayOffBut.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                MainActivity mainActivity= (MainActivity) getActivity();

                for (int i=0;i<10;i++) {
                    if (torList[i].getNameOf().equals("Avilable")) {
                        torList[i].setNameOf("Not-Avilable");
                        mainActivity.writeDataDao("Not-Avilable", "*00", dateSelected, LocalTime.of(i+8,0), LocalTime.of(i+9,0), "--");
                    } else {
                        if (((!torList[i].getNameOf().equals("Avilable")) && (!torList[i].getNameOf().equals("Not-Avilable")))) {
                            for (User user : dataSetUsres) {
                                if (torList[i].getPhoneOf().equals(user.getPhone())) {
                                    selectedUser[0] = user;
                                    break;
                                }
                            }
                            torList[i].setNameOf("Not-Avilable");
                            torList[i].setPurpose("--");
                            torList[i].setPhoneOf("*00");
                            mainActivity.writeDataDao("Not-Avilable", "*00", torList[i].getDate(), torList[i].getStartTime(), torList[i].getEndTime(), "--");
                            mainActivity.cancelAppNoAVil(selectedUser[0], torList[i]);
                        }
                    }
                }

                recycleAction(view);
                v.refreshDrawableState();
            }
        });


        Button cancelDayOffBut = view.findViewById(R.id.cancelDayoff_SchedulePage);
        cancelDayOffBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity mainActivity= (MainActivity) getActivity();
                for (int i=0;i<10;i++) {
                    if (torList[i].getNameOf().equals("Not-Avilable")) {
                        torList[i].setNameOf("Avilable");
                        mainActivity.writeDataDao("Avilable", "*00", dateSelected, LocalTime.of(i+8,0), LocalTime.of(i+9,0), "--");
                    }
                    else
                        break;
                }
                recycleAction(view);
                v.refreshDrawableState();

            }
        });

        Button forwordBut=view.findViewById(R.id.forwordButAdmin);
        forwordBut.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                dateSelected=dateSelected.plusDays(1);
                if (dateSelected.getDayOfWeek().toString().equals("SATURDAY")) { // cant go on saturday
                    dateSelected=dateSelected.plusDays(1);
                }
                TextView dateChoosen= view.findViewById(R.id.DateViewAdmin_schedulePage);
                dateChoosen.setText( dateSelected.toString());
                TextView dayString= view.findViewById(R.id.dayTextAdminTime);
                dayString.setText( dateSelected.getDayOfWeek().toString());

                Executor executor= Executors.newFixedThreadPool(3);
                executor.execute(new LoadTorsThread(dateSelected.toString()));
                Handler handler=new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        recycleAction(view);
                    }
                },200);
            }
        });

        Button prevBut=view.findViewById(R.id.prevButAdmin);
        prevBut.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                dateSelected=dateSelected.minusDays(1);
                if (dateSelected.getDayOfWeek().toString().equals("SATURDAY")) { // cant go on saturday
                    dateSelected=dateSelected.minusDays(1);
                }
                TextView dateChoosen= view.findViewById(R.id.DateViewAdmin_schedulePage);
                dateChoosen.setText( dateSelected.toString());
                TextView dayString= view.findViewById(R.id.dayTextAdminTime);
                dayString.setText( dateSelected.getDayOfWeek().toString());

                Executor executor= Executors.newFixedThreadPool(3);
                executor.execute(new LoadTorsThread(dateSelected.toString()));
                Handler handler=new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        recycleAction(view);
                    }
                },200);
            }
        });


        return view;
    }


    public void recycleAction(View view){
        RecyclerView recyclerView= (RecyclerView) view.findViewById(R.id.schedule_recycle_view);
        LinearLayoutManager layoutManager= new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        recyclerView.setItemAnimator(new DefaultItemAnimator());

        ArrayList<Tor> dataSet = new ArrayList<Tor>();

        MainActivity mainActivity= (MainActivity) getActivity();
//        mainActivity.readData(dataSet,dateSelected,view);
        for (int i = 0; i< MyData.torList.length; i++)
        {
            dataSet.add(new Tor(MyData.torList[i].getNameOf(),
                    MyData.torList[i].getPhoneOf(),
                    MyData.torList[i].getDate(),
                    MyData.torList[i].getStartTime(),
                    MyData.torList[i].getEndTime(),
                    MyData.torList[i].getPurpose()
            ));
        }

        CustomAdapter addapter = new CustomAdapter(dataSet);
        recyclerView.setAdapter(addapter);
    }

    public void alertMessege(View view, boolean[] flag){
        AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
        builder.setMessage("You have a meeting that day, would you like to take time off anyway? ")
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        flag[0] =true;
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        flag[0] = false;
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();

    }

}