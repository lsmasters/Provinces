package com.example.dell.provinces;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
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

public class Level5 extends AppCompatActivity {

    TextView number, question, correct;
    Button t, f;
    ImageView image;
    Boolean[] pName = new Boolean[]{true, true, false, true, false,
            true, true, true, false, false,
            true, true, true, false, false,
            true, true, true, true, false,
            true, true, true, true, false,
            true, true, true, true, true,
            false, false, true, true, false,
            false, false, true, false, false,
            true, true, false, false, true,
            true, false, true, false, true,
            false, true, false, true, true,
            true, false};
    String[] maps = new String[]{"a1", "a2", "a3", "a4", "a5", "a6", "a7", "a8", "a9", "a10",
            "a11", "a12", "a13", "a14", "a15", "a16", "a17", "a18", "a19", "a20",
            "a21", "a22", "a23", "a24", "a25", "a26", "a27", "a28", "a29", "a30",
            "a31", "a32", "a33", "a34", "a35", "a36", "a37", "a38", "a39", "a40",
            "a41", "a42", "a43", "a44", "a45", "a46", "a47", "a48", "a49", "a50",
            "a51", "a52", "a53", "a54", "a55", "a56", "a57"};
    String n;
    int counter, level, x,q;
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

        //get name from starRt screen
        Intent intent = getIntent();
        n = intent.getStringExtra("name");
        level = intent.getIntExtra(n, 3);
        //send welcome message to screen
        number.setText("Welcome to Level 5, " + n);
        setTitle("Level 5 NEIGHBOURS");
        //shuffle the order of the questions
        int[] q = new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9,
                10, 11, 12, 13, 14, 15, 16, 17, 18, 19,
                20, 21, 22, 23, 24, 25, 26, 27, 28, 29,
                30, 31, 32, 33, 34, 35, 36, 37, 38, 39,
                40, 41, 42, 43, 44, 45, 46, 47, 48, 49,
                50, 51, 52, 53, 54, 55, 56};

        int map;
        String str;

        //present the questions
        counter = 0;

        setQuestion();

        //set true/false listeners
        t.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (answer == true)
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
                if (answer == false)
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
        if (i > 10 && ((counter / (i - 1)) > .9)) {    //questions changed to for testing.....change back to 25
            //next level
            Toast.makeText(this, "CONGRATULATIONS.....You have completed Level 5", Toast.LENGTH_LONG).show();
            level++;
            dbUpdate(n,level);
            Intent intent = new Intent(this, Level6.class);
            intent.putExtra("name", n);
            intent.putExtra(n, level);
            startActivity(intent);
        }
    }

    //setup questions
    private void setQuestion() {
        number.setText("Question " + (i + 1));
        //randomly select the province
        Random r = new Random();
        q = r.nextInt(56);
        //show map of selected province
        int resID = getResources().getIdentifier(maps[q], "drawable", getPackageName());
        image.setImageResource(resID);
        //set true/false
        answer = pName[q];

    }

    //shuffle elements of an array
    private static void shuffleArray(int[] array) {
        int index, temp;
        Random random = new Random();
        for (int i = array.length - 1; i > 0; i--) {
            index = random.nextInt(i + 1);
            temp = array[index];
            array[index] = array[i];
            array[i] = temp;
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
        refChild.setValue(6);
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