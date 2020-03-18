package com.applications.jfischer.anti_memory;

import android.view.View;

/**
 * Klasse erbt von der Klasse OnClickListener, um beim setzen eines OnClickListener die Speicherung
 * des Attributs index zu ermöglichen.
 */
public class OnImageviewClickListener implements View.OnClickListener {

    int index;

    public OnImageviewClickListener(int indexN){
        index = indexN;
    }

    @Override
    public void onClick(View v){
    }

}
