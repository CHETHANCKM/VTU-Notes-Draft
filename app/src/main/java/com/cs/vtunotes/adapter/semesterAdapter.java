package com.cs.vtunotes.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cs.vtunotes.R;
import com.cs.vtunotes.models.semesterModel;
import com.cs.vtunotes.physics_chemistry.semesters;
import com.cs.vtunotes.physics_chemistry.subjects1;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class semesterAdapter extends RecyclerView.Adapter<semesterAdapter.MyViewHolder> {

    Context context;
    ArrayList<semesterModel> list;


    public semesterAdapter(Context context, ArrayList<semesterModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @NotNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
         View v = LayoutInflater.from(context).inflate(R.layout.list_card_chip, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull MyViewHolder holder, int position) {
        holder.setIsRecyclable(false);
        semesterModel semesterModel = list.get(position);

         String semester_name = semesterModel.getSem();
         holder.schemecode.setText(semester_name);


        Intent intent = ((Activity) context).getIntent();
        String branch = intent.getStringExtra("branch");
        String db_code = intent.getStringExtra("db_code");
        String scheme_code = intent.getStringExtra("scheme_code");


        holder.chip_list_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(v.getContext(), subjects1.class);
                i.putExtra("branch", branch);
                i.putExtra("db_code", db_code);
                i.putExtra("scheme_code", scheme_code);
                i.putExtra("semester_name", semester_name);
                v.getContext().startActivity(i);
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends  RecyclerView.ViewHolder {
        TextView schemecode;
        FrameLayout chip_list_card;
        public MyViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            schemecode = itemView.findViewById(R.id.card_subjectname);
            chip_list_card = itemView.findViewById(R.id.chip_list_card);

        }
    }
}
