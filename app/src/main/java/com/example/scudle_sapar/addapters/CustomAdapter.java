package com.example.scudle_sapar.addapters;

import static com.example.scudle_sapar.addapters.MyData.dateSelected;
import static com.example.scudle_sapar.addapters.MyData.timeSelected;

import android.graphics.Color;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.scudle_sapar.R;
import com.example.scudle_sapar.services.Tor;

import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Stack;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {

    private ArrayList<Tor> dataSet;

    public CustomAdapter(ArrayList<Tor> dataSet) {

        this.dataSet = dataSet;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        CardView cardView;
        TextView textView_start;
//        TextView textView_end;
        TextView isAvilabile;
        TextView purpose;
        String phone;
        boolean selected;


        public MyViewHolder(View itemView)
        {
            super(itemView);
            cardView= (CardView) itemView.findViewById(R.id.card_view);
            textView_start=( TextView) itemView.findViewById(R.id.start_time_card);
            isAvilabile= ( TextView) itemView.findViewById(R.id.name_card);
            purpose= ( TextView) itemView.findViewById(R.id.purpose_card);



            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    selected = !selected;
                    cardView.setCardBackgroundColor(selected ?
                            Color.BLUE : Color.LTGRAY);
                    notifyItemChanged(getAdapterPosition());
                }
            });

        }
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.card_layout, parent,false);
        MyViewHolder myViewHolder=new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CustomAdapter.MyViewHolder viewHolder, int listPosition) {
        TextView textView_start = viewHolder.textView_start;
        TextView isAvilabile=viewHolder.isAvilabile;
        TextView purpose=viewHolder.purpose;
        CardView cardView= viewHolder.cardView;
        String phone=viewHolder.phone;

        SimpleDateFormat dateFormat= new SimpleDateFormat("HH:mm");

        textView_start.setText(dataSet.get(listPosition).getStartTime().toString());
//        textView_end.setText(dataSet.get(listPosition).getEndTime().toString());
        isAvilabile.setText(dataSet.get(listPosition).getNameOf());
        purpose.setText(dataSet.get(listPosition).getPurpose());
        phone= dataSet.get(listPosition).getPhoneOf();




        cardView.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                viewHolder.cardView.setCardBackgroundColor(viewHolder.selected ?  Color.BLUE : Color.LTGRAY);
                timeSelected= LocalTime.parse(textView_start.getText().toString());
            }
        });

    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }


}
