package com.applications.jfischer.anti_memory;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.KeyListener;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Fungiert innerhalb des MVC-Patterns als View.
 */
public class PlayActivity extends AppCompatActivity implements Viewer{

    private int numOfCards;
    private Control control;
    private ImageView[] imageViewArray;
    private Button newCardsButton;
    private TextView pointView;
    private Chronometer chronometer;
    private int buttonStatus;
    private Button enterNameButton;
    private EditText enterNameText;
    private String time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);

        time = "03:00"; //Der Spieler hat 3 min Zeit

        startChronometer();

        pointView = (TextView) findViewById(R.id.textView2);

        newCardsButton = (Button) findViewById(R.id.button);
        newCardsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                   newCardsButtonClicked();
            }
        });

        enterNameText = (EditText) findViewById(R.id.editText);
        enterNameText.setTag(enterNameText.getKeyListener()); //Quelle: http://stackoverflow.com/questions/3928711/how-to-make-edittext-not-editable-through-xml-in-android
        enterNameText.setKeyListener(null);

        enterNameButton = (Button) findViewById(R.id.button2);

        buttonStatus = 1;
        numOfCards = 9;
        control = new Control(this);
        imageViewArray = new ImageView[9];

        findViews();

        setOnClickListeners();

        control.makeNewCards();
    }

    /**
     * Weißt jeder ImageView im Array einen OnClickListener zu. Wobei hier ein
     * OnImageviewClickListener verwedet wird, der von OnClickListener erbt und darum den Index
     * der ImageView mit abspeichern kann.
     */
    private void setOnClickListeners(){
        for(int i = 0; i < numOfCards; i++){
            imageViewArray[i].setOnClickListener(new OnImageviewClickListener(i){
                @Override
                public void onClick(View v){
                    cardClicked(index);
                }
            });
        }
    }

    /**
     * Weißt jedem Platz im Array eine ImageView zu die in der xml-Datei erstellt wurde.
     */
    private void findViews(){
        imageViewArray[0] = (ImageView) findViewById(R.id.imageView);
        imageViewArray[1] = (ImageView) findViewById(R.id.imageView2);
        imageViewArray[2] = (ImageView) findViewById(R.id.imageView3);
        imageViewArray[3] = (ImageView) findViewById(R.id.imageView4);
        imageViewArray[4] = (ImageView) findViewById(R.id.imageView5);
        imageViewArray[5] = (ImageView) findViewById(R.id.imageView6);
        imageViewArray[6] = (ImageView) findViewById(R.id.imageView7);
        imageViewArray[7] = (ImageView) findViewById(R.id.imageView8);
        imageViewArray[8] = (ImageView) findViewById(R.id.imageView9);
    }

    /**
     * Wird aufgerufen wenn eien ImageView geclickt wurde.
     * @param index Der Index der ImageView muss übergeben werden, um die folgenden Aktionen nur für
     *              die eine ImageView durchzuführen.
     */
    private void cardClicked(int index) {
            control.changeCardStatus(index);
            control.changeActiveCardsStatus(index);
        }

    /**
     * Wird bei einer Veränderung im Model aufgerufen, um den Veränderungen entsprechend auch die
     * View verändern zu können.
     */
    public void updateCard(){
        pointView.setText("Points: " + control.getPoints());
        for(int i = 0; i < 9; i++) {
            if (control.getCard(i) == null) {
                imageViewArray[i].setImageDrawable(null);
                imageViewArray[i].setOnClickListener(null);
            } else {
                int imageResource = control.getCard(i).cardToPicture(this);
                imageViewArray[i].setImageResource(imageResource);
            }
        }
    }

    /**
     * Wird aufgerufen, wenn der NewCards Button geclickt wurden. Falls sich dieser im Zustand 1
     * befindet, d.h. dass das Spiel läuft, werden neue Karten erstellt.
     * Falls er sich im Zustand 2 befindet, wird die aktuelle Activity beendet und man kehrt ins Menü
     * zurück.
     */
    private void newCardsButtonClicked() {
        if (buttonStatus == 1) { //buttonStatus 1 = Spiel läuft
            setOnClickListeners();
            control.makeNewCards();
        }else{
            this.finish();
        }
    }

    /**
     * Wird zu Beginn des Spiel aufgerufen und startet ein Chronometer, welches drei Minuten läuft.
     * Nachdem das Chronometer abgelaufen ist, wird die Aufschrift des newCardsButton geändert,
     * damit klar ist, dass man ins Menü zurückkehren kann. Außerdem ist es nun möglich seine Punkt-
     * zahl in die Highscore-Liste einzutragen.
     */
    private void startChronometer(){
        chronometer = (Chronometer) findViewById(R.id.chronometer);
        chronometer.start();
        chronometer.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
            @Override
            public void onChronometerTick(Chronometer chronometer) {
                if(chronometer.getText().toString().equals(time)){

                    chronometer.stop();

                    enterNameText.setKeyListener((KeyListener) enterNameText.getTag());
                    enterNameText.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            enterNameText.setText(" ");
                        }
                    });

                    enterNameButton.setText("Enter Score");
                    enterNameButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            enterNameButtonClicked(enterNameText.getText().toString());
                        }
                    });

                    for(int i = 0; i < numOfCards; i++){
                        imageViewArray[i].setOnClickListener(null);
                    }

                    Toast.makeText(getApplicationContext(), "GAME OVER!!!", Toast.LENGTH_LONG).show();
                    buttonStatus = 2; //buttonStatus 2 = Spiel vorbei

                    newCardsButton.setText("Menu");
                }
            }
        });
    }

    /**
     * Öffnet mit Hilfe eines Intents die Highscore Activity und übergibt durch den Intent auch den
     * Name und die Punktzahl des Spielers.
     * @param name Wird übergeben, nachdem er aus dem EditText ausgelesen wurde.
     */
    private void enterNameButtonClicked(String name){
        Intent intent = new Intent(this, HighscoreActivity.class);
        intent.putExtra("name", name);
        intent.putExtra("score", control.getPoints());
        startActivity(intent);
        this.finish();
    }
}
