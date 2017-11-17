package com.example.dell.provinces;

import android.support.v7.app.AppCompatActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


public class succ extends AppCompatActivity {
    String[] maps=new String[]{"u2","u3","u4","u5","u6","u7","u8","u9"};
    TextView name,lev;
    ImageView im;
    Button cont,quit;
    String n,newLevel;
    int level;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_succ);

        name=(TextView) findViewById(R.id.tb_name);
        lev=(TextView) findViewById(R.id.tb_level);
        im=(ImageView) findViewById(R.id.iv_success);
        cont=(Button) findViewById(R.id.btn_continue);
        quit=(Button) findViewById(R.id.btn_quit);

        //get name from previous class
        n = getIntent().getStringExtra("name");
        level= getIntent().getIntExtra( n, 1 );
        //send name and level to success screen
        name.setText(n);
        lev.setText(level);
        //advance  to next level
        level++;
        //show success map
         int resID = getResources().getIdentifier(maps[level-2], "drawable",  getPackageName());
         im.setImageResource(resID);

        //send new level to firebase
        //to be done
        //on click listener for CONTINUE

        cont.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (level<10){
                    //return to calling class
                    finish();
                } else {
                    //go to final winning screen
                }
            }
        });

        quit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //exit
                System.exit(0);
            }
        });
    }
}