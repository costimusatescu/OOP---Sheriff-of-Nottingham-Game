package com.test2;

import main.GameInput;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;


public class Game {
    private Comerciant[] comercianti;
    private List<Integer> ids;

    public Game(final GameInput input) {
        ids = input.getAssetIds();
        List<String> names = input.getPlayerNames();
        comercianti = new Comerciant[names.size()];
        for (int i = 0; i < names.size(); i++) {
            if (names.get(i).equals("basic")) {
                comercianti[i] = new Basic(names.get(i));
            } else {
                if (names.get(i).equals("greedy")) {
                    comercianti[i] = new Greedy(names.get(i));
                } else {
                    if (names.get(i).equals("bribed")) {
                        comercianti[i] = new Bribed(names.get(i));
                    }
                }
            }
        }
    }

    public void play() {

        // Creare Map<id carte, nume carte>
        HashMap<Integer, String> countBunuri = new HashMap<Integer, String>();
        countBunuri.put(0, "Apple");
        countBunuri.put(1, "Cheese");
        countBunuri.put(2, "Bread");
        countBunuri.put(3, "Chicken");
        countBunuri.put(10, "Silk");
        countBunuri.put(11, "Pepper");
        countBunuri.put(12, "Barrel");

        // Creare Map<id carte, kingBonus>
        HashMap<Integer, Integer> kingBonus = new HashMap<>();
        kingBonus.put(0, 20);
        kingBonus.put(1, 15);
        kingBonus.put(2, 15);
        kingBonus.put(3, 10);

        // Creare Map<id carte, queenBonus>
        HashMap<Integer, Integer> queenBonus = new HashMap<>();
        queenBonus.put(0, 10);
        queenBonus.put(1, 10);
        queenBonus.put(2, 10);
        queenBonus.put(3, 5);

        int j = 0;

        // Creare asstesInHand pentru prima runda
       for (int i = 0; i < comercianti.length; i++) {
           for (int k = 0; k < 6; k++) {
               comercianti[i].assetsInHand.add(countBunuri.get(ids.get(j)));
               j++;
           }
       }

       // Desfasurarea rundelor
        for (int i = 0; i < 2 * comercianti.length; i++) {
            for (int k = 0; k < comercianti.length; k++) {
                if (k != i % comercianti.length) {
                    comercianti[i % comercianti.length].controleaza(comercianti[k]);
                    for (int z = 0; z < comercianti[k].numarVerificare; ++z) {
                        comercianti[k].assetsInHand.add(countBunuri.get(ids.get((j))));
                        j++;
                    }
                }
            }
        }

        // Calcularea profitului dupa
        for (int i = 0; i < comercianti.length; i++) {
            comercianti[i].punctajfinal += comercianti[i].coins;    // Adaugare punctaj pe parcurs
            List<String> assetsName = comercianti[i].assetsOnMerchantStand;     // Creare lista pentru bunuri aduse
            List<String> bonusAssets = new LinkedList<>();  // Creare lista pentru bonusuri aduse de carti ilegale

            // Adaugare in lista a bonusurilor
            for (int k = 0; k < assetsName.size(); k++) {
                String compar = assetsName.get(k);
                if ((compar.equals("Silk")) || (compar == "Pepper") || (compar == "Barrel")) {
                    Carte bonusCarte = new Carte(compar);
                    for (j = 0; j < bonusCarte.bonus; j++) {
                        bonusAssets.add(bonusCarte.bonusNume);
                    }
                }
            }

            assetsName.addAll(bonusAssets); // Adaugare in lista bunurilor aduse a bonusurilor

            // Adaugarea profitului fiecarei carti
            for (int k = 0; k < assetsName.size(); k++) {
                Carte carte = new Carte(assetsName.get(k));
                comercianti[i].punctajfinal += carte.profit;
            }

        }

        // Calcul profit final
        for (int i = 0; i < 4; i++) { //pentru fiecare bun
            int maxk = 0;
            int maxq = 0;
            List<Integer> players = new LinkedList<>();

            // Calcul bunuri de un anumit tip pentru fiecare jucator
            for (int k = 0; k < comercianti.length; k++) {
                int nrBunuri = 0;
                String bun = countBunuri.get(i); // ia id
                List<String> bunuri = comercianti[k].assetsOnMerchantStand;
                for (int z = 0; z < bunuri.size(); z++) {
                    if (bunuri.get(z).equals(bun)) {
                        nrBunuri++;
                    }
                }
                players.add(nrBunuri);
            }

            for (int nr : players) {
                if (nr > maxk) {
                    int t = maxk;
                    maxk = nr;
                    if (t > maxq) {
                        maxq = t;
                    }
                }
                else if (nr > maxq) {
                    maxq = nr;
                }
            }

            // Adaugare la profit king si queen bonus
            for (int it = 0; it < players.size(); it++) {
                if (players.get(it) == maxk) {
                    comercianti[it].punctajfinal += kingBonus.get(i);
                }
                else if (players.get(it) == maxq) {
                    comercianti[it].punctajfinal += queenBonus.get(i);
                }
            }
        }


        // Ordonare descrescatoare dupa scor final
        for (int i = 0; i < comercianti.length; i++) {
            for (int k = i; k < comercianti.length; k++) {
                if (comercianti[k].punctajfinal > comercianti[i].punctajfinal) {
                    Comerciant aux;
                    aux = comercianti[i];
                    comercianti[i] = comercianti[k];
                    comercianti[k] = aux;
                }
            }
        }

        // Afisare nume comercianti si punctaj final
        for (int i = 0;i < comercianti.length; i++) {
            System.out.println(comercianti[i].name + ": " + comercianti[i].punctajfinal);
        }
    }
}
