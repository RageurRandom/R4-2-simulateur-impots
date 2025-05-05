package com.kerware.simulateur.services;

public class TrancheImpotService {
    private static final int[] LIMITES = {0, 11294, 28797, 82341, 177106, Integer.MAX_VALUE};
    private static final double[] TAUX = {0.0, 0.11, 0.3, 0.41, 0.45};

    /**
     * Calcule la tranche d'imposition en fonction du revenu
     * @param revenu
     * @return impots correspondant à la tranche d'imposition
     */
    public double calculer(double revenu) {
        double impot = 0;
        for (int i = 0; i < TAUX.length; i++) {

            if (revenu <= LIMITES[i]) {
                break;
            }

            impot += (Math.min(revenu, LIMITES[i+1]) - LIMITES[i]) * TAUX[i];
        }
        return impot;
    }
}