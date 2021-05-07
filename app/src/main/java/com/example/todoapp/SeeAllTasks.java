package com.example.todoapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class SeeAllTasks extends AppCompatActivity {
    DatabaseHelperForInProgress inDb;
    DatabaseHelper db;
    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager layoutManager;
    Context context;
    EditText editText;
    Cursor cursor;
    Button button;
    ArrayList<seeAllTaskCardDatatype> listOfCards;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getApplicationContext();
        setContentView(R.layout.activity_see_all_tasks);
        editText = findViewById(R.id.idOfTaskToMove);
        button = findViewById(R.id.moveToProgress);
        db = new DatabaseHelper(this);
        inDb = new DatabaseHelperForInProgress(this);
        cursor = db.getAllData();

        String[] id;
        String[] tasks;



            id =  new String[cursor.getCount()];
            tasks = new String[cursor.getCount()];
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

            listOfCards = new ArrayList<>();
            for(int j=0;j<cursor.getCount();j++){
                listOfCards.add(new seeAllTaskCardDatatype(tasks[j],id[j]));
            }







        recyclerView = findViewById(R.id.seeAllTaskRecycler);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new CustomAdapterForSeeAllTasks(listOfCards,context);
        recyclerView.setAdapter(adapter);

         move();



    }//onCreate ends

    public void move(){
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String idToMove = editText.getText().toString();
                if(TextUtils.isEmpty(idToMove)){
                    editText.setError("Enter Id to set it in progress");
                    return;
                }
                int intIdToMove = Integer.parseInt(idToMove);
                if(intIdToMove > 0 && intIdToMove <= cursor.getCount()){
                    int realIdToDelete = intIdToMove - 1;


                        boolean isDeleted = db.delete(listOfCards.get(realIdToDelete).getId());
                        boolean isInsertedToInDb = inDb.insert(listOfCards.get(realIdToDelete).getText());

                        if(isInsertedToInDb == true){
                            Toast.makeText(context,"Task is In Progress",Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(context,InProgress.class);
                            startActivity(intent);
                            editText.setText("");
                            listOfCards.remove(listOfCards.get(realIdToDelete));
                            adapter.notifyItemRemoved(realIdToDelete);
                            recyclerView.setAdapter(adapter);
                        }else{
                            Toast.makeText(context,"Problem while Setting in Progress",Toast.LENGTH_SHORT).show();
                        }



                }else{
                    editText.setError("Enter valid Id");
                }

            }
        });
    }


}