package com.example.group2_caloriecounter;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by ankit on 27/10/17.
 */

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder> {

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
    public void onBindViewHolder(ViewHolder holder, int position) {
        Movie movie = list.get(position);


        holder.textFood.setText(movie.getFood());
        holder.textCount.setText(movie.getCount());
        holder.textDate.setText(movie.getDate());
        holder.textId.setText(String.valueOf(movie.getId()));

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView  textFood, textCount, textDate, textId;

        public ViewHolder(View itemView) {
            super(itemView);

            textFood = itemView.findViewById(R.id.main_food);
            textCount = itemView.findViewById(R.id.main_count);
            textDate = itemView.findViewById(R.id.main_date);
            textId = itemView.findViewById(R.id.main_id);
        }
    }

}
