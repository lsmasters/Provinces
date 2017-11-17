package com.example.dell.provinces;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

/**
 * Created by dell on 11/7/2017.
 */

public class success extends AppCompatActivity {
    TextView tv;
    String n;
    int level;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_success);


        tv=(TextView) findViewById(R.id.tv_name);

        //get NAME and LEVEL from previous class

        n = getIntent().getStringExtra("name");
        level= getIntent().getIntExtra( n, 1 );
        //set title
        setTitle("CONGRATULATIONS "+n+"!");
        //add name
        tv.setText(n);

    }
}
