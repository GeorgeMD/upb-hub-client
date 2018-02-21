package com.example.georged.orarupb;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.georged.orarupb.base.BaseActivity;
import com.example.georged.orarupb.utils.interfaces.IAction;
import com.example.georged.orarupb.webApiClient.implementation.ServiceFactory;
import com.example.georged.orarupb.webApiClient.implementation.StudentService;
import com.example.georged.orarupb.webApiClient.models.Student;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnFocusChange;
import butterknife.OnTextChanged;

public class LoginActivity extends BaseActivity {

    @BindView(R.id.et_login_email)
    EditText _etEmail;
    @BindView(R.id.et_login_pass)
    EditText _etPass;
    @BindView(R.id.btn_login)
    Button _btnLogin;
    @BindView(R.id.tv_register_link)
    TextView _tvRegisterLink;
    @BindView(R.id.tv_invalid_login)
    TextView _tvInvalidLogin;

    private Toast loginToast;

    private StudentService studentService;

    public LoginActivity() { super(); }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(context);

        studentService = ServiceFactory.getInstance().getStudentService();
    }

    @OnClick(R.id.btn_login) void loginClick() {
        String email = _etEmail.getText().toString();
        String password = _etPass.getText().toString();

        if (email.isEmpty()) {
            _etEmail.requestFocus();
            return;
        }
        if (password.isEmpty()) {
            _etPass.requestFocus();
            return;
        }
        studentService.login(email, password, new IAction<Student>() {
            @Override
            public void Call(Student param) {
                if (param != null && param.id != 0) {
                    localStorage.set("user", param);
                    finish();
                } else {
                    _tvInvalidLogin.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    @OnFocusChange({R.id.et_login_email, R.id.et_login_pass})
    void onFocusChangeInputs(boolean focused) {
        if (focused && _tvInvalidLogin.getVisibility() == View.VISIBLE) {
            _tvInvalidLogin.setVisibility(View.GONE);
        }
    }

    @OnTextChanged({R.id.et_login_email, R.id.et_login_pass})
    void onTextChangedInputs() {
        if (_tvInvalidLogin.getVisibility() == View.VISIBLE) {
            _tvInvalidLogin.setVisibility(View.GONE);
        }
    }

    @OnClick(R.id.tv_register_link) void register() {
        makeLoginToast("Not yet implemented!");
    }

    private void makeLoginToast(String msg) {
        if (loginToast != null) {
            loginToast.cancel();
        }

        loginToast = Toast.makeText(context, msg, Toast.LENGTH_SHORT);
        loginToast.show();
    }
}
