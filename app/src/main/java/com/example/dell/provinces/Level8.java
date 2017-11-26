package com.example.dell.provinces;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
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

public class Level8 extends AppCompatActivity {

    TextView number, question, correct;
    Button t, f;
    ImageView image;
    String[] pName = new String[]{"British Columbia", "Alberta", "Saskatchewan",
            "Manitoba", "Ontario", "Quebec", "New Brunswick", "Nova Scotia",
            "Prince Edward Island", "Newfoundland and Labrador", "Yukon Territory", "North West Territories", "Nunavut"};
    String[] maps = new String[]{"b1", "b2", "b3", "b4", "b5", "b6", "b7", "b8", "b9", "b10", "b11", "b12", "b13"};
    String n;
    int counter, level,q;
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
        number.setText("Welcome to Level 7, " + n);
        setTitle("Level 7 Provincial Birds");
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
        if (i > 10 && ((counter / (i - 1)) > .9)) {    //questions changed to for testing.....change back to 25
            //next level
            successDialog(n,level);
            level++;
            dbUpdate(n,level);

            Intent intent = new Intent(this, Level9.class);
            intent.putExtra("name", n);
            intent.putExtra(n, level);
            startActivity(intent);
        }
    }
    void successDialog(String nam,int mlevel){
        //create dialog object
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.custom);
        dialog.setTitle("           CONGRATULATIONS, "+n);

        // set the custom dialog components - text, image and button
        TextView c = (TextView) dialog.findViewById(R.id.tv_congrats);
        c.setText("CONGRATULATIONS, "+nam);
        TextView t = (TextView) dialog.findViewById(R.id.tv_level);
        t.setText("Level "+mlevel+" completed!");
        TextView c2 = (TextView) dialog.findViewById(R.id.tv_congrats2);
        c2.setText("Step 8:  Almost there!");
        //change for each level
        ImageView image = (ImageView) dialog.findViewById(R.id.iv_map);
        image.setImageResource(R.drawable.u9);//    change with level

        // if button is clicked, continue to next level
        final Button cont = (Button) dialog.findViewById(R.id.btn_continue);
        cont.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"Custom dialog button pressed ...",Toast.LENGTH_LONG).show();
            }
        });
        dialog.show();
        final MediaPlayer sound = MediaPlayer.create(this,R.raw.tada);
        sound.start();
        sound.start();
        sound.start();


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
        else
            question.setText(pName[(q + 2) % 13]);

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
        refChild.setValue(9);
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