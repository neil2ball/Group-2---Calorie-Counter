package com.example.group2_caloriecounter;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by User on 1/2/2018.
 */

public class ModifyActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "ModifyActivity";

    EditText foodEditText;
    EditText countEditText;
    TextView dateTextView;
    TextView idTextView;

    Button btnUpdate, btnDelete;

    JSONObject postParams;

    RequestQueue requestQueue;

    Intent list;

    String url = "http://192.168.0.19:8080/employees";

    JsonObjectRequest jsonObjReq;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify);
        Log.d(TAG, "onCreate: started.");

        foodEditText = findViewById(R.id.food_edittext);
        countEditText = findViewById(R.id.count_edittext);
        dateTextView = findViewById(R.id.dateTextView);
        idTextView = findViewById(R.id.idTextView);


        btnUpdate = findViewById(R.id.btn_update);
        btnDelete = findViewById(R.id.btn_delete);

        btnUpdate.setOnClickListener(this);
        btnDelete.setOnClickListener(this);

        requestQueue = Volley.newRequestQueue(this);

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


        foodEditText.setText(foodString);
        countEditText.setText(countString);
        dateTextView.setText(dateString);
        idTextView.setText(idString);

    }

    public void onClick(View v){
        switch (v.getId()) {
            case R.id.btn_update:

                url += "/" + idTextView.getText().toString();


                postParams = new JSONObject();

                try {
                    postParams.put("food", foodEditText.getText().toString());
                    postParams.put("count", countEditText.getText().toString());
                    postParams.put("date", dateTextView.getText().toString());
                }
                catch (JSONException e) {
                    e.printStackTrace();
                }

                jsonObjReq = new JsonObjectRequest(Request.Method.PUT,
                        url, postParams,
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

                list = new Intent (getApplicationContext(), MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                startActivity(list);

                break;

            case R.id.btn_delete:

                url += "/" + idTextView.getText().toString();


                jsonObjReq = new JsonObjectRequest(Request.Method.DELETE,
                        url, null,
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


                list = new Intent (getApplicationContext(), MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                startActivity(list);

                break;
        }
    }

}