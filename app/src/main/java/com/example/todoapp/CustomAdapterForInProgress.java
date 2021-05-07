package com.example.todoapp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CustomAdapterForInProgress extends RecyclerView.Adapter<CustomAdapterForInProgress.CustomViewHolder> {

    static  ArrayList<seeAllTaskCardDatatype> list;
    Context context;
    static OnCardviewButtonListener onCardviewButtonListener;
    CustomAdapterForInProgress(ArrayList<seeAllTaskCardDatatype> passedList,Context passedContext,OnCardviewButtonListener onCardviewButtonListener){
        list = passedList;
        context = passedContext;
        this.onCardviewButtonListener = onCardviewButtonListener;
    }

    public interface OnCardviewButtonListener{
        void performActionOnPosition(View v,seeAllTaskCardDatatype obj,int pos);
    }

    public static class CustomViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView textView;
        private Button button;

        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.taskInProgress);
            button = itemView.findViewById(R.id.moveToCompletedTask);
            button.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onCardviewButtonListener.performActionOnPosition(v,list.get(getAdapterPosition()),getAdapterPosition());
        }
    }



    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_for_in_progress,parent,false);
        CustomViewHolder customViewHolder = new CustomViewHolder(view);
        return customViewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, int position){
           seeAllTaskCardDatatype obj = list.get(position);
           holder.textView.setText(obj.getText());

    }



    @Override
    public int getItemCount() {
        return list.size();
    }




}
