package com.kerware.simulateur.services;

public class AbattementService {
    private static final int MIN = 495, MAX = 14171;
    private static final double TAUX = 0.1;

    /**
     * Calcule l'abattement
     * @param revenusNet int
     * @return abattement : int Abattement correspondant aux revenus nets entrés en paramètre
     */
    public int calculer(int revenusNet) {
        double abattement = revenusNet * TAUX;
        return (int) Math.round(Math.min(Math.max(abattement, MIN), MAX));
    }
}