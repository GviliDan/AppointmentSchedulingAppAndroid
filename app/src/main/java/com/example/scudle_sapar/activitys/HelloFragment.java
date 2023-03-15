package com.example.scudle_sapar.activitys;

import static com.example.scudle_sapar.addapters.MyData.conectedUser;
import static com.example.scudle_sapar.addapters.MyData.dateSelected;
import static com.example.scudle_sapar.addapters.MyData.timeSelected;
import static com.example.scudle_sapar.addapters.MyData.torList;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.scudle_sapar.R;
import com.example.scudle_sapar.services.User;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HelloFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HelloFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HelloFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HelloFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HelloFragment newInstance(String param1, String param2) {
        HelloFragment fragment = new HelloFragment();
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
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//        HelloFragmentArgs args= HelloFragmentArgs.fromBundle(getArguments());
//        String email= args.getEmail();

        TextView name=view.findViewById(R.id.Name_helloPage);
        name.setText(conectedUser.getFullName());
        TextView nDate= view.findViewById(R.id.date_textView_helloP);
        TextView nTime= view.findViewById(R.id.timetextView_hellpP);
        TextView nPurpos= view.findViewById(R.id.purpos_textView_helloP);

        nPurpos.setText(conectedUser.getNextTor().getPurpose());
        LocalDateTime l1= LocalDateTime.of(conectedUser.getNextTor().getDate(), conectedUser.getNextTor().getStartTime());
        if ((conectedUser.getNextTor().getPurpose()!= "No Appointment exsist") && (l1.isAfter(LocalDateTime.now()))) {
            nDate.setText(conectedUser.getNextTor().getDate().toString());
            nTime.setText(conectedUser.getNextTor().getStartTime().toString());
        }
        else {
            nDate.setText(" -- ");
            nTime.setText(" -- ");
            nPurpos.setText("No Appointment exsist");
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_hello, container, false);

        Button makeAp= view.findViewById(R.id.makeApBut_HelloPage);
        makeAp.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                LocalDateTime l2= LocalDateTime.of(conectedUser.getNextTor().getDate(),conectedUser.getNextTor().getStartTime());
                if ((conectedUser.getNextTor().getPurpose()== "No Appointment exsist") || l2.isBefore(LocalDateTime.now())) {
                    Navigation.findNavController(view).navigate(R.id.hello_to_calendar);
                }
                else{
                    Toast.makeText(v.getContext(),"already have an appointment", Toast.LENGTH_LONG).show();
                }
            }
        });

        Button cancelApBut=view.findViewById(R.id.cancellAppBut_HelloPage);
        cancelApBut.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                TextView nDate= view.findViewById(R.id.date_textView_helloP);
                TextView nTime= view.findViewById(R.id.timetextView_hellpP);
                TextView nPurpos= view.findViewById(R.id.purpos_textView_helloP);
                MainActivity mainActivity= (MainActivity) getActivity();
                if ((conectedUser.getNextTor().getPurpose()== "No Appointment exsist") || conectedUser.getNextTor().getDate().isBefore(LocalDate.now())) {
                    Toast.makeText(v.getContext(),"No Appointment exsist to cancel", Toast.LENGTH_LONG).show();
                }
                else{
                    mainActivity.cancelApp(conectedUser, conectedUser.getNextTor());
                    nPurpos.setText(conectedUser.getNextTor().getPurpose());
                    nDate.setText(" -- ");
                    nTime.setText(" -- ");
                }
            }
        });

        Button loguot_but= view.findViewById(R.id.logOutBut_HelloPage);
        loguot_but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                conectedUser=new User();
                Navigation.findNavController(view).navigate(R.id.hello_exit_to_wellcome);
            }
        });

        return view;
    }
}