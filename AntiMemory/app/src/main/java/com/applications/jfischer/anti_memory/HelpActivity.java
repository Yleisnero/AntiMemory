package com.applications.jfischer.anti_memory;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Klasse, in der die Spielanleitung angezeigt wird.
 */
public class HelpActivity extends AppCompatActivity {

    private Button backButton;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);

        backButton = (Button) findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goBack();
            }
        });

        textView = (TextView) findViewById(R.id.textView);

        readTxt();
    }

    /**
     * Ließt den Text aus, der im Assets-Ordner hinterlegten Text-Datei aus.
     * Der Text wird in einer textView ausgegeben.
     * Quelle: http://stackoverflow.com/questions/9544737/read-file-from-assets
     */
    private void readTxt(){
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new InputStreamReader(getAssets().open("Instruction.txt")));

            String mLine;
            while ((mLine = reader.readLine()) != null){
                textView.append(mLine);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(reader != null){
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * Beendet die aktuelle Activity.
     */
    private void goBack(){
        this.finish();
    }

}
