package com.example.georged.orarupb.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.MenuPopupWindow;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.georged.orarupb.R;
import com.example.georged.orarupb.utils.ScheduleListAdapter;
import com.example.georged.orarupb.webApiClient.models.Schedule;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by georgemd on 01.03.2018.
 */

public class ScheduleImprovedFragment extends Fragment {
    @BindView(R.id.layout_schedule_main) LinearLayout _layoutMain;
    @BindView(R.id.layout_schedule) LinearLayout _layoutSchedule;
    @BindView(R.id.spin_year) Spinner _spinYear;
    @BindView(R.id.spin_series) Spinner _spinSeries;
    @BindView(R.id.spin_group) Spinner _spinGroup;
    @BindView(R.id.list_courses) RecyclerView _rvCourses;

    String[] years = new String[]{"", "Year 1", "Year 2", "Year 3", "Year 4"},
        series = new String[]{"", "CA", "CB", "CC", "CD"},
        groups = new String[]{"", "321 CD", "322 CD", "323 CD", "324 CD", "325 CD"};
    String year = "", serie = "", group = "";

    ScheduleListAdapter scheduleListAdapter;

    public ScheduleImprovedFragment() { }
    ArrayList<Schedule> schedules;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        scheduleListAdapter = new ScheduleListAdapter();

        // mocking
        schedules = new ArrayList<>();
        schedules.add(new Schedule());
        schedules.add(new Schedule());
        schedules.add(new Schedule());
        schedules.add(new Schedule());
        schedules.get(0).details = "242";
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_schedule_improved, container, false);
        ButterKnife.bind(this, view);
        setupSpinner(_spinYear, years);
        setupSpinner(_spinSeries, series);
        setupSpinner(_spinGroup, groups);
        _rvCourses.setAdapter(scheduleListAdapter);
        scheduleListAdapter.setSchedules(schedules);
        return view;
    }

    void setupSpinner(final Spinner spinner, String[] list) {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(),
                android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapter.addAll(list);

        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (parent.getId()) {
                    case R.id.spin_year:
                        year = ((TextView) view).getText().toString();
                        break;
                    case R.id.spin_series:
                        serie = ((TextView) view).getText().toString();
                        break;
                    case R.id.spin_group:
                        group = ((TextView) view).getText().toString();
                        break;
                    default:
                        Toast.makeText(getActivity(), "Something went wrong!", Toast.LENGTH_SHORT).show();
                }
                if (year.isEmpty() || serie.isEmpty() || group.isEmpty()) {
                    return;
                }

                showSchedule(parent);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    void showSchedule(View view) {
        schedules.add(new Schedule());
        schedules.get(4).details = "aici acum";
        scheduleListAdapter.setSchedules(schedules);
        _layoutMain.setGravity(Gravity.TOP);
        _layoutSchedule.setVisibility(View.VISIBLE);
    }
}
