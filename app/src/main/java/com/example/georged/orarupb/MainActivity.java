package com.example.georged.orarupb;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.georged.orarupb.base.BaseActivity;
import com.example.georged.orarupb.fragment.AccountFragment;
import com.example.georged.orarupb.fragment.ScheduleImprovedFragment;
import com.example.georged.orarupb.fragment.ScheduleMainFragment;
import com.example.georged.orarupb.webApiClient.models.Student;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity {

    @BindView(R.id.nav_view) NavigationView _navigationView;
    @BindView(R.id.drawer_layout) DrawerLayout _drawerLayout;
    @BindView(R.id.menuToolbar) Toolbar _toolbar;
    @BindView(R.id.content_frame) FrameLayout contentFrame;
    @BindView(R.id.loading_bar) ProgressBar _loadingBar;

    private static final String TAG_SCHEDULE = "Schedule";
    private static final String TAG_ACCOUNT = "Account";
    private static final String TAG_NEW_SCHEDULE = "NewSchedule";
    private static String CURRENT_TAG = TAG_SCHEDULE;

    private ActionBarDrawerToggle drawerToggle;

    private Student student;

    private Map<String, Fragment> fragsMap;

    public MainActivity() {
        super();
        fragsMap = new HashMap<>();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(context);

        if (!localStorage.hasKey("user")) {
            startActivity(new Intent(this, LoginActivity.class));
        }

        setSupportActionBar(_toolbar);

        setupNavigationView();
        drawerToggle = setupDrawerToggle();

        _drawerLayout.addDrawerListener(drawerToggle);
    }

    @Override
    protected void onResume() {
        super.onResume();

        student = getUser();
        if (student != null) {
            setupHeader();

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.content_frame, ScheduleMainFragment.newInstance(student), TAG_SCHEDULE)
                    .commit();

            getSupportFragmentManager().executePendingTransactions();
        }
    }

    private void setupHeader() {
        View header = (View) _navigationView.getHeaderView(0);
        TextView tvName = header.findViewById(R.id.tv_header_name);
        tvName.setText(student.nume + " " + student.prenume);

        TextView tvGroup = header.findViewById(R.id.tv_header_group);
        tvGroup.setText(student.groupNumber);
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        drawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void setupNavigationView() {
        _navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                selectDrawerItem(item);
                return true;
            }
        });
    }

    private ActionBarDrawerToggle setupDrawerToggle() {
        ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(this, _drawerLayout, _toolbar, R.string.openDrawer, R.string.closeDrawer);
        drawerToggle.setDrawerSlideAnimationEnabled(true);

        return drawerToggle;
    }

    private void selectDrawerItem(MenuItem item) {
        String tag;
        Fragment fragment;
        switch (item.getItemId()) {
            case R.id.nav_schedule:
                if (CURRENT_TAG.equals(TAG_SCHEDULE)) {
                    return;
                }
                fragment = ScheduleMainFragment.newInstance(student);
                tag = TAG_SCHEDULE;
                break;
            case R.id.nav_account:
                if (CURRENT_TAG.equals(TAG_ACCOUNT)) {
                    return;
                }
                fragment = new AccountFragment();
                tag = TAG_ACCOUNT;
                setTitle(TAG_ACCOUNT);
                break;
            case R.id.nav_logout:
                logout();
                return;
            case R.id.nav_improv_schedule:
                if (CURRENT_TAG.equals(TAG_NEW_SCHEDULE)) {
                    return;
                }
                fragment = new ScheduleImprovedFragment();
                tag = TAG_NEW_SCHEDULE;
                setTitle("Yada yada yada");
                break;
            default:
                if (CURRENT_TAG.equals(TAG_SCHEDULE)) {
                    return;
                }
                fragment = ScheduleMainFragment.newInstance(student);
                tag = TAG_SCHEDULE;
        }

        item.setChecked(true);
        _drawerLayout.closeDrawers();

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out);
        transaction.replace(R.id.content_frame, fragment, tag).commit();

        getSupportFragmentManager().executePendingTransactions();

        CURRENT_TAG = tag;
    }

    private void logout() {
        _drawerLayout.closeDrawers();
        localStorage.remove("user");
        getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        startActivity(new Intent(context, LoginActivity.class));
    }

    @Override
    public void onRequest() {
        _loadingBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void onResponse() {
        _loadingBar.setVisibility(View.GONE);
    }

    @Override
    public void onFailure() {
        _loadingBar.setVisibility(View.GONE);
        Toast.makeText(this, "Request failed!", Toast.LENGTH_SHORT).show();
    }
}
