package com.applications.jfischer.anti_memory;

import android.os.CountDownTimer;

import java.util.Random;

/**
 * Fungiert innerhalb des MVC-Patterns als Model.
 */
public class Model {

    private Card[] cardArray;
    private int[] activeCIndArray; //= activeCardsIndexArray
    private int uncoveredCards;
    private int numOfCards;
    private int points;
    private Generator generator; //Zum generieren von neuen Karten
    private PlayActivity playActivity;
    private Random random;

    public Model(PlayActivity playActivityN) {
        points = 0;
        numOfCards = 9;
        cardArray = new Card[numOfCards];
        generator = new Generator();
        activeCIndArray = new int[3];
        uncoveredCards = 0;
        playActivity = playActivityN;
        random = new Random();
    }

    /**
     * Erstellt neun neue Karten und speichert diese im zugehörigen Array ab.
     */
    public void makeNewCards() {
        for(int i = 0; i < numOfCards; i++){
            cardArray[i] = null;
        }
        generator.removeAllBLCards(); //BL = Blacklist
        generator.fillAllCardsArray(); //Karten neu einsortieren und umdrehen
        for (int i = 0; i < numOfCards; i++) {
            Card card = generator.makeNewCards();
            randomPosition(card);
        }
        update();
    }

    /**
     * Plaziert eine Karte an einer zufälligen Positon im Array.
     * @param card Die zu positionierende Karte wird übergeben.
     */
    private void randomPosition(Card card){
        int randomPos = random.nextInt(9);
        if(cardArray[randomPos] == null) {
            cardArray[randomPos] = card;
        }else{
            randomPosition(card);
        }
    }

    /**
     * Verändert den Status einer Karte.
     * @param index Der Index der Karte wird übergeben.
     * @param status Der einzunhmende Status wird übergeben.
     */
    public void setCardStatus(int index, boolean status) {
            uncoveredCards++;
            cardArray[index].setUnCoveredStatus(status);
            update();
    }

    /**
     * Hinterlegt den Index einer nun aktiven (umgedrehten) Karte in einem Array.
     * @param index Der Index der zuvor aktiv gewordenen Karte wird übergeben.
     */
    public void setActiveCardsStatus(int index) {
       activeCIndArray[uncoveredCards - 1] = index; //Da zuvor die Anzahl der aufgedeckten Karten bereits erhöht wurde
    }

    /**
     * Überprüft die aktiven (umgedrehten) Karte und vergibt Plus- oder Minus-Punkte.
     */
    public void analyse(){
        uncoveredCards = 100; //verhindern dass weiter Karten reagieren
      if(compareCards() == true) {
          points = points + 50;
          for(int i = 0; i < 3; i++){
              cardArray[activeCIndArray[i]] = null;
          }
      }
        else{
          points = points - 10;
          for (int i = 0; i < 3; i++) {
              cardArray[activeCIndArray[i]].setUnCoveredStatus(false);
          }
      }
        countdown();
    }

    /**
     * Startet einen Countdown, der nach dem Beenden die update() Methode aufruft.
     */
    private void countdown(){
        CountDownTimer countDownTimer = new CountDownTimer(2000, 1) {
            @Override
            public void onTick(long millisUntilFinished) {
            }
            @Override
            public void onFinish() {
                update();
                uncoveredCards = 0;
            }
        }.start();
    }

    /**
     * Benachrichtigt die View, dass Veränderungen aufgetreten sind und die View upgedated werden muss.
     */
    private void update(){
        playActivity.updateCard();
    }

    /**
     * Vergleicht, ob die aktiven Karten sich komplett unterscheiden.
     * @return Gibt true zurück, wenn sie sich komplett unterscheiden.
     */
    private boolean compareCards(){
        if(cardArray[activeCIndArray[0]].isCompletelyDifferent(cardArray[activeCIndArray[1]])){
            if(cardArray[activeCIndArray[0]].isCompletelyDifferent(cardArray[activeCIndArray[2]])){
                if(cardArray[activeCIndArray[1]].isCompletelyDifferent(cardArray[activeCIndArray[2]])){
                    return true;
                }
            }
        }
        return false;
    }

    public int getUncoveredCards(){
        return uncoveredCards;
    }

    public Card getCard(int index){
        return cardArray[index];
    }

    public int getPoints(){
        return points;
    }
}
