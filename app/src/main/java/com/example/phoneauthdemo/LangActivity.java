package com.example.phoneauthdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

public class LangActivity extends BaseActivity implements  AdapterView.OnItemSelectedListener {

    Spinner spinner;
    ArrayAdapter<CharSequence> adapter;
    String lang;
    Button btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lang);

        btn=findViewById(R.id.button3);

        spinner = (Spinner) findViewById(R.id.spinner);


         adapter = ArrayAdapter.createFromResource(this,
                R.array.lang_array, android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

    spinner.setAdapter(adapter);


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setNewLocale(LangActivity.this,lang);
                startActivity(new Intent(LangActivity.this, MobileEntryActivity.class));
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        spinner.setOnItemSelectedListener(this);


    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                   lang= (String) parent.getItemAtPosition(position);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
parent.getCount();

    }

    private void setNewLocale(AppCompatActivity mContext, @LocalManager.LocaleDef String language) {
        LocalManager.setNewLocale(this, language);

    }
}