package com.applications.jfischer.anti_memory;

/**
 * Klasse, die alle Eigenschaften eines Highscore-Eintrags enthält.
 * Quelle: http://www.programmierenlernenhq.de/android-sqlite-projekt-in-android-studio-anlegen/
 */
public class HighscoreEntry {
    private long id;
    private String name;
    private int score;

    public HighscoreEntry(long idN, String nameN, int scoreN){
        id = idN;
        name = nameN;
        score = scoreN;
    }

    public long getId(){
        return id;
    }

    public void setId(int idN){
        id = idN;
    }

    public String getName(){
        return name;
    }

    public void setName(String nameN){
        name = nameN;
    }

    public int getScore(){
        return score;
    }

    /**
     * Überschreibt die toString() Methode, um sicher zu stellen, dass die Ausgabe eines
     * HighscoreEntry korrekt erfolgen kann.
     * Hinweis: Falls diese Methode nicht vorhanden ist, führt ein Aufruf der toString()
     * Methode zu Komplikationen, da nicht klar ist welche Attribute in eine String verwandelten
     * werden sollen.
     * @return Gibt eine String bestehend aus Name und Punktzahl zurück.
     */
    @Override
    public String toString(){
        String output = name + ": " + score;
        return output;
    }
}
