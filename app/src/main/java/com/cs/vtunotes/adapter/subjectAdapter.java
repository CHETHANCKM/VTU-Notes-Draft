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

import com.cs.vtunotes.MainActivity;
import com.cs.vtunotes.R;
import com.cs.vtunotes.login;
import com.cs.vtunotes.models.subjectnameModels;
import com.cs.vtunotes.module_parent;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class subjectAdapter extends RecyclerView.Adapter<subjectAdapter.MyViewHolder> {

    public subjectAdapter(Context context, ArrayList<subjectnameModels> list) {
        this.context = context;
        this.list = list;
    }

    Context context;
    ArrayList<subjectnameModels> list;


    @Override
    public MyViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.list_card_chip, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull MyViewHolder holder, int position) {
        holder.setIsRecyclable(false);
        subjectnameModels subjectnameModels = list.get(position);


        Intent intent = ((Activity) context).getIntent();
        String branch = intent.getStringExtra("branch");
        String db_code = intent.getStringExtra("db_code");
        String semester_name = intent.getStringExtra("semester_name");



        String subjectcode = subjectnameModels.getSubject_code().toString();
        String subjectname = subjectnameModels.getSubject_name().toString();
        String scheme_code = subjectnameModels.getScheme().toString();
//
        holder.subjecname.setText(subjectcode+" - "+subjectname);


        holder.chip_list_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(v.getContext(), module_parent.class);
                i.putExtra("branch", branch);

                i.putExtra("subject_code", subjectcode);
                i.putExtra("subject_name", subjectname);

                i.putExtra("scheme", scheme_code);
                i.putExtra("semester_name",semester_name);

                v.getContext().startActivity(i);
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public  static  class MyViewHolder extends RecyclerView.ViewHolder {

        TextView subjecname;
        FrameLayout chip_list_card;

        public MyViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            subjecname = itemView.findViewById(R.id.card_subjectname);
            chip_list_card = itemView.findViewById(R.id.chip_list_card);
        }
    }
}
