package com.kerware.oldsimulateur;

import com.kerware.simulateur.model.SituationFamiliale;

/**
 *  Cette classe permet de simuler le calcul de l'impôt sur le revenu
 *  en France pour l'année 2024 sur les revenus de l'année 2023 pour
 *  des cas simples de contribuables célibataires, mariés, divorcés, veufs
 *  ou pacsés avec ou sans enfants à charge ou enfants en situation de handicap
 *  et parent isolé.
 *
 *  EXEMPLE DE CODE DE TRES MAUVAISE QUALITE FAIT PAR UN DEBUTANT
 *
 *  Pas de lisibilité, pas de commentaires, pas de tests
 *  Pas de documentation, pas de gestion des erreurs
 *  Pas de logique métier, pas de modularité
 *  Pas de gestion des exceptions, pas de gestion des logs
 *  Principe "Single Responsability" non respecté
 *  Pas de traçabilité vers les exigences métier
 *
 *  Pourtant ce code fonctionne correctement
 *  Il s'agit d'un "legacy" code qui est difficile à maintenir
 *  L'auteur n'a pas fourni de tests unitaires
 **/

public class Simulateur {



    private int l00 = 0 ;
    private int l01 = 11294;
    private int l02 = 28797;
    private int l03 = 82341;
    private int l04 = 177106;
    private int l05 = Integer.MAX_VALUE;

    private int[] limites = new int[6];

    private double t00 = 0.0;
    private double t01 = 0.11;
    private double t02 = 0.3;
    private double t03 = 0.41;
    private double t04 = 0.45;

    private double[] taux = new double[5];

    private static final int ABATTEMENT_MAX = 14171;
    private static final int ABATTEMENT_MIN = 495;
    private static final double TAUX_ABATTEMENT = 0.1;

    private static final double PLAFOND_DEMI_PART = 1759;

    private static final double SEUIL_DECOTE_DECLARANT_SEUL = 1929;
    private static final double SEUIL_DECOTE_DECLARANT_COUPLE = 3191;

    private static final double DECOTE_MAX_DECLARANT_SEUL = 873;
    private static final double DECOTE_MAX_DECLARANT_COUPLE = 1444;
    private static final double TAUX_DECOTE = 0.4525;

    private int revenusNet = 0;
    private int nbEnf = 0;
    private int nbEnfHandicapes = 0;

    private double rFRef = 0;
    private double resteImposable = 0;

    private double nbPartsDecl = 0;
    private double nbParts = 0;
    private double decote = 0;

    private double montantImpDecl = 0;
    private double montantImpots = 0;


    private static final int CINQ = 5; //TODO trouver à quoi cette constante correspond

    // Fonction de calcul de l'impôt sur le revenu net en France en 2024 sur les revenu 2023


    /**
     * Fonction calculant et renvoyant l'abattement
     * @return abattement double
     */
    private double getAbattement(){
        double abattement = revenusNet * TAUX_ABATTEMENT;

        abattement = Math.max(abattement, ABATTEMENT_MAX);
        abattement = Math.min(abattement, ABATTEMENT_MIN);

        return abattement;
    }


