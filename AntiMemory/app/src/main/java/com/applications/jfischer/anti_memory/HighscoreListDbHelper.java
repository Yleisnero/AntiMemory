package com.applications.jfischer.anti_memory;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Ist ein Teil der Datenbank-Struktur. Die Klasse hilft beim Erstellen und Aktualisieren der
 * Datenbank. Beinhaltet die Tabelle in der die Einträge abgespeichert werden.
 *Quelle: http://www.programmierenlernenhq.de/android-sqlite-projekt-in-android-studio-anlegen/
 */
public class HighscoreListDbHelper extends SQLiteOpenHelper {
    public static final String DB_NAME = "highscore_list.db";
    public static final int DB_VERSION = 1;

    public static final String TABLE_HIGHSCORE_LIST = "highscore_list";

    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_SCORE = "score";

    public static final String SQL_CREATE = "CREATE TABLE " + TABLE_HIGHSCORE_LIST +
            "(" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                  COLUMN_NAME + " TEXT NOT NULL, " +
                  COLUMN_SCORE + " INTEGER NOT NULL);";

    public HighscoreListDbHelper(Context context){
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
            db.execSQL(SQL_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){

    }
}
