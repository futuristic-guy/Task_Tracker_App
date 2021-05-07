package com.example.todoapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    DatabaseHelper db;
    Cursor cursor;
    Button addTaskBtn,inProgressBtn, completedBtn,seeAllBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        db = new DatabaseHelper(this);
        addTaskBtn = findViewById(R.id.addTask);
        inProgressBtn = findViewById(R.id.inProgress);
        completedBtn = findViewById(R.id.completedTasks);
        seeAllBtn = findViewById(R.id.allTasksBtn);

        cursor = db.getAllData();

        goToAddTaskActivity();
        goToInProgressActivity();
        goToCompletedTasksActivity();
        goToSeeAllTaskActivity();

    }//onCreate ends

    public void goToSeeAllTaskActivity() {
        if (cursor.getCount() == 0) {
            seeAllBtn.setEnabled(false);
        } else {

            seeAllBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(MainActivity.this, SeeAllTasks.class);
                    startActivity(intent);
                }
            });
        }
    }

    public void goToAddTaskActivity(){
        addTaskBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,AddTask.class);
                startActivity(intent);
            }
        });
    }

    public void goToInProgressActivity(){
        inProgressBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,InProgress.class);
                startActivity(intent);
            }
        });
    }

    public void  goToCompletedTasksActivity(){
        completedBtn.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(MainActivity.this,CompletedTasks.class);
                        startActivity(intent);
                    }
                }
        );
    }


public void onRestart(){
        this.recreate();
        super.onRestart();
}



}