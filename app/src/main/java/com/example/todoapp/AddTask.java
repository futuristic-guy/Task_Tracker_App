package com.example.todoapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddTask extends AppCompatActivity {
    DatabaseHelper db;
    EditText editTask;
    Button insertToDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);
        db = new DatabaseHelper(this);
        editTask = findViewById(R.id.writtenTask);
        insertToDb  = findViewById(R.id.addTaskToDb);

        addTaskToDb();

    }

    public void addTaskToDb(){
        insertToDb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                  String task = editTask.getText().toString();

                  if(task.equals(String.valueOf(""))){
                      editTask.setError("Enter Task to be Done");
                      return;
                  }
                boolean isInserted =  db.insert(task);
                 if(isInserted == true){
                     Toast.makeText(AddTask.this,"Task Added !",Toast.LENGTH_SHORT).show();

                     Intent intent = new Intent(AddTask.this,SeeAllTasks.class);
                     startActivity(intent);
                     editTask.setText("");
                 } else {
                     Toast.makeText(AddTask.this,"Some went Wront",Toast.LENGTH_SHORT).show();
                 }


            }
        });
    }

}