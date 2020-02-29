package com.test2;

import java.util.LinkedList;

abstract class  Comerciant {
    int coins;
    int standNumber;
    int handNumber;
    String name;
    LinkedList<String> assetsInHand = new LinkedList<String>();
    LinkedList<String> assetsOnMerchantStand = new LinkedList<String>();
    String carteDeclarata;
    int numarDeclarat;
    String[] carteVerificare = new String[10];
    int numarVerificare;
    int mita;
    public int punctajfinal;
    int runda;

    // Constructor care seteaza variabilele
    Comerciant(String name) {
        this.coins = 50;
        this.standNumber = 0;
        this.name = name;
        this.handNumber = 0;
        this.punctajfinal = 0;
    }

    // Metoda abstracta de creare a sacului
    public abstract void getCard();

    // Metoda abstacta pentru controlarea sacului comerciantului
    public abstract void controleaza(Comerciant comerciant);
}