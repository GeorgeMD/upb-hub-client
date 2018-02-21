package com.example.georged.orarupb.utils;

import com.example.georged.orarupb.R;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by georgemd on 14.02.2018.
 */

public class ScheduleViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.tv_shortCourse) TextView tvShortCourse;
    @BindView(R.id.tv_longCourse) TextView tvLongCourse;
    @BindView(R.id.tv_timespan) TextView tvTimespan;
    @BindView(R.id.tv_location) TextView tvLocation;
    @BindView(R.id.tv_details) TextView tvDetails;

    public ScheduleViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
