package com.applications.jfischer.anti_memory;


import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Klasse zum Erzeugen von allen Karten und den passenden Karten-Triple.
 */
public class Generator {
    private Random random;
    private ArrayList blackList;
    private Card[] cardTripleArray; //Array für 3 Karten die als eine der vorgeschlagene Lösungen gelten.
    private int addedCards; //Anzahl der Karten die zum cardTripleArray hinzugefügt wurden.
    private Card[] allCardsArray;

    public Generator() {
        random = new Random();
        blackList = new ArrayList();
        cardTripleArray = new Card[3];
        allCardsArray = new Card[36];
        fillAllCardsArray();
    }

    /**
     * Überprüft ob die übergebene Karte bereits im Spiel ist und somit in der Blacklist enthalten ist.
     * @param card Die zu überprüfende Karte wird übergeben.
     * @return Gibt true zurück, wenn die Karte in der Blacklist enthalten ist.
     */
    private boolean IsInBlacklist(Card card){
        Card compareCard;
        for (int i = 0; i < blackList.size(); i++) {
            if (i < blackList.size()) {
                compareCard = (Card) blackList.get(i);
                if (compareCard.isEqual(card)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Erstellt eine neue Karte, abhängig von den bereits vorhandenen Karten und den anderen Karten
     * des zugehörigen Karten-Triple.
     * @return Gibt die erstellte Karte zurück.
     */
    public Card makeNewCards(){
        if(addedCards == 0) {
            Card card = randomCard();
            if (IsInBlacklist(card) == false) {
                cardTripleArray[0] = card;
                addedCards++;
                blackList.add(card);
                return card;
            }
            return makeNewCards();
        }

        if(addedCards == 1){
            for(int i = 0; i < 36; i++){
                if (allCardsArray[i].isCompletelyDifferent(cardTripleArray[0])
                        && IsInBlacklist(allCardsArray[i]) == false){
                    cardTripleArray[1] = allCardsArray[i];
                    addedCards++;
                    blackList.add(allCardsArray[i]);
                    return allCardsArray[i];
                }
            }
        }

        if(addedCards == 2){
            for(int i = 0; i < 36; i++){
                if(allCardsArray[i].isCompletelyDifferent(cardTripleArray[0])
                        && allCardsArray[i].isCompletelyDifferent(cardTripleArray[1])
                            && IsInBlacklist(allCardsArray[i]) == false){
                        addedCards = 0;
                        blackList.add(allCardsArray[i]);
                        return allCardsArray[i];
                }
            }
        }
        return randomCard();
    }

    /**
     * Erzeugt eine zufällige Karte.
     * @return Gibt die erzeugte Karte zurück.
     */
    private Card randomCard(){
        int i = random.nextInt(36);
        return allCardsArray[i];
    }

    /**
     * Füllt das allCardsArray mit allen möglichen Karten auf.
     */
    public void fillAllCardsArray(){
        String shapeS = "";
        String colorS = "";
        int position = 0;
        for(int shape = 1; shape < 4; shape++){
            for(int color = 1; color < 5; color++){
                for(int count = 1; count < 4; count++){
                    switch(shape){
                        case 1: shapeS = "circle"; break;
                        case 2: shapeS = "square"; break;
                        case 3: shapeS = "triangle"; break;
                    }

                    switch(color){
                        case 1: colorS = "blue"; break;
                        case 2: colorS = "green"; break;
                        case 3: colorS = "red"; break;
                        case 4: colorS = "yellow"; break;
                    }
                    allCardsArray[position] = new Card(colorS, count, shapeS, false);
                    position++;
                }
            }
        }
    }

    /**
     * Löscht alle Karten aus der Blacklist.
     */
    public void removeAllBLCards() {
        blackList.clear();
    }
}


