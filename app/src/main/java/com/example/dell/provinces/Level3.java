package com.example.dell.provinces;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.net.Uri;
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

public class Level3 extends AppCompatActivity {

    TextView number, question, correct;
    Button t, f;
    ImageView image;
    String[] pName = new String[]{"EAST", "EAST", "EAST", "EAST", "NORTH",
            "NORTH", "WEST", "EAST", "EAST", "EAST",
            "WEST", "WEST", "EAST", "EAST", "NORTH",
            "NORTH", "WEST", "WEST", "EAST", "EAST",
            "WEST", "NORTH", "WEST", "EAST", "EAST"};
    String[] maps = new String[]{"d1", "d2", "d3", "d4", "d5", "d6", "d7", "d8", "d9", "d10", "d11", "d12", "d13", "d14", "d15", "d16", "d17", "d18", "d19", "d20", "d21", "d22", "d23", "d24", "d25"};
    String n, choice;
    int counter, level, x,q;
    boolean answer;
    int i = 0;
    Uri webpage;

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
        number.setText("Welcome to Level 3, " + n);
        setTitle("Level 3 Directions");
        //shuffle the order of the questions
        int[] q = new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24};

        int map;
        String str;

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
        if (i > 4 && ((counter / (i - 1)) > .9)) {    //questions changed to for testing.....change back to 25
            //next level
            successDialog(n,level);
            level++;
            dbUpdate(n, level);
            Intent intent = new Intent(this, Level4.class);
            intent.putExtra("name", n);
            intent.putExtra(n, level);
            startActivity(intent);
        }
        if (i>4 &&((counter/(i-1))<.5) ) {
            troubleDialog(n, level);
        }
    }
    public void troubleDialog(String nam, int mlevel){
        //create dialog object
        Dialog dialog2 = new Dialog(this);
        dialog2.setContentView(R.layout.activity_tryagain);
        dialog2.show();
        TextView tv = (TextView) dialog2.findViewById(R.id.textView2);
        tv.setText("You are having some trouble, "+n);

        final Button help = (Button) dialog2.findViewById(R.id.btn_helpingApp);
        help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch(level){
                    case 1: // app for names of provinces
                        webpage = Uri.parse("https://lizardpoint.com/geography/canada-quiz.php");
                        break;
                    case 2: // app for shapes of provinces
                        webpage = Uri.parse("https://online.seterra.com/en/vgp/3006)");
                        break;
                    case 3:  // app for directions of provinces
                        webpage = Uri.parse("https://www.helpfulgames.com/subjects/geography/242-cardinal-directions.html");
                        break;
                    case 4:  // app for capitals
                        webpage = Uri.parse("https://www.sporcle.com/games/g/canadacapitals");
                        break;
                    case 5:  // app for adjacency
                        webpage = Uri.parse("https://online.seterra.com/en/vgp/3006)");
                        break;
                    case 6:  // app for flags
                        webpage = Uri.parse("http://www.cbc.ca/kindscbc2/games/match-the-flag-canadian-provinces");
                        break;
                    case 7:  // app for birds
                        webpage = Uri.parse("https://cottagelife.com/general/quiz-do-you-know-the-official-bird-of-every-province-and-territory/");
                        break;
                    case 8:  // app for flowers
                        webpage = Uri.parse("http://www.canadafloraldelivery.com/provincial-and-territorial-flowers-of-canada");
                        break;
                    default:
                }
                Intent webintent = new Intent(Intent.ACTION_VIEW,webpage);
                startActivity(webintent);
            }
        });

        final Button collaborate = (Button) dialog2.findViewById(R.id.btnCollaborate);
        collaborate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"Sorry but not implemented yet!",Toast.LENGTH_LONG).show();
            }
        });

        final Button done = (Button) dialog2.findViewById(R.id.btnQuit);
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"Please speak with your teacher.",Toast.LENGTH_LONG).show();
            }
        });

        final MediaPlayer sound = MediaPlayer.create(this,R.raw.oops);
        sound.start();

        dialog2.show();
    }
    //setup questions
    private void setQuestion() {
        number.setText("Question " + (i + 1));
        //randomly select the province
        Random r = new Random();
        q = r.nextInt(25);
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
        //else if false, pick another direction
        else {
            choice = pName[q];
            while (choice == pName[q]) {
                r = new Random();
                x = r.nextInt(24);
                choice = pName[x];
            }
            question.setText(choice);
        }
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
        refChild.setValue(4);
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
        c2.setText("Step 3:  Wow! Keep going!");
        //change for each level
        ImageView image = (ImageView) dialog.findViewById(R.id.iv_map);
        image.setImageResource(R.drawable.u4);//    change with level

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