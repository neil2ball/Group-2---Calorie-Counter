package com.example.group2_caloriecounter;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    String food;
    String calories;
    Button buttonEnter, buttonViewGraph, buttonList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        food = findViewById(R.id.editTextFood).toString();
        calories = findViewById(R.id.editTextCalories).toString();

        buttonEnter = findViewById(R.id.buttonEnter);
        buttonViewGraph = findViewById(R.id.buttonViewGraph);
        buttonList = findViewById(R.id.buttonList);

        buttonEnter.setOnClickListener(this);
        buttonViewGraph.setOnClickListener(this);
        buttonList.setOnClickListener(this);

    }

    public void onClick(View v){
        switch (v.getId()) {
            case R.id.buttonEnter:

                food = findViewById(R.id.editTextFood).toString();
                calories = findViewById(R.id.editTextCalories).toString();



                break;

            case R.id.buttonViewGraph:

                Intent graph = new Intent (getApplicationContext(), GraphActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                startActivity(graph);

                break;

            case R.id.buttonList:
                Intent list = new Intent (getApplicationContext(), ListActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                startActivity(list);
        }
    }
}
