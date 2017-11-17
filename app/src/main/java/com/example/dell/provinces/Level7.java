package com.example.dell.provinces;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Random;

import static android.R.attr.level;
import static android.R.attr.name;

public class Level7 extends AppCompatActivity {

    TextView number, question, correct;
    Button t, f;
    ImageView image;
    String[] pName = new String[]{"BC", "AB", "SK",
            "MB", "ON", "QC", "NB", "NS",
            "PE", "NL", "YT", "NT", "NU"};
    String[] maps = new String[]{"m1", "m2", "m3", "m4", "m5", "m6", "m7", "m8", "m9", "m10", "m11", "m12", "m13"};
    String n;
    int counter, level, nq,q;
    boolean answer;
    int i = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level1);

        number = (TextView) findViewById(R.id.questionNumber);
        question = (TextView) findViewById(R.id.question);
        correct = (TextView) findViewById(R.id.numberCorrect);
        t = (Button) findViewById(R.id.trueBtn);
        f = (Button) findViewById(R.id.falseBtn);
        image = (ImageView) findViewById(R.id.showProvince);

        //get name from previous class
        Intent ii = getIntent();
        n = ii.getStringExtra("name");
        level = ii.getIntExtra(n, 1);
        //send welcome message to screen
        number.setText("Welcome to Level 7, " + n);
        setTitle("Level 7: Provincial Abbreviations");
        //shuffle the order of the questions
        int[] q = new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12};

        int map;
        String str;
        //shuffleArray(q);

        //present the questions
        counter = 0;

        setQuestion();

        //set true/false listeners
        t.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (answer)
                    counter++;
                else errorDialog();
                checkScore();
                correct.setText("Correct:  " + counter + " out of " + (i + 1));
                i++;
                setQuestion();
            }
        });
        f.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!answer)
                    counter++;
                else errorDialog();
                checkScore();
                correct.setText("Correct:  " + counter + " out of " + (i + 1));
                i++;
                setQuestion();
            }
        });

    }

    //check score to see if ready for next level
    private void checkScore() {
        if (i > 10 && ((counter / (i - 1)) > .9)) {           //i changed to 10 for testing only...set back to 25
            //goto SUCCESS screen to end level
            Intent intent1 = new Intent(getApplicationContext(), succ.class);
            intent1.putExtra("name", n);
            intent1.putExtra(n, level);
            Toast.makeText(this, "You should see SUCCESS screen NOW", Toast.LENGTH_LONG).show();
            startActivity(intent1);

            level++;
            dbUpdate(n,level);
            Intent intent2 = new Intent(this, Level8.class);
            intent2.putExtra("name", n);
            intent2.putExtra(n, level);
            startActivity(intent2);

        }
    }

    //setup questions
    private void setQuestion() {
        number.setText("Question " + (i + 1));
        //randomly select the province
        Random r = new Random();
        q = r.nextInt(13);
        //show map of selected province
        int resID = getResources().getIdentifier(maps[q], "drawable", getPackageName());
        image.setImageResource(resID);
        //find random true/false
        Random tf = new Random();
        answer = tf.nextBoolean();
        //if true...do
        if (answer) {
            question.setText(pName[q]);
        }
        //else if false, pick a province two away
        else {
            do {
                nq = r.nextInt(12);
                question.setText(pName[(q + nq) % 12]);
            } while (q != nq);
        }
    }

    protected void onPause() {
        super.onPause();
        SharedPreferences sp = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor e = sp.edit();
        e.putInt(n, level);
    }

    void dbUpdate(String name, int level) {
        FirebaseDatabase db = FirebaseDatabase.getInstance();     //"https://greatcanadianrace.firebaseio.com/");
        DatabaseReference ref = db.getReference("user");
        //add data to db
        DatabaseReference refChild = ref.child(name);
        refChild.setValue(8);
    }
    void errorDialog(){
        AlertDialog.Builder ad = new AlertDialog.Builder(this);
        ad.setMessage("TRIP.....STUMBLE...CRASH!\n\nThe CORRECT answer is \n\n"+pName[q]+"\n")
                .setTitle("         OOPS!!!")
                .setIcon(R.drawable.tripped)
                .setPositiveButton("CONTINUE", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                })
                .setCancelable(false)
                .create()
                .show();
    }
}