package com.example.group2_caloriecounter;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by User on 1/2/2018.
 */

public class ModifyActivity extends AppCompatActivity {

    private static final String TAG = "ModifyActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify);
        Log.d(TAG, "onCreate: started.");

        getIncomingIntent();
    }

    private void getIncomingIntent(){
        Log.d(TAG, "getIncomingIntent: checking for incoming intents.");

        if(getIntent().hasExtra("food") && getIntent().hasExtra("count")
                && getIntent().hasExtra("date") && getIntent().hasExtra("id")){
            Log.d(TAG, "getIncomingIntent: found intent extras.");

            String foodString = getIntent().getStringExtra("food");
            String countString = getIntent().getStringExtra("count");
            String dateString = getIntent().getStringExtra("date");
            String idString = getIntent().getStringExtra("id");

            setImage(foodString, countString, dateString, idString);
        }
    }


    private void setImage(String foodString, String countString, String dateString, String idString){
        Log.d(TAG, "setImage: setting te image and name to widgets.");

        EditText foodEditText = findViewById(R.id.food_edittext);
        EditText countEditText = findViewById(R.id.count_edittext);
        TextView dateTextView = findViewById(R.id.dateTextView);
        TextView idTextView = findViewById(R.id.idTextView);

        foodEditText.setText(foodString);
        countEditText.setText(countString);
        dateTextView.setText(dateString);
        idTextView.setText(idString);

    }

}