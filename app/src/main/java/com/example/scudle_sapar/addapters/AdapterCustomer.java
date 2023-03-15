package com.example.scudle_sapar.addapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.scudle_sapar.R;
import com.example.scudle_sapar.services.Tor;
import com.example.scudle_sapar.services.User;

import org.jetbrains.annotations.TestOnly;

import java.util.ArrayList;

public class AdapterCustomer extends RecyclerView.Adapter<AdapterCustomer.MyViewHolder>{

    private ArrayList<User> dataSet;

    public AdapterCustomer(ArrayList<User> dataSet){
        this.dataSet=dataSet;
    }

    @NonNull
    @Override
    public AdapterCustomer.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.card_layout_customer, parent,false);
        AdapterCustomer.MyViewHolder myViewHolder=new AdapterCustomer.MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterCustomer.MyViewHolder holder, int position) {

        TextView fullname= holder.fullName;
        TextView textView_email=holder.email;
        TextView phone=holder.phone;
        CardView cardView= holder.cardView;

        fullname.setText(dataSet.get(position).getFullName());
        textView_email.setText(dataSet.get(position).getEmail());
        phone.setText(dataSet.get(position).getPhone());

    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        CardView cardView;
        TextView fullName;
        TextView email;
        TextView phone;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView= (CardView) itemView.findViewById(R.id.cardViewCustomer);
            fullName= (TextView) itemView.findViewById(R.id.name_customer_card);
            email= (TextView) itemView.findViewById(R.id.email_customer_card);
            phone= (TextView) itemView.findViewById(R.id.phone_customer_card);

        }
    }
}
