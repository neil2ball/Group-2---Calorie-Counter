package com.example.group2_caloriecounter;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    EditText editTextFood;
    EditText editTextCount;

    String food;
    String count;
    String date;

    Date dateDate;

    RequestQueue requestQueue;

    JSONObject postparams;

    Intent list;


    Button buttonEnter, buttonViewGraph, buttonList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextFood = findViewById(R.id.editTextFood);
        editTextCount = findViewById(R.id.editTextCalories);

        food = editTextFood.getText().toString();
        count = editTextCount.getText().toString();

        buttonEnter = findViewById(R.id.buttonEnter);
        buttonViewGraph = findViewById(R.id.buttonViewGraph);
        buttonList = findViewById(R.id.buttonList);

        buttonEnter.setOnClickListener(this);
        buttonViewGraph.setOnClickListener(this);
        buttonList.setOnClickListener(this);

        requestQueue = Volley.newRequestQueue(this);

    }

    public void onClick(View v){
        switch (v.getId()) {
            case R.id.buttonEnter:

                food = editTextFood.getText().toString();
                count = editTextCount.getText().toString();

                dateDate = Calendar.getInstance().getTime();

                date = dateDate.toString();


                String url = "http://192.168.0.19:8080/employees";


                postparams = new JSONObject();

                try {
                    postparams.put("food", food);
                    postparams.put("count", count);
                    postparams.put("date", date);
                }
                catch (JSONException e) {
                    e.printStackTrace();
                }

                JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                        url, postparams,
                        new Response.Listener() {
                            @Override
                            public void onResponse(Object response) {

                            }


                            public void onResponse(JSONObject response) {
                                //Success Callback
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                //Failure Callback
                            }
                        });



                requestQueue.add(jsonObjReq);

                try {
                    Thread.sleep(1000);
                }
                catch (InterruptedException e) {
                    e.printStackTrace();
                }

                list = new Intent (getApplicationContext(), ListActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                startActivity(list);


                break;

            case R.id.buttonViewGraph:

                list = new Intent (getApplicationContext(), GraphActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                startActivity(list);

                break;

            case R.id.buttonList:

                list = new Intent (getApplicationContext(), ListActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                startActivity(list);

                break;
        }
    }
}
