package com.example.phoneauthdemo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.hbb20.CountryCodePicker;

public class MobileEntryActivity extends BaseActivity{

    CountryCodePicker ccp;
    private FirebaseAuth mAuth;

    Button btn;
    EditText editTextCarrierNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ccp = (CountryCodePicker) findViewById(R.id.ccp);
        editTextCarrierNumber = (EditText) findViewById(R.id.editText_carrierNumber);
        ccp.registerCarrierNumberEditText(editTextCarrierNumber);


        btn = findViewById(R.id.button);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //|| editTextCarrierNumber.getText().toString().trim().length() != 10
                if (editTextCarrierNumber.getText().toString().trim().isEmpty()
                        ) {
                    Toast.makeText(MobileEntryActivity.this, "Invalid number", Toast.LENGTH_LONG).show();
                } else {
                    Intent i = new Intent(MobileEntryActivity.this, OtpActivity.class);
                    i.putExtra("mobileNo", ccp.getFullNumberWithPlus().replace(" ", ""));
                    startActivity(i);
                }


            }
        });
    }
}



