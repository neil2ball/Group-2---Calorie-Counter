package com.example.group2_caloriecounter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.cursoradapter.widget.SimpleCursorAdapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ListActivity extends AppCompatActivity {
    private Context mContext;
    private Activity mActivity;


    private Button mButtonDo;
    private TextView mTextView;
    private String mJSONURLString = "http://pastebin.com/raw/Em972E5s";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        // Get the application context
        mContext = getApplicationContext();
        mActivity = ListActivity.this;

        // Get the widget reference from XML layout
        mTextView = (TextView) findViewById(R.id.tv);


                // Empty the TextView
                mTextView.setText("");

                // Initialize a new RequestQueue instance
                RequestQueue requestQueue = Volley.newRequestQueue(mContext);

                // Initialize a new JsonArrayRequest instance
                JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                        Request.Method.GET,
                        mJSONURLString,
                        null,
                        new Response.Listener<JSONArray>() {
                            @Override
                            public void onResponse(JSONArray response) {
                                // Do something with response
                                //mTextView.setText(response.toString());

                                // Process the JSON
                                try{
                                    // Loop through the array elements
                                    for(int i=0;i<response.length();i++){
                                        // Get current json object
                                        JSONObject student = response.getJSONObject(i);

                                        // Get the current student (json object) data
                                        String firstName = student.getString("firstname");
                                        String lastName = student.getString("lastname");
                                        String age = student.getString("age");

                                        // Display the formatted json data in text view
                                        mTextView.append(firstName +" " + lastName +"\nAge : " + age);
                                        mTextView.append("\n\n");
                                    }
                                }catch (JSONException e){
                                    e.printStackTrace();
                                }
                            }
                        },
                        new Response.ErrorListener(){
                            @Override
                            public void onErrorResponse(VolleyError error){
                                // Do something when error occurred

                            }
                        }
                );

                // Add JsonArrayRequest to the RequestQueue
                requestQueue.add(jsonArrayRequest);
    }
}
