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

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForgotPasswordActivity extends AppCompatActivity {

    EditText etEmail;
    Button btnSubmit;
    String mEmail;
    int mResponseStatus;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+[a-z]";
    TextView txtBackToLogin;
    private ApiInterface mApiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        initialization();
    }

    private void initialization() {
        mApiInterface = ApiClient.getClient().create(ApiInterface.class);
        etEmail = (EditText) findViewById(R.id.etEmail);
        btnSubmit = (Button) findViewById(R.id.btnSubmit);
        txtBackToLogin = (TextView) findViewById(R.id.txtClickToLogin);
        SubmitBtnClick();
        backToLogin();
    }

    private void backToLogin() {
        txtBackToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), LoginActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i);
            }
        });

    }

    private void SubmitBtnClick() {
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 getEditTextValue();

                if (mEmail == null || mEmail.isEmpty() || mEmail.equals("")) {
                    etEmail.setError("Enter Email");
                } else if (!mEmail.matches(emailPattern)) {
                    etEmail.setError("Invalid  Email");
                } else {
                    Toast.makeText(getApplicationContext(), "Submit", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(getApplicationContext(), LoginActivity.class);
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

                    startActivity(i);

                    //    ApiCall(mEmail);

                }
            }
        });
    }

    private void ApiCall(String mEmail) {
        Call<LoginModel> modelCall = mApiInterface.forgatepassword(mEmail);
        modelCall.enqueue(new Callback<LoginModel>() {
            @Override
            public void onResponse(Call<LoginModel> call, Response<LoginModel> response) {
                mResponseStatus = response.body().getStatus();
                if (mResponseStatus == 0) {
                    Toast.makeText(getApplicationContext(), "Submit", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(getApplicationContext(), LoginActivity.class);
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(i);
                }
            }

            @Override
            public void onFailure(Call<LoginModel> call, Throwable t) {

            }
        });
    }

    private void getEditTextValue() {
        mEmail = etEmail.getText().toString();
    }
}
