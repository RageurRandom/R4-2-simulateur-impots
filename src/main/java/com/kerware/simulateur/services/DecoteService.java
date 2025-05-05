package com.kerware.simulateur.services;

public class DecoteService {
    private static final double SEUIL_CELIBATAIRE = 1929;
    private static final double SEUIL_COUPLE = 3191;
    private static final double MAX_CELIBATAIRE = 873;
    private static final double MAX_COUPLE = 1444;
    private static final double TAUX = 0.4525;

    public int calculer(int nbPartsDeclarants, double impotAvantDecote) {
        if (nbPartsDeclarants == 1 && impotAvantDecote < SEUIL_CELIBATAIRE) {
            return (int) Math.round(MAX_CELIBATAIRE - (impotAvantDecote * TAUX));
        } else if (nbPartsDeclarants == 2 && impotAvantDecote < SEUIL_COUPLE) {
            return (int) Math.round(MAX_COUPLE - (impotAvantDecote * TAUX));
        }
        return 0;
    }
}