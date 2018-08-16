package com.example.android.androidandregistration.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.androidandregistration.R;
import com.example.android.androidandregistration.model.RegistrationModel;
import com.example.android.androidandregistration.retrofit.ApiClient;
import com.example.android.androidandregistration.retrofit.ApiInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegistrationActivity extends AppCompatActivity {
    private static final String TAG = "RegistrationActivity";
    EditText etFirstName, etLastName, etEmail, etContact, etPassword;
    RadioGroup radioGroupGender;
    String mFirstName, mLastName, mEmail, mContact, mPassword, mGender = "male";
    Button btnSubmit;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+[a-z]";
    ApiInterface mApiInterface;
    int mResponse;
    TextView txtClockToLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        initialization();
    }

    private void initialization() {
        mApiInterface = ApiClient.getClient().create(ApiInterface.class);
        txtClockToLogin = (TextView) findViewById(R.id.txtClickToLogin);
        etFirstName = (EditText) findViewById(R.id.etFirstName);
        etLastName = (EditText) findViewById(R.id.etLastName);
        etEmail = (EditText) findViewById(R.id.etEmail);
        etContact = (EditText) findViewById(R.id.etContact);
        etPassword = (EditText) findViewById(R.id.etPassword);
        radioGroupGender = (RadioGroup) findViewById(R.id.radioGender);
        btnSubmit = (Button) findViewById(R.id.btnSubmit);
        btnClickSubmit();
        clickToLoginPage();
    }

    private void clickToLoginPage() {
        txtClockToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), LoginActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i);
            }
        });

    }

    private void btnClickSubmit() {
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                getTextViewValue();
                if (mFirstName == null || mFirstName.isEmpty() || mFirstName.equals("")) {
                    etFirstName.setError("Enter FirstName");
                } else if (mLastName == null || mLastName.isEmpty() || mLastName.equals("")) {
                    etLastName.setError("Enter LastName");
                } else if (mEmail == null || mEmail.isEmpty() || mEmail.equals("")) {
                    etEmail.setError("Enter Email");
                } else if (mPassword == null || mPassword.isEmpty() || mPassword.equals("")) {
                    etPassword.setError("Enter Password");
                } else if (mContact == null || mContact.isEmpty() || mContact.equals("")) {
                    etContact.setError("Enter Contact");
                } else if (!mEmail.matches(emailPattern)) {
                    etEmail.setError("Invalid Email Address ");
                } else if (mContact.length() < 10) {
                    etContact.setError("Enter 10 Digit Contact ");
                } else {
                    apiCall();
                }
            }
        });
    }

    private void apiCall() {
        Call<RegistrationModel> activityCall = mApiInterface.registration(mFirstName, mLastName, mEmail, mPassword, mGender, mContact);
        activityCall.enqueue(new Callback<RegistrationModel>() {
            @Override
            public void onResponse(Call<RegistrationModel> call, Response<RegistrationModel> response) {
                mResponse = response.body().getStatus();

                if (mResponse == 0) {
                    Toast.makeText(RegistrationActivity.this, response.body().getMsg(), Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(getApplicationContext(), LoginActivity.class);
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(i);
                }
                Log.e(TAG, "onResponse: " + mResponse);
            }

            @Override
            public void onFailure(Call<RegistrationModel> call, Throwable t) {
                Log.e(TAG, "onFailure: " + t);
            }
        });
    }

    private void getTextViewValue() {
        mFirstName = etFirstName.getText().toString();
        mLastName = etLastName.getText().toString();
        mContact = etContact.getText().toString();
        mEmail = etEmail.getText().toString();
        mPassword = etPassword.getText().toString();
        radioGroupGender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i) {
                    case R.id.radioBtnMale:
                        mGender = "male";
                        break;
                    case R.id.radioBtnFemale:
                        mGender = "female";
                        break;
                }
            }
        });
    }
}
