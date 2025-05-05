package com.kerware.simulateur;

/**
 * Classe principale pour tester le calculateur d'impôt avec les mêmes cas
 * que l'ancien simulateur.
 */
public class Simulateur {
    public static void main(String[] args) {
        testerCasSimulateur();
    }

    private static void testerCasSimulateur() {
        // Cas 1: Marié, 3 enfants, revenu 65000€
        testerCas(
                65000,
                SituationFamiliale.MARIE,
                3,
                0,
                false,
                "Cas 1: Marié, 3 enfants, revenu 65000€"
        );

        // Cas 2: Marié, 3 enfants dont 1 handicapé, revenu 65000€
        testerCas(
                65000,
                SituationFamiliale.MARIE,
                3,
                1,
                false,
                "Cas 2: Marié, 3 enfants dont 1 handicapé, revenu 65000€"
        );

        // Cas 3: Divorcé, 1 enfant, parent isolé, revenu 35000€
        testerCas(
                35000,
                SituationFamiliale.DIVORCE,
                1,
                0,
                true,
                "Cas 3: Divorcé, 1 enfant, parent isolé, revenu 35000€"
        );

        // Cas 4: Divorcé, 2 enfants, parent isolé, revenu 35000€
        testerCas(
                35000,
                SituationFamiliale.DIVORCE,
                2,
                0,
                true,
                "Cas 4: Divorcé, 2 enfants, parent isolé, revenu 35000€"
        );

        // Cas 5: Divorcé, 3 enfants, parent isolé, revenu 50000€
        testerCas(
                50000,
                SituationFamiliale.DIVORCE,
                3,
                0,
                true,
                "Cas 5: Divorcé, 3 enfants, parent isolé, revenu 50000€"
        );

        // Cas 6: Divorcé, 3 enfants dont 1 handicapé, parent isolé, revenu 50000€
        testerCas(
                50000,
                SituationFamiliale.DIVORCE,
                3,
                1,
                true,
                "Cas 6: Divorcé, 3 enfants dont 1 handicapé, parent isolé, revenu 50000€"
        );

        // Cas 7: Célibataire, pas d'enfant, revenu 200000€
        testerCas(
                200000,
                SituationFamiliale.CELIBATAIRE,
                0,
                0,
                false,
                "Cas 7: Célibataire, pas d'enfant, revenu 200000€"
        );
    }

    private static void testerCas(int revenusNet,
                                  SituationFamiliale situation,
                                  int nbEnfants,
                                  int nbEnfantsHandicapes,
                                  boolean parentIsole,
                                  String description) {
        System.out.println("\n" + description);

        ICalculateurImpot calculateur = new CalculateurImpot();
        calculateur.setRevenusNet(revenusNet);
        calculateur.setSituationFamiliale(situation);
        calculateur.setNbEnfantsACharge(nbEnfants);
        calculateur.setNbEnfantsSituationHandicap(nbEnfantsHandicapes);
        calculateur.setParentIsole(parentIsole);

        calculateur.calculImpotSurRevenuNet();

        // Affichage des résultats
        System.out.println("Impôt sur le revenu net: " + calculateur.getImpotSurRevenuNet() + "€");
    }
}