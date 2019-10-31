package com.example.mymallx;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class OTPverificationActivity extends AppCompatActivity {

    TextView mobileNo;
    TextView resultText;
    EditText otp;
    Button verifiBtn;
    String phone;
    public static boolean thanquActivity=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otpverification);

        mobileNo=findViewById(R.id.VerefiedmobileNoID);
        otp=findViewById(R.id.OtpEditeTextId);
        verifiBtn=findViewById(R.id.verifiId);
        resultText=findViewById(R.id.resultTextId);
        phone=getIntent().getStringExtra("mobileNo");
        mobileNo.setText(phone);

        verifiBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String OTP=otp.getText().toString();
                if (OTP.equals("1234"))
                {
                    thanquActivity=true;
                    finish();
                    Intent deliveryIntent=new Intent(getApplicationContext(),DeliveryActivity.class);
                    startActivity(deliveryIntent);
                    Toast.makeText(getApplicationContext(),"OTP match sucessfully",Toast.LENGTH_LONG).show();

                }else
                {
                    resultText.setText("Your OTP is not match!!");
                }
            }
        });
    }
}
