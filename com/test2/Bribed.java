package com.test2;

class Bribed extends Comerciant {

    // Constructor pentru clasa derivata Bribed
    Bribed(String name) {
        super(name);
        this.name = "BRIBED";
        mita = 0;
    }

    // Metoda de creare a sacului
    public void getCard() {
        int max = 0;
        int profitMax = 0;
        int countI = 0;
        String maxString = " ";

        // Se adauga in sac toate bunurile ilegale
        if (coins > 0) {
            for (int i = 0; i < assetsInHand.size(); i++) {
                Carte c = new Carte(assetsInHand.get(i));
                if (c.legal == false) {
                    carteVerificare[countI] = c.nume;
                    countI++;
                    assetsInHand.remove(i);
                    i--;
                }
            }
            numarVerificare = countI;
            carteDeclarata = "Apple";
            numarDeclarat = 1;
            // Se seteaza mita in functie de bunuri ilegale
            if ((countI > 0) && (countI < 3)){
                mita = 5;
            }
            if (countI > 2) {
                mita = 10;
            }
        }

        // Se face sacul stiind ca nu avem bunuri ilegale
        if ((coins <= 0) || (countI == 0)) {
            for (int i = 0; i < assetsInHand.size(); i++) {
                int count = 0;
                for (int j = 0; j < assetsInHand.size(); j++) {
                    if (assetsInHand.get(i) == assetsInHand.get(j))
                        count++;
                }
                if ((count >= max)) {

                    Carte c = new Carte(assetsInHand.get(i));
                    if ((c.profit > profitMax) && (c.legal == true)) {
                        profitMax = c.profit;
                        max = count;
                        maxString = assetsInHand.get(i);
                        carteDeclarata = maxString;
                        numarDeclarat = max;
                        for (int j = 0; j < max; j++) {
                            carteVerificare[j] = maxString;
                        }
                        //carteVerificare[0] = maxString;
                        //carteVerificare[1] = maxString;
                        numarVerificare = max;

                    }
                }

            }




            for (int i = 0; i < assetsInHand.size(); i++) {
                if ((assetsInHand.get(i) == maxString) || (assetsInHand.get(i) == carteVerificare[1])) {
                    assetsInHand.remove(i);
                    i--;
                }
            }

        }
    }


    public void controleaza(Comerciant comerciant) {
        comerciant.getCard();
        Carte carteDeclarata = new Carte(comerciant.carteDeclarata);
        boolean ok = true;


        for (int i = 0; i < comerciant.numarVerificare; i++) {
            Carte carteVerificare1 = new Carte(comerciant.carteVerificare[i]);
            if ((carteVerificare1.nume == carteDeclarata.nume)) {
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
