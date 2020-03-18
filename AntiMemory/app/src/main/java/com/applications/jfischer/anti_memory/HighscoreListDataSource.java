package com.applications.jfischer.anti_memory;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Ist ein Teil der Datenbank-Struktur. Übernimmt alle Datenbankzugriffe, d.h. sowohl lesend
 * als auch schreibend.
 * Quelle: http://www.programmierenlernenhq.de/android-sqlite-projekt-in-android-studio-anlegen/
 */
public class HighscoreListDataSource {
    private SQLiteDatabase database;
    private HighscoreListDbHelper dbHelper;
    private String[] columns = {
            HighscoreListDbHelper.COLUMN_ID,
            HighscoreListDbHelper.COLUMN_NAME,
            HighscoreListDbHelper.COLUMN_SCORE
    };

    public HighscoreListDataSource(Context context){
        dbHelper = new HighscoreListDbHelper(context);
    }

    /**
     * Öffnet eine beschreibbare Datenbank mit Hilfe des DB-Helpers.
     */
    public void open(){
        database = dbHelper.getWritableDatabase();
    }

    /**
     * Schließt den DB-Helper.
     */
    public void close(){
        dbHelper.close();
    }

    /**
     * Erstellt einen neuen Highscore-Eintrag,
     * @param name Muss übergeben werden um einen vollständigen Highscore Eintrag erstellen zu können.
     * @param score Muss übergeben werden um einen vollständigen Highscore Eintrag erstellen zu können.
     * @return Gibt den erstellten Highscore Eintrag zurück.
     */
    public HighscoreEntry createHighscoreEntry(String name, int score){
        ContentValues values = new ContentValues();
        values.put(HighscoreListDbHelper.COLUMN_NAME, name);
        values.put(HighscoreListDbHelper.COLUMN_SCORE, score);

        long insertId = database.insert(HighscoreListDbHelper.TABLE_HIGHSCORE_LIST, null, values);

        Cursor cursor = database.query(HighscoreListDbHelper.TABLE_HIGHSCORE_LIST, columns,
                HighscoreListDbHelper.COLUMN_ID + "=" + insertId, null, null, null, null);

        cursor.moveToFirst();
        HighscoreEntry highscoreEntry = cursorToHighscoreEntry(cursor);
        cursor.close();

        return highscoreEntry;
    }

    /**
     * Gibt den Highscore Eintrag zurück, auf den der Cursor zeigt.
     * @param cursor Wird übergeben um den Highscore Eintrag auf den der Cursor zeigt auszulesen.
     * @return Gibt den ausgelesenen Highscore Eintrag zurück.
     */
    public  HighscoreEntry cursorToHighscoreEntry(Cursor cursor){
        int idIndex = cursor.getColumnIndex(HighscoreListDbHelper.COLUMN_ID);
        int idName = cursor.getColumnIndex(HighscoreListDbHelper.COLUMN_NAME);
        int idScore = cursor.getColumnIndex(HighscoreListDbHelper.COLUMN_SCORE);

        String name = cursor.getString(idName);
        int score = cursor.getInt(idScore);
        long id = cursor.getLong(idIndex);

        HighscoreEntry highscoreEntry = new HighscoreEntry(id, name, score);

        return highscoreEntry;
    }

    /**
     * Ließt alle Einträge der Datenbank aus und speichert diese in einer Liste, indem der Cursor
     * auf jeden Eintrag einmal zeigt und dieser so ausgelesen werden kann.
     * @return Gibt die Liste mit allen Einträgen zurück.
     */
    public List<HighscoreEntry> getAllHighscoreEntries(){
        List<HighscoreEntry> highScoreList = new ArrayList<>();

        Cursor cursor = database.query(HighscoreListDbHelper.TABLE_HIGHSCORE_LIST, columns, null,
                null, null, null, null);

        cursor.moveToFirst();
        HighscoreEntry highscoreEntry;

        while(cursor.isAfterLast() == false){
            highscoreEntry = cursorToHighscoreEntry(cursor);
            highScoreList.add(highscoreEntry);
            cursor.moveToNext();
        }

        cursor.close();

        return highScoreList;
    }

    /**
     * Löscht alle Einträge der Datenbank.
     */
    public void delete(){
        open();
        database.delete("highscore_list", null, null);
    }
}
