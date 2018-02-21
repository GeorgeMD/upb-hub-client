package com.example.georged.orarupb.fragment;

import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.georged.orarupb.R;
import com.example.georged.orarupb.utils.Day;
import com.example.georged.orarupb.utils.ScheduleListAdapter;
import com.example.georged.orarupb.utils.interfaces.IAction;
import com.example.georged.orarupb.webApiClient.implementation.ScheduleService;
import com.example.georged.orarupb.webApiClient.implementation.ServiceFactory;
import com.example.georged.orarupb.webApiClient.models.Schedule;
import com.example.georged.orarupb.webApiClient.models.Student;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ScheduleFragment extends Fragment
        implements LoaderManager.LoaderCallbacks<Cursor> {

    private Student student;
    private Day day;
    private ScheduleService scheduleService;
    @BindView(R.id.list_courses) RecyclerView _rvCourses;
    @BindView(R.id.empty) TextView _tvEmpty;

    ScheduleListAdapter scheduleListAdapter;

    static final String DAY_KEY = "day";
    static final String STUDENT_KEY = "student";

    public ScheduleFragment() {
    }

    public static ScheduleFragment newInstance(Day day, @NonNull Student student) {
        ScheduleFragment fragment = new ScheduleFragment();

        Bundle bundle = new Bundle();
        bundle.putSerializable(DAY_KEY, day);
        bundle.putSerializable(STUDENT_KEY, student);

        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        scheduleService = ServiceFactory.getInstance().getScheduleService();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_schedule, container, false);
        ButterKnife.bind(this, view);
        Spinner spinSemigroup = getActivity().findViewById(R.id.spin_semigroup);

        if (getArguments() != null) {
            day = (Day) getArguments().getSerializable(DAY_KEY);
            student = (Student) getArguments().getSerializable(STUDENT_KEY);
        } else {
            _rvCourses.setVisibility(View.GONE);
            _tvEmpty.setVisibility(View.VISIBLE);
            _tvEmpty.setText("Something went very wrong! Try restarting your app.");
            return view;
        }

        setupScheduleTable();
        updateScheduleTable(spinSemigroup.getSelectedItemPosition() + 1);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // TODO: GET CACHED ARRAY LIST OF SCHEDULES, ASK SERVER IF ANYTHING IS NEW!
        if (savedInstanceState != null) {
            day = (Day) savedInstanceState.getSerializable(DAY_KEY);
            student = (Student) savedInstanceState.getSerializable(STUDENT_KEY);
        }
    }

    private void setupScheduleTable() {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        _rvCourses.setLayoutManager(layoutManager);

        scheduleListAdapter = new ScheduleListAdapter();
        _rvCourses.setAdapter(scheduleListAdapter);
    }

    private void updateScheduleTable(ArrayList<Schedule> schedules) {
        if (schedules.isEmpty()) {
            _rvCourses.setVisibility(View.GONE);
            _tvEmpty.setText(R.string.free_day);
            _tvEmpty.setVisibility(View.VISIBLE);
            return;
        }

        if (_rvCourses.getVisibility() == View.GONE) {
            _rvCourses.setVisibility(View.VISIBLE);
            _tvEmpty.setVisibility(View.GONE);
        }

        scheduleListAdapter.setSchedules(schedules);
    }

    public void updateScheduleTable(int semigroup) {
        scheduleService.get(student.groupId, day.getName(), semigroup, new IAction<ArrayList<Schedule>>() {
            @Override
            public void Call(ArrayList<Schedule> param) {
                updateScheduleTable(param);
            }
        });
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return null;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

    // TODO: CACHE ARRAY LIST OF SCHEDULES
    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putSerializable(DAY_KEY, day);
        outState.putSerializable(STUDENT_KEY, student);
    }
}
