package com.applications.jfischer.anti_memory;

/**
 * Fungiert innerhalb der MVC-Patterns als Kontroller.
 */
public class Control {

    private Model model;

    /**
     * Konstruktur der Klassse Control.
     * @param playActivityN Die PlayActivity, die als View dient, wird übergeben,
     *                      um mit dem Model verknüpft werden zu können.
     */
    public Control(PlayActivity playActivityN){
        model = new Model(playActivityN);
    }

    /**
     * Verändert den Status der Karte. Der Status der Karte wird auf "aufgedeckt" gesetzt,
     * sofern die Karte vorher umgedreht war und nicht mehr als drei Karten umgedreht sind.
     * Ansonsten passiert nichts.
     * @param index Der Index der Karte wird übergeben, um zu wissen um welche Karte es sich handelt.
     */
    public void changeCardStatus(int index) {
        if (model.getCard(index).getUncoveredStatus() == false && model.getUncoveredCards() < 3) {
            model.setCardStatus(index, true);
        }
    }

    /**
     * Übergibt dem Model den Index der Karten, die gerade aktiv (umgedreht) sind.
     * Falls drei Karten aufgedeckt sind, wird die Methode zum Vergleichen der Karten im Model aufgerufen.
     * @param index Der Index der Karte wird übergeben, um zu wissen um welche Karte es sich handelt.
     */
    public void changeActiveCardsStatus(int index){
        if (model.getUncoveredCards() <= 3) {
            model.setActiveCardsStatus(index);
        }

        if(model.getUncoveredCards() == 3){
            model.analyse();
        }
    }

    public int getPoints(){
        return model.getPoints();
    }

    public void makeNewCards(){
        model.makeNewCards();
    }

    public Card getCard(int index){
        return model.getCard(index);
    }


}
