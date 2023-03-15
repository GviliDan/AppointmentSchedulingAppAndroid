package com.example.scudle_sapar.activitys;

import static com.example.scudle_sapar.addapters.MyData.dataSetUsres;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.scudle_sapar.R;
import com.example.scudle_sapar.addapters.AdapterCustomer;
import com.example.scudle_sapar.addapters.MyData;
import com.example.scudle_sapar.services.User;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MyCustomersFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MyCustomersFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public MyCustomersFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MyCustomersFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MyCustomersFragment newInstance(String param1, String param2) {
        MyCustomersFragment fragment = new MyCustomersFragment();
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

        RecyclerView recyclerView= (RecyclerView) view.findViewById(R.id.recycleViewCustomes);
        LinearLayoutManager layoutManager= new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        recyclerView.setItemAnimator(new DefaultItemAnimator());

//        ArrayList<User> dataSet = new ArrayList<User>();
//
//        for (int i = 0; i< MyData.customes.length; i++)
//        {
//            dataSet.add(new User(MyData.customes[i].getFullName(),
//                    MyData.customes[i].getEmail(),
//                    MyData.customes[i].getPhone()
//
//            ));
//        }

        AdapterCustomer addapter = new AdapterCustomer(dataSetUsres);
        recyclerView.setAdapter(addapter);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_my_customers, container, false);

        EditText phonenum=view.findViewById(R.id.editTextSearchMyCustomer);
        TextView name=view.findViewById(R.id.nameViewMCP);
        TextView email= view.findViewById(R.id.emailViewMcp);
        Button searchBut= view.findViewById(R.id.search_button_mcp);

        searchBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (User user : dataSetUsres) {
                    if (phonenum.getText().toString().equals(user.getPhone())) {
                        name.setText(user.getFullName());
                        email.setText(user.getEmail());
                        break;
                    }
                    else {
                        name.setText("");
                        email.setText("User doesn't exist");
                    }
                }

            }
            });

        Button backButt=view.findViewById(R.id.backButtonMCP);
        backButt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).navigate(R.id.myCustomersBackToAdmin);
            }
        });



        return view;

    }
}
