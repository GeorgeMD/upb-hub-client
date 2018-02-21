package com.example.georged.orarupb;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.georged.orarupb.base.BaseActivity;
import com.example.georged.orarupb.utils.interfaces.IAction;
import com.example.georged.orarupb.viewModels.RegisterVM;
import com.example.georged.orarupb.webApiClient.implementation.GroupService;
import com.example.georged.orarupb.webApiClient.implementation.ServiceFactory;
import com.example.georged.orarupb.webApiClient.implementation.StudentService;
import com.example.georged.orarupb.webApiClient.models.Grupa;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;

import java.util.ArrayList;
import java.util.List;

public class RegisterActivity extends BaseActivity implements Validator.ValidationListener {

    private RegisterVM model;
    private StudentService studentService;

    public RegisterActivity() { super(); }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        GroupService grupaService = ServiceFactory.getInstance().getGroupService();
        studentService = ServiceFactory.getInstance().getStudentService();

        grupaService.get(new IAction<ArrayList<Grupa>>() {
            @Override
            public void Call(ArrayList<Grupa> param) {
                model = new RegisterVM(context, param);

                model.setValidator(RegisterActivity.this);
                model.setBtnSave();
            }
        });
    }

    @Override
    public void onValidationSucceeded() {
        studentService.create(model.toEntity(), new IAction<Integer>() {
            @Override
            public void Call(Integer param) {
                startActivity(new Intent(context, LoginActivity.class));
                finish();
            }
        });
    }

    @Override
    public void onValidationFailed(List<ValidationError> errors) {
        for (ValidationError error : errors) {
            View view = error.getView();
            String msg = error.getCollatedErrorMessage(this);

            if (view instanceof EditText) {
                ((EditText) view).setError(msg);
            }
            else {
                Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
            }
        }
    }
}
