package com.example.connectingus;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

public class OTPVerify extends AppCompatActivity {
    ConstraintLayout layout;
    Button resend_sms,wrong_number;
    TextView timer;
    TextView ph_number;
    String number;
    Button btn_continue;
    EditText digit1,digit2,digit3,digit4,digit5,digit6;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.verify_otp);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        timer = findViewById(R.id.timer);
        resend_sms = findViewById(R.id.resend_sms);
        wrong_number = findViewById(R.id.wrong_number);
        layout = findViewById(R.id.layout);
        ph_number = findViewById(R.id.ph_number);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            number = extras.getString("number");
        }
        ph_number.setText("Verify +91 "+number);
        setTimer();
        resend_sms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resend_sms.setEnabled(false);
                setTimer();
            }
        });
        wrong_number.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),NumberVerify.class);
                startActivity(intent);
            }
        });
        digit1 = findViewById(R.id.digit1);
        digit2 = findViewById(R.id.digit2);
        digit3 = findViewById(R.id.digit3);
        digit4 = findViewById(R.id.digit4);
        digit5 = findViewById(R.id.digit5);
        digit6 = findViewById(R.id.digit6);
        btn_continue = findViewById(R.id.btn_continue);
        digit1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void afterTextChanged(Editable editable) {
                if (digit1.getText().toString().length() == 1) {
                    digit2.requestFocus();
                }
            }
        });
        digit2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void afterTextChanged(Editable editable) {
                if (digit2.getText().toString().length() == 1) {
                    digit3.requestFocus();
                }
                if (digit2.getText().toString().length() == 0) {
                    digit1.requestFocus();
                }
            }
        });
        digit3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void afterTextChanged(Editable editable) {
                if (digit3.getText().toString().length() == 1) {
                    digit4.requestFocus();
                }
                if (digit3.getText().toString().length() == 0) {
                    digit2.requestFocus();
                }
            }
        });
        digit4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void afterTextChanged(Editable editable) {
                if (digit4.getText().toString().length() == 1) {
                    digit5.requestFocus();
                }
                if (digit4.getText().toString().length() == 0) {
                    digit3.requestFocus();
                }
            }
        });
        digit5.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void afterTextChanged(Editable editable) {
                if (digit5.getText().toString().length() == 1) {
                    digit6.requestFocus();
                }
                if (digit5.getText().toString().length() == 0) {
                    digit4.requestFocus();
                }
            }
        });
        digit6.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void afterTextChanged(Editable editable) {
                if (digit6.getText().toString().length() == 0) {
                    digit5.requestFocus();
                }
            }
        });
    }
    public void setTimer(){
        new CountDownTimer(60000, 1000) {
            public void onTick(long millisUntilFinished) {
                timer.setText("00 : " + millisUntilFinished / 1000);
            }

            public void onFinish() {
                timer.setText("00 : 00");
                resend_sms.setEnabled(true);
            }
        }.start();
    }
}
