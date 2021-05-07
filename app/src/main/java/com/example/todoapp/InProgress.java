package com.example.todoapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;

public class InProgress extends AppCompatActivity {
    DatabaseHelperForInProgress inDb;
    DatabaseHelperForCompletedTasks cDb;
    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    CustomAdapterForInProgress.OnCardviewButtonListener onCardviewButtonListener;
    RecyclerView.LayoutManager layoutManager;
    ArrayList<seeAllTaskCardDatatype> inProgressList;
    Context context;
    Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_in_progress);
        context = getApplicationContext();
        inDb = new DatabaseHelperForInProgress(this);
        cDb = new DatabaseHelperForCompletedTasks(this);
        cursor = inDb.getAllData();

        if(cursor.getCount() > 0  ){


            String[] id =  new String[cursor.getCount()];
            String[] tasks = new String[cursor.getCount()];
            int row =0;
            int column = 0;
            while(cursor.moveToNext()){

                for(int i=0;i<cursor.getColumnCount();i++){
                    if(i == 0){
                        id[row] = cursor.getString(i);
                    }
                    if(i == 1){
                        tasks[column] = cursor.getString(i);
                    }

                }
                row++;
                column++;
            }



            inProgressList = new ArrayList<>();
            for(int j=0;j<cursor.getCount();j++){
                inProgressList.add(new seeAllTaskCardDatatype(tasks[j],id[j]));
            }

            onCardviewButtonListener = new CustomAdapterForInProgress.OnCardviewButtonListener() {
                @Override
                public void performActionOnPosition(View v, seeAllTaskCardDatatype obj,int pos) {
                    inDb.delete(obj.getId());
                    boolean isInsertedOnCompleteDb = cDb.insert(obj.getText());

                    if(isInsertedOnCompleteDb == true){

                        Toast.makeText(getApplicationContext(),"Task Done",Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(),CompletedTasks.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        recreate();
                    }
                    else {
                        Toast.makeText(getApplicationContext(),"Something Went wrong",Toast.LENGTH_SHORT).show();
                    }

                }
            };

            recyclerView = findViewById(R.id.inProgressRecycler);
            recyclerView.setHasFixedSize(true);
            layoutManager = new LinearLayoutManager(this);
            recyclerView.setLayoutManager(layoutManager);
            adapter = new CustomAdapterForInProgress(inProgressList,context,onCardviewButtonListener);
            recyclerView.setAdapter(adapter);
        }else{
            Toast.makeText(this,"No Task is in Progress",Toast.LENGTH_LONG).show();
        }






    }//onCreate Ends




}