package com.example.scudle_sapar.activitys;

import static com.example.scudle_sapar.addapters.MyData.dateSelected;
import static com.example.scudle_sapar.addapters.MyData.torList;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.TextView;

import com.example.scudle_sapar.R;
import com.example.scudle_sapar.myThreads.LoadTorsThread;

import java.time.LocalDate;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CalendarAdminFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CalendarAdminFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public CalendarAdminFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CalendarAdminFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CalendarAdminFragment newInstance(String param1, String param2) {
        CalendarAdminFragment fragment = new CalendarAdminFragment();
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



    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.fragment_calendar_admin, container, false);
        Handler handler= new Handler();

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                view.refreshDrawableState();
            }
        },500);

        dateSelected= LocalDate.now();

        Executor executor= Executors.newFixedThreadPool(3);
        executor.execute(new LoadTorsThread(LocalDate.now().toString()));

        MainActivity mainActivity = (MainActivity) getActivity();
//        mainActivity.loadData(LocalDate.now().toString());
        TextView dateChoosen= view.findViewById(R.id.choosenDateTextCalendarAdmin);
        dateChoosen.setText( dateSelected.toString());

        TextView workNoWork = view.findViewById(R.id.workNoWork);


        TextView countText= view.findViewById(R.id.count_textView19);
        Integer[] count = {countApp(workNoWork)};
        countText.setText(count[0].toString());

        CalendarView calendarView = view.findViewById(R.id.calendarView2);
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {

                dateSelected= LocalDate.of(year,month+1,dayOfMonth);
                dateChoosen.setText(dateSelected.toString());
                System.out.println("date choosen: "+ dateChoosen.getText().toString() );
                executor.execute(new LoadTorsThread(dateSelected.toString()));
//                mainActivity.loadData(dateSelected.toString());

//                countText.setText(count[0].toString());


                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        view.refreshDrawableState();
                        System.out.println("refresh");
                        count[0] = countApp(workNoWork);
                        countText.setText(count[0].toString());
                        if (dateSelected.getDayOfWeek().toString().equals("SATURDAY"))
                            workNoWork.setText("Day-off");
                    }
                },200);

            }

        });

        Button showSchedule= view.findViewById(R.id.showScheduleBut);
        showSchedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).navigate(R.id.calendarAdmin_to_schedule);
            }
        });

        Button backBut= view.findViewById(R.id.backBut_calendarAdminPage);
        backBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).navigate(R.id.calendarBackToAdmin);
            }
        });


        return view;
    }



    private Integer countApp(TextView workNoWork) {
        int avil=0;
        int not_avil=0;
        for (int i=0;i<10;i++){
            if (torList[i].getNameOf().equals("Avilable"))
                avil++;
            if (torList[i].getNameOf().equals("Not-Avilable"))
                not_avil++;
        }
        if (not_avil==10)
            workNoWork.setText("Day-off");
        else
            workNoWork.setText("Work");

        return 10-avil-not_avil;
    }
}