package com.example.group2_caloriecounter;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.jjoe64.graphview.DefaultLabelFormatter;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.helper.DateAsXAxisLabelFormatter;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

public class GraphActivity extends AppCompatActivity implements View.OnClickListener {

    private String url = "https://group2-247923.appspot.com/employees";

    private String[][] array;

    GraphView graph;
    LineGraphSeries<DataPoint> series;

    Button buttonDaily, buttonWeekly, buttonMonthly;

    String androidId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);

        androidId = Settings.Secure.getString(getContentResolver(),
                Settings.Secure.ANDROID_ID);

        graph = findViewById(R.id.graph);

        series = new LineGraphSeries<DataPoint>(new DataPoint[] { });


        buttonDaily = findViewById(R.id.buttonDaily);
        buttonWeekly = findViewById(R.id.buttonWeekly);
        buttonMonthly = findViewById(R.id.buttonMonthly);


        buttonDaily.setOnClickListener((View.OnClickListener) this);
        buttonWeekly.setOnClickListener((View.OnClickListener) this);
        buttonMonthly.setOnClickListener((View.OnClickListener) this);

        getData();
    }

    private void getData() {
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                array = new String[response.length()][2];

                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject jsonObject = response.getJSONObject(i);

                        if(androidId.equals(jsonObject.getString("android"))) {
                            array[i][0] = jsonObject.getString("date");
                            array[i][1] = jsonObject.getString("count");
                        }
                        else
                        {

                            array[i][0] = jsonObject.getString("date");
                            array[i][1] = "0";

                        }


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                Log.wtf("array", String.valueOf(array.length));

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Volley", error.toString());
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonArrayRequest);
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.buttonDaily:

                if (array.length > 0) {

                    int[] calorieCount;
                    int intCount = 1;

                    Date[] dateArray;

                    Calendar cal0 = Calendar.getInstance();



                    Calendar cal1 = Calendar.getInstance();

                    for (int a = 1; a < array.length; a++) {

                        try {
                            cal0.setTime(new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy").parse(array[a - 1][0]));
                            cal1.setTime(new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy").parse(array[a][0]));
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

                         if (cal0.get(Calendar.DAY_OF_YEAR) < cal1.get(Calendar.DAY_OF_YEAR))
                         {
                             intCount++;

                         }
                         else if (cal0.get(Calendar.YEAR) < cal1.get(Calendar.YEAR))
                         {
                             intCount++;
                         }
                    }

                    calorieCount = new int[intCount];
                    dateArray = new Date[intCount];

                    for (int i = 0; i < intCount; i++) {

                        calorieCount[i] = 0;
                        dateArray[i] = null;

                    }

                    intCount = 0;

                    try {
                        cal0.setTime(new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy").parse(array[0][0]));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    dateArray[0] = cal0.getTime();
                    calorieCount[0] += Integer.parseInt(array[0][1]);

                    for (int a = 1; a < array.length; a++) {

                        try {
                            cal0.setTime(new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy").parse(array[a - 1][0]));
                            cal1.setTime(new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy").parse(array[a][0]));
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

                        if (cal0.get(Calendar.DAY_OF_YEAR) == cal1.get(Calendar.DAY_OF_YEAR))
                        {
                            calorieCount[intCount] += Integer.parseInt(array[a][1]);
                        }
                        else if (cal0.get(Calendar.DAY_OF_YEAR) < cal1.get(Calendar.DAY_OF_YEAR))
                        {
                            intCount++;
                            dateArray[intCount] = cal1.getTime();
                            calorieCount[intCount] += Integer.parseInt(array[a][1]);


                        }
                        else if (cal0.get(Calendar.YEAR) < cal1.get(Calendar.YEAR))
                        {
                            intCount++;
                            dateArray[intCount] = cal1.getTime();
                            calorieCount[intCount] += Integer.parseInt(array[a][1]);


                        }
                    }
                    DataPoint[] dataPoints = new DataPoint[dateArray.length];
                    Calendar[] cal = new Calendar[dateArray.length];

                    for (int i =0; i < dateArray.length; i++) {
                        cal[i] = Calendar.getInstance();
                    }

                    for (int i = 0; i < dateArray.length; i++) {
                        cal[i].setTime(dateArray[i]);

                        if (calorieCount[i] > 0) {

                            dataPoints[i] = new DataPoint(cal[i].get(Calendar.DAY_OF_YEAR), calorieCount[i]);
                        }

                    }

                    int[] range = calorieCount;

                    Arrays.sort(range);


                    // set date label formatter
                    graph.getGridLabelRenderer().setNumHorizontalLabels(range.length);

                    // set manual x bounds to have nice steps
                    graph.getViewport().setMinX(cal[0].get(Calendar.DAY_OF_YEAR));
                    graph.getViewport().setMaxX(cal[dateArray.length - 1].get(Calendar.DAY_OF_YEAR));
                    graph.getViewport().setMinY(range[0]);
                    graph.getViewport().setMaxY(range[range.length - 1]);
                    graph.getViewport().setXAxisBoundsManual(true);
                    graph.getViewport().setYAxisBoundsManual(true);

                    // as we use dates as labels, the human rounding to nice readable numbers
                    // is not necessary
                    graph.getGridLabelRenderer().setHumanRounding(true);

                    series.resetData(dataPoints);
                    graph.addSeries(series);
                }

                break;

            case R.id.buttonWeekly:

                if (array.length > 0) {

                    int[] calorieCount;
                    int intCount = 1;

                    Date[] dateArray;

                    Calendar cal0 = Calendar.getInstance();



                    Calendar cal1 = Calendar.getInstance();

                    for (int a = 1; a < array.length; a++) {

                        try {
                            cal0.setTime(new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy").parse(array[a - 1][0]));
                            cal1.setTime(new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy").parse(array[a][0]));
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

                        if (cal0.get(Calendar.WEEK_OF_YEAR) < cal1.get(Calendar.WEEK_OF_YEAR))
                        {
                            intCount++;

                        }
                        else if (cal0.get(Calendar.YEAR) < cal1.get(Calendar.YEAR))
                        {
                            intCount++;
                        }
                    }

                    calorieCount = new int[intCount];
                    dateArray = new Date[intCount];

                    for (int i = 0; i < intCount; i++) {

                        calorieCount[i] = 0;
                        dateArray[i] = null;

                    }

                    intCount = 0;

                    try {
                        cal0.setTime(new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy").parse(array[0][0]));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    dateArray[0] = cal0.getTime();
                    calorieCount[0] += Integer.parseInt(array[0][1]);

                    for (int a = 1; a < array.length; a++) {

                        try {
                            cal0.setTime(new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy").parse(array[a - 1][0]));
                            cal1.setTime(new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy").parse(array[a][0]));
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

                        if (cal0.get(Calendar.WEEK_OF_YEAR) == cal1.get(Calendar.WEEK_OF_YEAR))
                        {
                            calorieCount[intCount] += Integer.parseInt(array[a][1]);
                        }
                        else if (cal0.get(Calendar.WEEK_OF_YEAR) < cal1.get(Calendar.WEEK_OF_YEAR))
                        {
                            intCount++;
                            dateArray[intCount] = cal1.getTime();
                            calorieCount[intCount] += Integer.parseInt(array[a][1]);


                        }
                        else if (cal0.get(Calendar.YEAR) < cal1.get(Calendar.YEAR))
                        {
                            intCount++;
                            dateArray[intCount] = cal1.getTime();
                            calorieCount[intCount] += Integer.parseInt(array[a][1]);


                        }
                    }
                    DataPoint[] dataPoints = new DataPoint[dateArray.length];
                    Calendar[] cal = new Calendar[dateArray.length];

                    for (int i =0; i < dateArray.length; i++) {
                        cal[i] = Calendar.getInstance();
                    }

                    for (int i = 0; i < dateArray.length; i++) {
                        cal[i].setTime(dateArray[i]);

                        if(calorieCount[i] > 0) {
                            dataPoints[i] = new DataPoint(cal[i].get(Calendar.WEEK_OF_YEAR), calorieCount[i]);
                        }

                    }

                    int[] range = calorieCount;

                    Arrays.sort(range);


                    // set date label formatter
                    graph.getGridLabelRenderer().setNumHorizontalLabels(range.length);

                    // set manual x bounds to have nice steps
                    graph.getViewport().setMinX(cal[0].get(Calendar.WEEK_OF_YEAR));
                    graph.getViewport().setMaxX(cal[dateArray.length - 1].get(Calendar.WEEK_OF_YEAR));
                    graph.getViewport().setMinY(range[0]);
                    graph.getViewport().setMaxY(range[range.length - 1]);
                    graph.getViewport().setXAxisBoundsManual(true);
                    graph.getViewport().setYAxisBoundsManual(true);

                    // as we use dates as labels, the human rounding to nice readable numbers
                    // is not necessary
                    graph.getGridLabelRenderer().setHumanRounding(true);

                    series.resetData(dataPoints);
                    graph.addSeries(series);
                }

                break;

            case R.id.buttonMonthly:

                if (array.length > 0) {

                    int[] calorieCount;
                    int intCount = 1;

                    Date[] dateArray;

                    Calendar cal0 = Calendar.getInstance();



                    Calendar cal1 = Calendar.getInstance();

                    for (int a = 1; a < array.length; a++) {

                        try {
                            cal0.setTime(new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy").parse(array[a - 1][0]));
                            cal1.setTime(new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy").parse(array[a][0]));
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

                        if (cal0.get(Calendar.MONTH) < cal1.get(Calendar.MONTH))
                        {
                            intCount++;

                        }
                        else if (cal0.get(Calendar.YEAR) < cal1.get(Calendar.YEAR))
                        {
                            intCount++;
                        }
                    }

                    calorieCount = new int[intCount];
                    dateArray = new Date[intCount];

                    for (int i = 0; i < intCount; i++) {

                        calorieCount[i] = 0;
                        dateArray[i] = null;

                    }

                    intCount = 0;

                    try {
                        cal0.setTime(new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy").parse(array[0][0]));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    dateArray[0] = cal0.getTime();
                    calorieCount[0] += Integer.parseInt(array[0][1]);

                    for (int a = 1; a < array.length; a++) {

                        try {
                            cal0.setTime(new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy").parse(array[a - 1][0]));
                            cal1.setTime(new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy").parse(array[a][0]));
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

                        if (cal0.get(Calendar.MONTH) == cal1.get(Calendar.MONTH))
                        {
                            calorieCount[intCount] += Integer.parseInt(array[a][1]);
                        }
                        else if (cal0.get(Calendar.MONTH) < cal1.get(Calendar.MONTH))
                        {
                            intCount++;
                            dateArray[intCount] = cal1.getTime();
                            calorieCount[intCount] += Integer.parseInt(array[a][1]);


                        }
                        else if (cal0.get(Calendar.YEAR) < cal1.get(Calendar.YEAR))
                        {
                            intCount++;
                            dateArray[intCount] = cal1.getTime();
                            calorieCount[intCount] += Integer.parseInt(array[a][1]);


                        }
                    }
                    DataPoint[] dataPoints = new DataPoint[dateArray.length];
                    Calendar[] cal = new Calendar[dateArray.length];

                    for (int i =0; i < dateArray.length; i++) {
                        cal[i] = Calendar.getInstance();
                    }

                    for (int i = 0; i < dateArray.length; i++) {
                        cal[i].setTime(dateArray[i]);

                        if (calorieCount[i] > 0) {
                            dataPoints[i] = new DataPoint(cal[i].get(Calendar.MONTH), calorieCount[i]);
                        }

                    }

                    int[] range = calorieCount;

                    Arrays.sort(range);


                    // set date label formatter
                    graph.getGridLabelRenderer().setNumHorizontalLabels(range.length);

                    // set manual x bounds to have nice steps
                    graph.getViewport().setMinX(cal[0].get(Calendar.MONTH));
                    graph.getViewport().setMaxX(cal[dateArray.length - 1].get(Calendar.MONTH));
                    graph.getViewport().setMinY(range[0]);
                    graph.getViewport().setMaxY(range[range.length - 1]);
                    graph.getViewport().setXAxisBoundsManual(true);
                    graph.getViewport().setYAxisBoundsManual(true);

                    // as we use dates as labels, the human rounding to nice readable numbers
                    // is not necessary
                    graph.getGridLabelRenderer().setHumanRounding(true);

                    series.resetData(dataPoints);
                    graph.addSeries(series);
                }

                break;
        }
    }
}
