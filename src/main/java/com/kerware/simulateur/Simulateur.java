package com.kerware.simulateur;

public class Simulateur {
    public static void main(String[] args) {
        ICalculateurImpot calculateur = new CalculateurImpot();

        // Configuration du calcul
        calculateur.setRevenusNet(65000);
        calculateur.setSituationFamiliale(SituationFamiliale.MARIE);
        calculateur.setNbEnfantsACharge(3);
        calculateur.setNbEnfantsSituationHandicap(1);
        calculateur.setParentIsole(false);

        // Exécution du calcul
        calculateur.calculImpotSurRevenuNet();

        // Affichage des résultats
        System.out.println("Impôt sur le revenu: " + calculateur.getImpotSurRevenuNet());
        System.out.println("Revenu fiscal de référence: " + calculateur.getRevenuFiscalReference());
        System.out.println("Nombre de parts: " + (calculateur.getNbPartsFoyerFiscal() / 100.0));
    }
}