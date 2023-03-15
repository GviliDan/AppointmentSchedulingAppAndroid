package com.example.scudle_sapar.activitys;

import static com.example.scudle_sapar.addapters.MyData.dateSelected;

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

import com.example.scudle_sapar.R;
import com.example.scudle_sapar.addapters.CustomAdapter;
import com.example.scudle_sapar.addapters.MyData;
import com.example.scudle_sapar.myThreads.LoadTorsThread;
import com.example.scudle_sapar.services.Tor;
import com.example.scudle_sapar.services.User;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AdminPageFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AdminPageFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public AdminPageFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AdminPageFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AdminPageFragment newInstance(String param1, String param2) {
        AdminPageFragment fragment = new AdminPageFragment();
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

        TextView dateView= view.findViewById(R.id.dateView_admin_page);
        dateView.setText(LocalDate.now().toString());

        Executor executor= Executors.newSingleThreadExecutor();
        executor.execute(new LoadTorsThread(LocalDate.now().toString()));

        Handler handler=new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                view.refreshDrawableState();
                System.out.println("refresh");
                recycleActionAdmin(view);
            }
        },50);



    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_admin_page, container, false);

        Button toMycustome=view.findViewById(R.id.to_my_custome_butt);
        toMycustome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity mainActivity= (MainActivity) getActivity();
                Navigation.findNavController(view).navigate(R.id.adminToMyCustomers);
            }
        });

        Button toCalendar=view.findViewById(R.id.calanderButtonAdmin);
        toCalendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).navigate(R.id.adminToCalaendar);
            }
        });

        Button logOutButt= view.findViewById(R.id.log_out_but_admin_page);
        logOutButt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyData.conectedUser= new User();
                Navigation.findNavController(view).navigate(R.id.adminLogOutHome);
            }
        });

        return view;
    }

    public void recycleActionAdmin(View view){
        RecyclerView recyclerView= (RecyclerView) view.findViewById(R.id.today_recycle_view);
        LinearLayoutManager layoutManager= new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        recyclerView.setItemAnimator(new DefaultItemAnimator());

        ArrayList<Tor> dataSet = new ArrayList<Tor>();

        for (int i = 0; i< MyData.torList.length; i++)
        {
            dataSet.add(new Tor(MyData.torList[i].getNameOf(),
                    MyData.torList[i].getPhoneOf(),
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