package com.test2;

final class Carte {
    String nume;
    boolean legal;
    int profit;
    int penalty;
    int bonus;
    String bonusNume;

    // Constructor care seteaza campurile in functie de paramentru
    Carte(String nume) {
        this.nume = nume;
        if (nume == "Apple") {
            legal = true;
            profit  = 2;
            penalty  = 2;
            bonus = 0;
            bonusNume = " ";
        }
        if (nume == "Cheese") {
            legal = true;
            profit  = 3;
            penalty  = 2;
            bonus = 0;
            bonusNume = " ";
        }
        if (nume == "Bread") {
            legal = true;
            profit  = 4;
            penalty  = 2;
            bonus = 0;
            bonusNume = " ";
        }
        if (nume == "Chicken") {
            legal = true;
            profit  = 4;
            penalty  = 2;
            bonus = 0;
            bonusNume = " ";
        }
        if (nume == "Silk") {
            legal = false;
            profit  = 9;
            penalty  = 4;
            bonus = 3;
            bonusNume = "Cheese";
        }
        if (nume == "Pepper") {
            legal = false;
            profit  = 8;
            penalty  = 4;
            bonus = 2;
            bonusNume = "Chicken";
        }
        if (nume == "Barrel") {
            legal = false;
            profit  = 7;
            penalty  = 4;
            bonus = 2;
            bonusNume = "Bread";
        }
    }
}