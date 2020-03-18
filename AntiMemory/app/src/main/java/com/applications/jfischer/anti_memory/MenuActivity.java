package com.applications.jfischer.anti_memory;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

/**
 * Klasse der Activity, die beim Start der App geöffnet wird.
 * Von hier aus werden die restlichen Activies gestartet.
 */
public class MenuActivity extends AppCompatActivity {

    private ImageButton buttonPlay;
    private ImageButton buttonHighscore;
    private ImageButton buttonHelp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_acticity);

        buttonPlay = (ImageButton) findViewById(R.id.buttonPlay);
        buttonPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startPlayActivity();
            }
        });
        buttonPlay.setImageResource(R.drawable.play);

        buttonHighscore = (ImageButton) findViewById(R.id.buttonHighscore);
        buttonHighscore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startHighscoreActivity();
            }
        });
        buttonHighscore.setImageResource(R.drawable.highscore);

        buttonHelp = (ImageButton) findViewById(R.id.buttonHelp);
        buttonHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startHelpActivity();
            }
        });
        buttonHelp.setImageResource(R.drawable.help);
    }

    /**
     * Methode startet die Play-Activity mit Hilfe eines Intents.
     */
    private void startPlayActivity(){
        Intent intent = new Intent(this, PlayActivity.class);
        startActivity(intent);
    }

    /**
     * Methode startet die Highscore-Activity mit Hilfe eines Intents.
     */
    private void  startHighscoreActivity(){
        Intent intent = new Intent(this, HighscoreActivity.class);
        startActivity(intent);
    }

    /**
     * Methode startet die Help-Activity mit Hilfe eines Intents.
     */
    private void  startHelpActivity(){
        Intent intent = new Intent(this, HelpActivity.class);
        startActivity(intent);
    }
}