    public long calculImpot(int revNet, SituationFamiliale sitFam, int nbEnfants, int nbEnfantsHandicapes, boolean parentIsol) {
        //TODO parentIso =/= parentIsol
        revenusNet = revNet;

        nbEnf = nbEnfants;
        nbEnfHandicapes = nbEnfantsHandicapes;

        //TODO on doit pouvoir faire mieux
        limites[0] = l00;
        limites[1] = l01;
        limites[2] = l02;
        limites[3] = l03;
        limites[4] = l04;
        limites[5] = l05;

        taux[0] = t00;
        taux[1] = t01;
        taux[2] = t02;
        taux[3] = t03;
        taux[4] = t04;

        // Abattement

        double abattement = getAbattement();
        rFRef = revenusNet - abattement;

        // parts déclarants

        nbPartsDecl = sitFam.getNbParts();

        //on garde la part du conjoint décédé si on a des enfants à charge
        if(sitFam == SituationFamiliale.VEUF && nbEnf > 0){

            nbPartsDecl = 2;
        }

        // parts enfants à charge
        if ( nbEnf <= 2 ) {
            nbParts = nbPartsDecl + nbEnf * 0.5;
        } else {
            nbParts = nbPartsDecl +  1.0 + ( nbEnf - 2 );
        }

        // parent isolé
        if (parentIsol && nbEnf > 0) {
            nbParts = nbParts + 0.5;
        }

        // enfant handicapé
        nbParts = nbParts + nbEnfHandicapes * 0.5;

        // impôt des declarants
        resteImposable = rFRef / nbPartsDecl;

        montantImpDecl = 0;

        //TODO A changer en fonction
        int i = 0;
        do {
            if ( resteImposable >= limites[i] && resteImposable < limites[i+1] ) {
                montantImpDecl += ( resteImposable - limites[i] ) * taux[i];
                break;
            } else {
                montantImpDecl += ( limites[i+1] - limites[i] ) * taux[i];
            }
            i++;
        } while( i < CINQ);

        montantImpDecl = montantImpDecl * nbPartsDecl;
        montantImpDecl = Math.round(montantImpDecl);

        // impôt foyer fiscal complet
        resteImposable =  rFRef / nbParts;
        montantImpots = 0;

        //TODO A changer en fonction
        i = 0;

        do {
            if ( resteImposable >= limites[i] && resteImposable < limites[i+1] ) {
                montantImpots += ( resteImposable - limites[i] ) * taux[i];
                break;
            } else {
                montantImpots += ( limites[i+1] - limites[i] ) * taux[i];
            }
            i++;
        } while( i < CINQ);

        montantImpots = montantImpots * nbParts;
        montantImpots = Math.round(montantImpots);

        // baisse impot
        double baisseImpot = montantImpDecl - montantImpots;

        // dépassement plafond
        double ecartParts = nbParts - nbPartsDecl;

        double plafond = ecartParts * PLAFOND_DEMI_PART * 2;

        if ( baisseImpot >= plafond ) {
            montantImpots = montantImpDecl - plafond;
        }

        decote = 0;
        // decote
        if ( nbPartsDecl == 1 ) {
            if ( montantImpots < SEUIL_DECOTE_DECLARANT_SEUL) {
                 decote = DECOTE_MAX_DECLARANT_SEUL - ( montantImpots * TAUX_DECOTE);
            }
        }
        if (  nbPartsDecl == 2 ) {
            if ( montantImpots < SEUIL_DECOTE_DECLARANT_COUPLE) {
                 decote =  DECOTE_MAX_DECLARANT_COUPLE - ( montantImpots * TAUX_DECOTE);
            }
        }

        decote = Math.round( decote );


        montantImpots = Math.max(0, montantImpots - decote);

        return Math.round(montantImpots);
    }

    public static void main(String[] args) {
        Simulateur simulateur = new Simulateur();
        long impot = simulateur.calculImpot(65000, SituationFamiliale.MARIE, 3, 0, false);
        System.out.println("Impot sur le revenu net : " + impot);
        impot = simulateur.calculImpot(65000, SituationFamiliale.MARIE, 3, 1, false);
        System.out.println("Impot sur le revenu net : " + impot);
        impot = simulateur.calculImpot(35000, SituationFamiliale.DIVORCE, 1, 0, true);
        System.out.println("Impot sur le revenu net : " + impot);
        impot = simulateur.calculImpot(35000, SituationFamiliale.DIVORCE, 2, 0, true);
        System.out.println("Impot sur le revenu net : " + impot);
        impot = simulateur.calculImpot(50000, SituationFamiliale.DIVORCE, 3, 0, true);
        System.out.println("Impot sur le revenu net : " + impot);
        impot = simulateur.calculImpot(50000, SituationFamiliale.DIVORCE, 3, 1, true);
        System.out.println("Impot sur le revenu net : " + impot);
        impot = simulateur.calculImpot(200000, SituationFamiliale.CELIBATAIRE, 0, 0, true);
        System.out.println("Impot sur le revenu net : " + impot);

    }

}
