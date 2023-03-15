package com.example.scudle_sapar.activitys;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.scudle_sapar.R;
import com.example.scudle_sapar.myThreads.LoadTorsThread;
import com.example.scudle_sapar.myThreads.LoadUsersThread;

import java.time.LocalDate;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentRegLog#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentRegLog extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public FragmentRegLog() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentRegLog.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentRegLog newInstance(String param1, String param2) {
        FragmentRegLog fragment = new FragmentRegLog();
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

// log-in
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_reg_log, container, false);
        EditText phoneText=view.findViewById(R.id.Phone_textLogPage);
        EditText passText=view.findViewById(R.id.PassRegLogPage);

//        MainActivity mainActivity = (MainActivity) getActivity();
//        mainActivity.loadData(LocalDate.now().toString());

        Executor executor= Executors.newSingleThreadExecutor();
        executor.execute(new LoadTorsThread(LocalDate.now().toString()));

        Button buttonReg = view.findViewById(R.id.buttonReg_RegLogPage);

        buttonReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.HometoRegister);
            }
        });


        Button buttonLog =view.findViewById(R.id.buttonLog_RegLogPage);
        buttonLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity mainActivity= (MainActivity) getActivity();
                if (!phoneText.getText().toString().isEmpty())
                    mainActivity.readAndLogin(phoneText, passText, view);
                else
                    Toast.makeText(v.getContext(), "No phone entered  ", Toast.LENGTH_LONG).show();
            }
        });


        Button buttonAdminLog =view.findViewById(R.id.admin_enter_but);
        buttonAdminLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (phoneText.getText().toString().equals("055")) {
                    Executor executor= Executors.newSingleThreadExecutor();
                    executor.execute(new LoadUsersThread());
                    MainActivity mainActivity = (MainActivity) getActivity();
                    mainActivity.funcAdminLogin(passText, view);
//                    mainActivity.loadUsers();
                }
            }
        });



        return view;
    }



}
