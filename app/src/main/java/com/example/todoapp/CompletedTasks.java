package com.example.todoapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;

public class CompletedTasks extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager layoutManager;
    Cursor cursor;
    DatabaseHelperForCompletedTasks cDb;
    ArrayList<seeAllTaskCardDatatype> completedTasklist;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_completed_tasks);
        cDb = new DatabaseHelperForCompletedTasks(this);
        cursor = cDb.getALLData();

        if(cursor.getCount() != 0 ){

            String[] id = new String[cursor.getCount()];
            String[] tasks = new String[cursor.getCount()];
            int row =0;

            while(cursor.moveToNext()){
                for(int i=0;i<cursor.getColumnCount();i++){
                    if(i == 0){
                        id[row] = cursor.getString(i);
                    }

                    if(i == 1){
                        tasks[row] = cursor.getString(i);
                    }
                }
                row++;
            }

            completedTasklist = new ArrayList<>();

            for(int i=cursor.getCount()-1;i >= 0;i--){
                completedTasklist.add(new seeAllTaskCardDatatype(tasks[i],id[i]));
            }

            recyclerView = findViewById(R.id.completedTasksRecycler);
            recyclerView.setHasFixedSize(true);
            layoutManager = new LinearLayoutManager(this);
            recyclerView.setLayoutManager(layoutManager);
            adapter = new CustomAdapterForSeeAllTasks(completedTasklist,getApplicationContext());
            recyclerView.setAdapter(adapter);

        }else {
            Toast.makeText(getApplicationContext(),"Completed Section is Empty",Toast.LENGTH_LONG).show();
        }

        button = findViewById(R.id.clearHistory);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isDeleted = cDb.deleteAll();

                if(isDeleted == true){
//                    Intent intent = new Intent(getApplicationContext(),MainActivity.class);
//                    startActivity(intent);
                    Toast.makeText(getApplicationContext(),"Cleared Succesfully",Toast.LENGTH_SHORT).show();

                    //code for recreation of activity in smooth way
                    finish();
                    overridePendingTransition(0, 0);
                    startActivity(getIntent());
                    overridePendingTransition(0, 0);

                }else{
                    Toast.makeText(getApplicationContext(),"Can't Be deleted",Toast.LENGTH_SHORT).show();
                }
            }
        });


    }//onCreate Ends
}