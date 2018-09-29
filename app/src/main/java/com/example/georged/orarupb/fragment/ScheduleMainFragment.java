package com.example.georged.orarupb.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.georged.orarupb.R;
import com.example.georged.orarupb.utils.Day;
import com.example.georged.orarupb.webApiClient.models.Student;

import org.joda.time.LocalDate;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ScheduleMainFragment extends Fragment {

    @BindView(R.id.content_pager) ViewPager _contentPager;

    private ScheduleFragAdapter scheduleFragAdapter;

    private Student student;
    private Day shownDay;

    public ScheduleMainFragment() {
    }

    public static ScheduleMainFragment newInstance(Student student) {
        ScheduleMainFragment fragment = new ScheduleMainFragment();

        Bundle args = new Bundle();
        args.putSerializable(ScheduleFragment.STUDENT_KEY, student);

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);

        if (getArguments() != null) {
            student = (Student) getArguments().getSerializable(ScheduleFragment.STUDENT_KEY);
        }

        scheduleFragAdapter = new ScheduleFragAdapter(getFragmentManager());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_schedule_main, container, false);
        setHasOptionsMenu(true);
        ButterKnife.bind(this, view);

//        getActivity().findViewById(R.id.spin_semigroup).setVisibility(View.VISIBLE);
        getActivity().findViewById(R.id.btn_semigroup).setVisibility(View.VISIBLE);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (savedInstanceState != null) {
            student = (Student) savedInstanceState.getSerializable(ScheduleFragment.STUDENT_KEY);
            shownDay = (Day) savedInstanceState.getSerializable(ScheduleFragment.DAY_KEY);
        } else {
            shownDay = Day.valueOf(LocalDate.now().getDayOfWeek() - 1);
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        if (student != null) {
            _contentPager.setAdapter(scheduleFragAdapter);

            _contentPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                }

                @Override
                public void onPageSelected(int position) {
                    shownDay = Day.valueOf(position);
                    getActivity().setTitle(shownDay.getName());
                }

                @Override
                public void onPageScrollStateChanged(int state) {
                }
            });

            _contentPager.setCurrentItem(shownDay.getPos(), false);
            getActivity().setTitle(shownDay.getName());
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putSerializable(ScheduleFragment.STUDENT_KEY, student);
        outState.putSerializable(ScheduleFragment.DAY_KEY, shownDay);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
//        Spinner spinSemigroup = getActivity().findViewById(R.id.spin_semigroup);
//
//        ArrayAdapter<String> adapterSemigroup = new ArrayAdapter<>(getActivity(),
//                android.R.layout.simple_spinner_item,
//                new String[]{"Semigroup 1", "Semigroup 2"});
//        adapterSemigroup.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//
//        spinSemigroup.setAdapter(adapterSemigroup);
//
//        spinSemigroup.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
//                int semigroup = pos + 1;
//                int currPos = _contentPager.getCurrentItem();
//
//                // update selected day
//                ((ScheduleFragment) scheduleFragAdapter.instantiateItem(_contentPager, currPos))
//                        .updateScheduleTable(semigroup);
//                // update left neighbour if it exists
//                if (currPos > 0) {
//                    ((ScheduleFragment) scheduleFragAdapter.instantiateItem(_contentPager, currPos - 1))
//                            .updateScheduleTable(semigroup);
//                }
//                // update right neighbour if it exists
//                if (currPos < 6) {
//                    ((ScheduleFragment) scheduleFragAdapter.instantiateItem(_contentPager, currPos + 1))
//                            .updateScheduleTable(semigroup);
//                }
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });

        Button btn = getActivity().findViewById(R.id.btn_semigroup);
        btn.setText("Semigroup 1");
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int semigroup;
                if (((Button) v).getText().equals("Semigroup 1")) {
                    semigroup = 2;
                } else {
                    semigroup = 1;
                }

                int currPos = _contentPager.getCurrentItem();

                // update selected day
                ((ScheduleFragment) scheduleFragAdapter.instantiateItem(_contentPager, currPos))
                        .updateScheduleTable(semigroup);
                // update left neighbour if it exists
                if (currPos > 0) {
                    ((ScheduleFragment) scheduleFragAdapter.instantiateItem(_contentPager, currPos - 1))
                            .updateScheduleTable(semigroup);
                }
                // update right neighbour if it exists
                if (currPos < 6) {
                    ((ScheduleFragment) scheduleFragAdapter.instantiateItem(_contentPager, currPos + 1))
                            .updateScheduleTable(semigroup);
                }
                ((Button) v).setText("Semigroup " + semigroup);
            }
        });
        super.onCreateOptionsMenu(menu, inflater);
    }

    private class ScheduleFragAdapter extends FragmentStatePagerAdapter {

        public ScheduleFragAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return ScheduleFragment.newInstance(Day.valueOf(position), student);
        }

        @Override
        public int getCount() {
            return 7;
        }
    }
}
