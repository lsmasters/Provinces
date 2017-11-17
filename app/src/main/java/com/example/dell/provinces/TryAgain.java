package com.example.dell.provinces;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;



/**
 * Created by dell on 11/7/2017.
 */

public class TryAgain extends AppCompatActivity {
    Button help,collaborate,quit;
    int level;
    String name;
    Uri webpage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tryagain);



        help=(Button) findViewById(R.id.btn_helpingApp);
        collaborate=(Button) findViewById(R.id.btnCollaborate );
        quit=(Button) findViewById(R.id.btn_quit);

        //get level and name
        //get NAME and LEVEL from previous class
        name = getIntent().getStringExtra("name");
        level= getIntent().getIntExtra(name,1);


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
            }
        });

        collaborate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //find a student who is ahead...list available students(finished)
                //send a msg to student for help
                //quit
            }
        });

        quit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //send a msg to teacher
                //quit
            }
        });
    }
}
