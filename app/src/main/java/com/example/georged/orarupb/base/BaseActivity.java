package com.example.georged.orarupb.base;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.example.georged.orarupb.utils.Constants;
import com.example.georged.orarupb.utils.LocalStorage;
import com.example.georged.orarupb.utils.interfaces.IAction;
import com.example.georged.orarupb.utils.interfaces.IServiceEvents;
import com.example.georged.orarupb.webApiClient.implementation.ServiceFactory;
import com.example.georged.orarupb.webApiClient.models.Student;

/**
 * Created by George D on 23-Jun-17.
 */

public abstract class BaseActivity extends AppCompatActivity implements IServiceEvents {

    protected Activity context;
    protected LocalStorage localStorage;
    protected String title;
    protected ServiceFactory serviceFactory;
    protected boolean updateRequired;

    private static final String TAG_DIALOG = "dialog";

    public BaseActivity() {
        context = this;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        this.localStorage = new LocalStorage(this);
        this.serviceFactory = ServiceFactory.getInstance();
        serviceFactory.setBaseUrl(Constants.API_URL);
        serviceFactory.setEvents(this);

        checkVersion();
    }

    protected Student getUser() { return localStorage.get("user", Student.class); }

    @Override
    public void onRequest() {

    }

    @Override
    public void onFailure() {

    }

    @Override
    public void onResponse() {

    }

    private void checkVersion() {
        try {
            PackageInfo pInfo = context.getPackageManager().getPackageInfo(getPackageName(), 0);
            String version = pInfo.versionName;

            serviceFactory.getUtilsService().checkVersion(version, new IAction<Boolean>() {
                @Override
                public void Call(Boolean param) {
                    manageUpdate(param);
                }
            });
        } catch (PackageManager.NameNotFoundException e) {
            Toast.makeText(context, "Couldn't verify app version!", Toast.LENGTH_LONG).show();
        }
    }

    private void manageUpdate(boolean isUpdated) {
        if (!isUpdated) {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle("Update available");
            builder.setMessage("There is an update available. Go to download page?");
            builder.setPositiveButton("Update", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Uri webpage = Uri.parse("http://dellserver.go.ro/upbhub/UpbHub.apk");
                    Intent intent = new Intent(Intent.ACTION_VIEW, webpage);
                    if (intent.resolveActivity(getPackageManager()) != null) {
                        startActivity(intent);
                    }
                }
            });
            builder.setNegativeButton("Not now", null);
            builder.show();
        }
    }
}
