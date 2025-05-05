package com.kerware.simulateur.services;

public class PlafonnementService {
    private static final double PLAFOND_DEMI_PART = 1759;

    /**
     * Applique le plafond
     * @param impotDeclarants
     * @param impotFoyer
     * @param nbParts
     * @param nbPartsDeclarants
     * @return double impots nets plafonnés
     */
    public double appliquer(double impotDeclarants, double impotFoyer,
                            double nbParts, double nbPartsDeclarants) {

        double ecartParts = nbParts - nbPartsDeclarants;
        double plafond = ecartParts * PLAFOND_DEMI_PART * 2;

        if (impotDeclarants - impotFoyer > plafond) {
            return impotDeclarants - plafond;
        }

        return impotFoyer;
    }
}