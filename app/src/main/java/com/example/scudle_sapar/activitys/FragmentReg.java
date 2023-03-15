package com.example.scudle_sapar.activitys;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.scudle_sapar.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentReg#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentReg extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public FragmentReg() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentReg.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentReg newInstance(String param1, String param2) {
        FragmentReg fragment = new FragmentReg();
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
        // Inflate the layout for this fragment
        View view=  inflater.inflate(R.layout.fragment_reg, container, false);

        EditText emailText=view.findViewById(R.id.Email_RegPage);
        EditText passText=view.findViewById(R.id.Pass_RegPage);
        EditText phoneText=view.findViewById(R.id.Phone_RegPage);
        EditText NameText=view.findViewById(R.id.FullName_RegPage);

        Button register=view.findViewById(R.id.buttonReg_RegPage);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity mainActivity= (MainActivity) getActivity();
                mainActivity.funcReg(emailText, passText, view, phoneText, NameText);



            }
        });


        return view;
    }
}