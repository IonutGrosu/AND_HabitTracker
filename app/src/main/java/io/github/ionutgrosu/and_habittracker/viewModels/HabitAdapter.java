package io.github.ionutgrosu.and_habittracker.viewModels;

import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import io.github.ionutgrosu.and_habittracker.R;
import io.github.ionutgrosu.and_habittracker.models.Habit;
import io.github.ionutgrosu.and_habittracker.utils.Utils;

public class HabitAdapter extends RecyclerView.Adapter<HabitAdapter.ViewHolder> {

    private ArrayList<Habit> habits;

    public HabitAdapter(ArrayList<Habit> habits){
        this.habits = habits;
    }

    private OnCheckBoxClickListener checkBoxClickListener;

    public void setOnCheckboxCheckedListener(OnCheckBoxClickListener checkBoxClickListener) {
        this.checkBoxClickListener = checkBoxClickListener;
    }

    public void setHabits(ArrayList<Habit> habits) {
        this.habits = habits;
    }

    private int getProgressPercentage(int current, int total) {
        return (current * 100) / total;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.habit_container, parent, false);
        return new ViewHolder(view);

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.habitName.setText(habits.get(position).getName());
        holder.habitProgress.setText("" + habits.get(position).getProgress() + "/" + habits.get(position).getDuration());
        holder.progressBar.setProgress(getProgressPercentage(habits.get(position).getProgress(), habits.get(position).getDuration()));
        if (Utils.isToday(habits.get(position).getLastCheckDate())){
            holder.checkBox.setChecked(true);
            holder.checkBox.setClickable(false);
        }
    }

    @Override
    public int getItemCount() {
        return habits.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView habitName;
        TextView habitProgress;
        CheckBox checkBox;
        ProgressBar progressBar;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            habitName = itemView.findViewById(R.id.habitContainerName);
            habitProgress = itemView.findViewById(R.id.habitContainerProgress);
            checkBox = itemView.findViewById(R.id.habitContainerCheckBox);
            progressBar = itemView.findViewById(R.id.habitContainerProgressBar);
            checkBox.setOnCheckedChangeListener((compoundButton, b) -> checkBoxClickListener.checkBoxClicked(b, habits.get(getAdapterPosition())));
        }
    }

    public interface OnCheckBoxClickListener {
        void checkBoxClicked(boolean checked, Habit habitToUpdate);
    }
}
