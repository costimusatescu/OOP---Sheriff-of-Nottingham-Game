package com.test2;

import java.util.LinkedList;
import java.util.List;

class Basic extends Comerciant {

    // Constructor pentru clasa derivata Basic
    Basic(String name) {
        super(name);    // Apeleaaza constructorul din super-clasa
        this.name = "BASIC";
        mita = 0;   // Seteaza mita
    }

    // Metoda de creare a sacului
    public void getCard() {
        List<Integer> freq = new LinkedList<>();
        String asset = "";
        int maxFreq = 0;

        // Calcularea frecventei maxime pentru bunurile legale
        for (int i = 0; i < assetsInHand.size(); i++) {
            Carte carte = new Carte(assetsInHand.get(i));
            int count = 0;
            for (int j = 0; j < assetsInHand.size(); j++) {
                if (assetsInHand.get(i) == assetsInHand.get(j))
                    count++;
            }
            freq.add(count);
            if (carte.legal == true && count >  maxFreq) {
                maxFreq = count;
            }
        }

        // Daca se gaseste cel putin o carte legala
        if (maxFreq != 0) {
            int maxProfit = 0;

            // Aflarea cartii cu frecventa maxima care are profitul cel mai mare
            for (int i= 0; i < assetsInHand.size(); i++) {
                Carte carte = new Carte(assetsInHand.get(i));
                if (freq.get(i) == maxFreq && carte.legal == true && carte.profit> maxProfit) {
                    asset = assetsInHand.get(i);
                    maxProfit = carte.profit;
                }
            }

            // Maxim 5 produse in sac
            if (maxFreq == 6) {
                maxFreq--;
            }

            // Setarea cartilor ce vor fi verificate
            for (int i = 0; i < maxFreq; i++) {
                carteVerificare[i] = asset;
            }

            // Se elimina din mana cartile
            for (int i = 0; i < maxFreq; i++) {
                assetsInHand.remove(asset);
            }

            // Setarea cartii declarate, a numerelor de verificare si declarat
            carteDeclarata = asset;
            numarVerificare = numarDeclarat = maxFreq;
        }
        // Daca nu se gaseste nicio carte legala
        else {
            int profitMax = 0;
            for (int i = 0; i < assetsInHand.size(); i++) {
                Carte c = new Carte(assetsInHand.get(i));
                if (c.profit > profitMax) {
                    profitMax = c.profit;
                    asset = assetsInHand.get(i);
                }
            }

            carteDeclarata = "Apple";
            numarDeclarat = 1;
            carteVerificare[0] = asset;
            numarVerificare = 1;

            assetsInHand.remove(asset);
        }
    }

    // Metoda de controlare a unui comerciant
    public void controleaza(Comerciant comerciant) {
        boolean ok = true;
        comerciant.getCard();
        Carte carteDeclarata = new Carte(comerciant.carteDeclarata);

        // Cartile din sac sunt verificate cu cele declarate
        for (int i = 0; i < comerciant.numarVerificare; i++) {
            Carte carteVerificare1 = new Carte(comerciant.carteVerificare[i]);

            if ((carteVerificare1.nume == carteDeclarata.nume) && (comerciant.numarVerificare == comerciant.numarDeclarat)) {
                comerciant.assetsOnMerchantStand.add(comerciant.carteVerificare[i]);
            }
            // Tranzitia banilor pentru bunurile ilegale
            else {
                comerciant.coins -= carteVerificare1.penalty;
                coins += carteVerificare1.penalty;
                ok = false;
            }

        }

        // Comerciantul primeste bani daca tot ce a declarat este adevarat
        if (ok == true) {
            comerciant.coins += comerciant.numarVerificare * carteDeclarata.penalty;
            coins -= comerciant.numarVerificare * carteDeclarata.penalty;
        }
    }

}