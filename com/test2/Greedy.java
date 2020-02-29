package com.test2;

import java.util.LinkedList;
import java.util.List;

class Greedy extends Comerciant {

    // Initilizare primei runde
    int runda = 1;

    // Constructor pentru clasa derivata Greedy
    Greedy(String name) {
        super(name);
        this.name = "GREEDY";
        mita = 0;
    }

    // Metoda de creare a sacului este la fel ca basic in rundele impare
    public void getCard() {
        List<Integer> freq = new LinkedList<>();
        String asset = "";
        int maxFreq = 0;

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

        if (maxFreq != 0) {
            int maxProfit = 0;
            for (int i= 0; i < assetsInHand.size(); i++) {
                Carte carte = new Carte(assetsInHand.get(i));
                if (freq.get(i) == maxFreq && carte.legal == true && carte.profit> maxProfit) {
                    asset = assetsInHand.get(i);
                    maxProfit = carte.profit;
                }
            }

            if (maxFreq == 6) {
                maxFreq--;
            }

            for (int i = 0; i < maxFreq; i++) {
                carteVerificare[i] = asset;
            }

            for (int i = 0; i < maxFreq; i++) {
                assetsInHand.remove(asset);
            }

            carteDeclarata = asset;
            numarVerificare = numarDeclarat = maxFreq;
        }
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

        // Se adauga cartea ilegala de profit maxim
        if ((runda % 2 == 0) && (numarVerificare != 5)) {
            int profitMax = 0;
            for (int i = 0; i < assetsInHand.size(); i++) {
                Carte c = new Carte(assetsInHand.get(i));
                if ((c.profit > profitMax) &&(c.legal == false)) {
                    profitMax = c.profit;
                    asset = assetsInHand.get(i);
                }
            }

            carteVerificare[numarDeclarat] = asset;
            numarDeclarat++;
            numarVerificare++;
            assetsInHand.remove(asset);
        }

        // Incrementarea rundei
        this.runda++;
    }

    // Metoda controleaza
    public  void controleaza(Comerciant comerciant) {
        boolean ok = true;
        comerciant.getCard();
        Carte carteDeclarata = new Carte(comerciant.carteDeclarata);

        // Accepta mita daca este diferita de 0
        if (comerciant.mita != 0) {
            coins += comerciant.mita;
            comerciant.coins -= comerciant.mita;
            comerciant.mita = 0;
            for (int i = 0; i < comerciant.numarVerificare; i++) {
                comerciant.assetsOnMerchantStand.add(comerciant.carteVerificare[i]);
            }
        }

        // Este la fel ca basic
        else {
            for (int i = 0; i < comerciant.numarVerificare; i++) {
                Carte carteVerificare1 = new Carte(comerciant.carteVerificare[i]);



                if ((carteVerificare1.nume == carteDeclarata.nume) && (comerciant.numarVerificare == comerciant.numarDeclarat)) {
                    comerciant.assetsOnMerchantStand.add(comerciant.carteVerificare[i]);
                } else {
                    comerciant.coins -= carteVerificare1.penalty;
                    coins += carteVerificare1.penalty;
                    ok = false;
                }
            }
            if (ok == true) {
                comerciant.coins += comerciant.numarVerificare * carteDeclarata.penalty;
                coins -= comerciant.numarVerificare * carteDeclarata.penalty;
            }
        }

    }

}