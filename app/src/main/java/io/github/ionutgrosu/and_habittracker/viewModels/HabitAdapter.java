package io.github.ionutgrosu.and_habittracker.viewModels;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import io.github.ionutgrosu.and_habittracker.R;
import io.github.ionutgrosu.and_habittracker.models.Habit;

public class HabitAdapter extends RecyclerView.Adapter<HabitAdapter.ViewHolder> {

    private ArrayList<Habit> habits;

    public HabitAdapter(ArrayList<Habit> habits){
        this.habits = habits;
    }

    private OnCheckBoxClickListener checkBoxClickListener;

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
        holder.progressBar.setProgress(getProgressPercentage(habits.get(position).getProgress(), habits.get(position).getDuration()));
    }

    private int getProgressPercentage(int current, int total) {
        return (current * 100) / total;
    }

    @Override
    public int getItemCount() {
        return habits.size();
    }

    public void setOnCheckboxCheckedListener(OnCheckBoxClickListener checkBoxClickListener) {
        this.checkBoxClickListener = checkBoxClickListener;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView habitName;
        CheckBox checkBox;
        ProgressBar progressBar;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            habitName = itemView.findViewById(R.id.habitContainerName);
            checkBox = itemView.findViewById(R.id.habitContainerCheckBox);
            progressBar = itemView.findViewById(R.id.habitContainerProgressBar);
            checkBox.setOnCheckedChangeListener((compoundButton, b) -> checkBoxClickListener.checkBoxClicked(b, habits.get(getAdapterPosition())));
        }
    }

    public interface OnCheckBoxClickListener {
        void checkBoxClicked(boolean checked, Habit habitToUpdate);
    }
}
