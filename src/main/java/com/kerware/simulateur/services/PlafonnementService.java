package com.kerware.simulateur.services;

public class PlafonnementService {
    private static final double PLAFOND_DEMI_PART = 1759;

    public double appliquer(double impotDeclarants, double impotFoyer, double nbParts, double nbPartsDeclarants) {
        double ecartParts = nbParts - nbPartsDeclarants;
        double plafond = ecartParts * PLAFOND_DEMI_PART * 2;
        return (impotDeclarants - impotFoyer > plafond) ? impotDeclarants - plafond : impotFoyer;
    }
}