
package com.applications.jfischer.anti_memory;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;

/**
 * Klasse die alle Eigenschaften einer Karte enthält.
 * Bei dem boolean unCovered gilt: Wenn unCovered == true, dann ist die Karte aufgedeckt.
 */
public class Card extends AppCompatActivity {
    private String color;
    private int count;
    private String symbol;
    private boolean unCovered;

public Card(String colorN, int countN, String symbolN, boolean unCoveredN){
        color = colorN;
        count = countN;
        symbol = symbolN;
        unCovered = unCoveredN;
    }

    /**
     * Vergleicht eine Karte im Hinblick auf die Eigenschaften Farbe, Symbol und Anzahl.
     * @param card Die zu vergleichende Karte wird übergeben.
     * @return Gibt true zurück, wenn alle Eigenschaften übereinstimmen.
     */
   public boolean isEqual(Card card){
        if(card.getColor() == color && card.getCount() == count && card.getSymbol() == symbol){
            return true;
        }
        return false;
    }

    /**
     * Bestimmt, ob sich die Karte in den Eigenschaften Farbe, Symbol und Anzahl komplett unterscheidet.
     * @param card Die zu vergleichende Karte wird übergeben.
     * @return Gibt true zurück, wenn sich alle Eigenschaften unterscheiden.
     */
    public boolean isCompletelyDifferent(Card card){
        if(card.getCount() != count && card.getSymbol() != symbol && card.getColor() != color){
            return true;
        }
        return  false;
    }

    /**
     * Gibt die, der Karte entsprechende Image Resource zurück um sie in der View ausgeben zu können.
     * Wird beim durch die Update-Methode der View aufgerufen, wenn Veränderungen aufgetreten sind.
     * @param context Muss übergeben werden um Informationen über die Image Resource der Karte zu erhalten.
     * @return Gibt eine Image Resource in Form eines int zurück.
     */
    public int cardToPicture(Context context) {
        if (unCovered == true) {
                String uri = "drawable/" + symbol + color + count;
                int imageResource = context.getResources().getIdentifier(uri, null, context.getPackageName());
                return imageResource;
        } else {
            return R.drawable.backgroundcard;
        }
    }


    public String getColor(){
        return color;
    }

    public String getSymbol(){
        return  symbol;
    }

    public int getCount(){
        return count;
    }

    public boolean getUncoveredStatus(){
        return unCovered;
    }

    public void setUnCoveredStatus(boolean unCoveredN){
        unCovered = unCoveredN;
    }

}
