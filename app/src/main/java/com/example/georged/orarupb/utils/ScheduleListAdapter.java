package com.example.georged.orarupb.utils;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.georged.orarupb.R;
import com.example.georged.orarupb.webApiClient.models.Schedule;

import java.util.ArrayList;

/**
 * Created by georgemd on 14.02.2018.
 */

public class ScheduleListAdapter extends RecyclerView.Adapter<ScheduleViewHolder> {

    ArrayList<Schedule> schedules;

    @Override
    public ScheduleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.schedule_list_item, parent, false);

        return new ScheduleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ScheduleViewHolder holder, int position) {
        Schedule schedule = schedules.get(position);
        String startHour = schedule.startHour.substring(0, schedule.startHour.length() - 3);
        String endHour = schedule.endHour.substring(0, schedule.endHour.length() - 3);

        holder.tvLocation.setText(schedule.location);
        holder.tvShortCourse.setText(schedule.courseShort);
        holder.tvLongCourse.setText(schedule.course);
        holder.tvTimespan.setText(startHour + " - " + endHour);
        if (schedule.details != null) {
            holder.tvDetails.setText("(" + schedule.details + ")");
        }
    }

    @Override
    public int getItemCount() {
        return schedules == null ? 0 : schedules.size();
    }

    public void setSchedules(ArrayList<Schedule> schedules) {
        this.schedules = schedules;
        notifyDataSetChanged();
    }
}
