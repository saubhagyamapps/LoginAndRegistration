package com.example.android.androidandregistration.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.androidandregistration.R;
import com.example.android.androidandregistration.model.LoginModel;
import com.example.android.androidandregistration.retrofit.ApiClient;
import com.example.android.androidandregistration.retrofit.ApiInterface;
import com.example.android.androidandregistration.sessionManagement.SessionManagement;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivity";
    EditText etEmail, etPassword;
    Button btnLogin;
    String mEmail, mPassword;
    int mResponseStatus;
    String mResponseMsg;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+[a-z]";
    TextView txtSignUp, txtForgotPwd;
    SessionManagement mSessionManagement;
    private ApiInterface mApiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initialization();
    }

    private void initialization() {
        mSessionManagement = new SessionManagement(getApplicationContext());
        mApiInterface = ApiClient.getClient().create(ApiInterface.class);
        etEmail = (EditText) findViewById(R.id.etEmail);
        etPassword = (EditText) findViewById(R.id.etPassword);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        txtSignUp = (TextView) findViewById(R.id.txtSignUp);
        txtForgotPwd = (TextView) findViewById(R.id.txtForgotPwd);
        LoginBtnClick();
        ForgotPassword();
        SignUpPage();
    }

    private void ForgotPassword() {
        txtForgotPwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ForgotPasswordActivity.class);
                startActivity(intent);
            }
        });
    }

    private void SignUpPage() {
        txtSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), RegistrationActivity.class);
                startActivity(intent);

            }
        });

    }

    private void LoginBtnClick() {
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getEditTextValue();

                if (mEmail == null || mEmail.isEmpty() || mEmail.equals("")) {
                    etEmail.setError("Enter Email");
                } else if (mPassword == null || mPassword.isEmpty() || mPassword.equals("")) {
                    etPassword.setError("Enter Password");
                } else if (!mEmail.matches(emailPattern)) {
                    Toast.makeText(LoginActivity.this, "Invalid Email", Toast.LENGTH_LONG).show();
                } else {
                    ApiCall(mEmail, mPassword);
                }
            }
        });
    }

    private void ApiCall(final String mEmail, final String mPassword) {
        Call<LoginModel> modelCall = mApiInterface.login(mEmail, mPassword);
        modelCall.enqueue(new Callback<LoginModel>() {
            @Override
            public void onResponse(Call<LoginModel> call, Response<LoginModel> response) {
                mResponseStatus = response.body().getStatus();
                if (mResponseStatus == 0) {
                    mSessionManagement.createLoginSession(mEmail, mPassword);
                    Toast.makeText(LoginActivity.this, "Login", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(), NavigationActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);

                } else {
                    Toast.makeText(LoginActivity.this, response.body().getMsg(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<LoginModel> call, Throwable t) {

            }
        });
    }

    private void getEditTextValue() {
        mEmail = etEmail.getText().toString();
        mPassword = etPassword.getText().toString();
    }
}