package com.applications.jfischer.anti_memory;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Klasse zur Ausgabe der Highscore-Liste, die in einer Datenbank verwaltet ist.
 */
public class HighscoreActivity extends AppCompatActivity {

    private HighscoreListDataSource dataSource;
    private ListView highScoreListView;
    private Button backButton;
    private Button deleteButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_highscore);

        backButton = (Button) findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goBack();
            }
        });

        deleteButton = (Button) findViewById(R.id.deleteButton);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteHighscoreList();
            }
        });

        highScoreListView = (ListView) findViewById(R.id.highScoreListView);

       updateDatabase();
    }

    /**
     * Überprüft ob innerhalb des Intent, der für das Starten der Activity verantwortlich war,
     * ein Highscore-Eintrag mitgegeben wurden. Falls dies der Fall ist wird der Eintrag in der
     * Datenbank eingetragen. Dannach wird die Methode showAllListEntries() aufgerufen.
     */
    private void updateDatabase(){
        Intent intent = getIntent();

        dataSource = new HighscoreListDataSource(this);
        dataSource.open();

        if(intent.getExtras() != null){
            dataSource.createHighscoreEntry(intent.getStringExtra("name"), intent.getIntExtra("score", 0));
        }

        showAllListEntries();

        dataSource.close();
    }


    /**
     * Gibt alle Highscore-Einträge in einer List-View aus, wobei diese absteigend nach der
     * Punktzahl sortiert sind.
     * Quellen: http://stackoverflow.com/questions/16425127/how-to-use-collections-sort-in-java-specific-situation
     *          http://stackoverflow.com/questions/10709803/java-comparator-how-to-sort-by-integer
     */
    private void showAllListEntries(){
       List<HighscoreEntry> highscoreEntrylist = dataSource.getAllHighscoreEntries();

        Collections.sort(highscoreEntrylist, new Comparator<HighscoreEntry>() {
            @Override
            public int compare(HighscoreEntry highscoreEntry, HighscoreEntry t1) {
                return  t1.getScore() - highscoreEntry.getScore();
            }
        });

        ArrayAdapter<HighscoreEntry> highscoreEntryArrayAdapter = new ArrayAdapter<>(
                this, android.R.layout.simple_list_item_1, highscoreEntrylist);

        highScoreListView.setAdapter(highscoreEntryArrayAdapter);
        }

    /**
     * Löscht alle Einträge der Datenbank und startet die Highscore-Activity neu.
     */
    private void deleteHighscoreList(){
        dataSource.delete();
        Intent intent =  new Intent(this, HighscoreActivity.class);
        finish();
        startActivity(intent);
    }

    /**
     * Beendet die aktuelle Activity.
     */
    private void goBack(){
        this.finish();
    }
}
