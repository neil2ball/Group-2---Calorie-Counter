package com.example.group2_caloriecounter;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

/**
 * Created by ankit on 27/10/17.
 */

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder> {

    private static final String TAG = "MovieAdapter";

    private Context context;
    private List<Movie> list;

    public MovieAdapter(Context context, List<Movie> list) {
        this.context = context;
        this.list = list;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.single_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        Movie movie = list.get(position);


        holder.textFood.setText(movie.getFood());
        holder.textCount.setText(movie.getCount());
        holder.textDate.setText(movie.getDate());
        holder.textId.setText(String.valueOf(movie.getId()));

        holder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: clicked on: " + list.get(position));

                Intent intent = new Intent(context, ModifyActivity.class);
                intent.putExtra("food", holder.textFood.getText().toString());
                intent.putExtra("count", holder.textCount.getText().toString());
                intent.putExtra("date", holder.textDate.getText().toString());
                intent.putExtra("id", holder.textId.getText().toString());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView  textFood, textCount, textDate, textId;

        LinearLayout parentLayout;

        public ViewHolder(View itemView) {
            super(itemView);

            textFood = itemView.findViewById(R.id.main_food);
            textCount = itemView.findViewById(R.id.main_count);
            textDate = itemView.findViewById(R.id.main_date);
            textId = itemView.findViewById(R.id.main_id);

            parentLayout = itemView.findViewById(R.id.parent_layout);
        }
    }

}
