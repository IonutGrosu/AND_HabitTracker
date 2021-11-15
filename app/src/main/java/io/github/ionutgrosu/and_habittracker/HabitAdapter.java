package io.github.ionutgrosu.and_habittracker;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class HabitAdapter extends RecyclerView.Adapter<HabitAdapter.ViewHolder> {

    private ArrayList<Habit> habits;

    public HabitAdapter(ArrayList<Habit> habits){
        this.habits = habits;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.habit_container, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.habitName.setText(habits.get(position).getName());
        holder.habitDuration.setText(String.valueOf(habits.get(position).getDuration()));
    }

    @Override
    public int getItemCount() {
        return habits.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView habitName;
        TextView habitDuration;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            habitName = itemView.findViewById(R.id.habitContainerName);
            habitDuration = itemView.findViewById(R.id.habitContainerDuration);
        }
    }
}
