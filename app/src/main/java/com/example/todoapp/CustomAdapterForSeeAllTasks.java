package com.example.todoapp;

import android.content.Context;
import android.content.Intent;
import android.opengl.Visibility;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CustomAdapterForSeeAllTasks extends RecyclerView.Adapter<CustomAdapterForSeeAllTasks.CustomViewHolder> {

    ArrayList<seeAllTaskCardDatatype> list;

    Context context;

    CustomAdapterForSeeAllTasks(ArrayList<seeAllTaskCardDatatype> listCame,Context activityContext){
        list = listCame;

        context = activityContext;
    }

    DatabaseHelper db = new DatabaseHelper(context);

    public static class CustomViewHolder extends RecyclerView.ViewHolder {
        private TextView task;
        private TextView iid;


        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);
            task = itemView.findViewById(R.id.task);
            iid = itemView.findViewById(R.id.id);

        }
    }

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_for_see_all_task,parent,false);
        CustomViewHolder customViewHolder = new CustomViewHolder(view);
        return customViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {
        seeAllTaskCardDatatype obj = list.get(position);
        holder.task.setText(obj.getText());
        holder.iid.setText(String.valueOf(position+1));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }



}
