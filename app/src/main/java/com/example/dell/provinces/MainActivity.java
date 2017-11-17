package com.example.dell.provinces;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Random;




public class MainActivity extends AppCompatActivity {
    EditText name;
    Button submit;
    int level;
    String n;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        name=(EditText)findViewById(R.id.nameInput);
        submit=(Button)findViewById(R.id.submitBtn);



        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                n=name.getText().toString().trim();
                Toast.makeText(MainActivity.this, "Welcome, "+ n,Toast.LENGTH_LONG).show();
                SharedPreferences sp = getPreferences(MODE_PRIVATE);

                FirebaseDatabase db = FirebaseDatabase.getInstance();     //"https://greatcanadianrace.firebaseio.com/");
                DatabaseReference ref=db.getReference("user");
                DatabaseReference refChild=ref.child(n);

                    refChild.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            String lev = dataSnapshot.getValue().toString();
                            level = Integer.parseInt(lev);
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            Toast.makeText(MainActivity.this, "DATABASE FAILURE See your teacher", Toast.LENGTH_LONG).show();
                        }
                    });


                Toast.makeText(MainActivity.this, "You will start at level "+level, Toast.LENGTH_LONG).show();

                Intent intent;

                //change to appropriate starting level
                switch(level){
                    case 1:
                        intent= new Intent(MainActivity.this, Level1.class);
                        break;
                    case 2:
                        intent= new Intent(MainActivity.this,Level2.class);
                        break;
                    case 3:
                        intent= new Intent(MainActivity.this,Level3.class);
                        break;
                    case 4:
                        intent= new Intent(MainActivity.this,Level4.class);
                        break;
                    case 5:
                        intent= new Intent(MainActivity.this,Level5.class);
                        break;
                    case 6:
                        intent= new Intent(MainActivity.this,Level6.class);
                        break;
                    case 7:
                        intent= new Intent(MainActivity.this,Level7.class);
                        break;
                    case 8:
                        intent= new Intent(MainActivity.this,Level8.class);
                        break;
                    case 9:
                        intent= new Intent(MainActivity.this,Level9.class);
                        break;
                    case 10:
                        intent= new Intent(MainActivity.this,Level10.class);
                        break;
                    default:
                        level=1;
                        intent= new Intent(MainActivity.this,Level1.class);
                        break;
                }

                //pass name and level to the level
                intent.putExtra("name", n);
                intent.putExtra(n, level);
                //start next level
                startActivity(intent);

            }
        });
    }
    protected void onPause() {
        super.onPause();
        SharedPreferences sp = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor e = sp.edit();
        e.putInt(n, level);
    }
}
