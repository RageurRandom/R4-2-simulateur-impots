package com.kerware.simulateur;

import com.kerware.simulateur.exceptions.CalculImpotException;

/**
 * Implémentation du calculateur d'impôt sur le revenu conforme à la législation française 2024.
 * Ce calculateur prend en compte :
 * - Les différentes situations familiales (célibataire, marié, etc.)
 * - Le nombre d'enfants à charge
 * - Les enfants handicapés
 * - Le statut de parent isolé
 *
 * Conforme aux exigences fiscales pour l'année 2024 sur les revenus 2023.
 */
public class CalculateurImpot implements ICalculateurImpot {

    // Constantes fiscales
    private static final int[] LIMITES_TRANCHES = {0, 11294, 28797, 82341, 177106, Integer.MAX_VALUE};
    private static final double[] TAUX_TRANCHES = {0.0, 0.11, 0.3, 0.41, 0.45};
    private static final int ABATTEMENT_MAX = 14171;
    private static final int ABATTEMENT_MIN = 495;
    private static final double TAUX_ABATTEMENT = 0.1;
    private static final double PLAFOND_DEMI_PART = 1759;
    private static final double SEUIL_DECOTE_DECLARANT_SEUL = 1929;
    private static final double SEUIL_DECOTE_DECLARANT_COUPLE = 3191;
    private static final double DECOTE_MAX_DECLARANT_SEUL = 873;
    private static final double DECOTE_MAX_DECLARANT_COUPLE = 1444;
    private static final double TAUX_DECOTE = 0.4525;
    private static final int NB_TRANCHES = 5;

    // Variables d'instance
    private int revenusNet;
    private SituationFamiliale situationFamiliale;
    private int nbEnfants;
    private int nbEnfantsHandicapes;
    private boolean parentIsole;

    // Résultats du calcul
    private int revenuFiscalReference;
    private int abattement;
    private double nbParts;
    private int impotAvantDecote;
    private int decote;
    private int impotSurRevenuNet;

    /**
     * Constructeur par défaut
     */
    public CalculateurImpot() {
        // Initialisation des valeurs par défaut
        this.revenusNet = 0;
        this.situationFamiliale = SituationFamiliale.CELIBATAIRE;
        this.nbEnfants = 0;
        this.nbEnfantsHandicapes = 0;
        this.parentIsole = false;
    }

    // Implémentation des méthodes de l'interface
    @Override
    public void setRevenusNet(int rn) {
        if (rn < 0) {
            throw new CalculImpotException("Le revenu net ne peut pas être négatif");
        }
        this.revenusNet = rn;
    }

    @Override
    public void setSituationFamiliale(SituationFamiliale sf) {
        if (sf == null) {
            throw new CalculImpotException("La situation familiale ne peut pas être nulle");
        }
        this.situationFamiliale = sf;
    }

    @Override
    public void setNbEnfantsACharge(int nbe) {
        if (nbe < 0) {
            throw new CalculImpotException("Le nombre d'enfants ne peut pas être négatif");
        }
        this.nbEnfants = nbe;
    }

    @Override
    public void setNbEnfantsSituationHandicap(int nbesh) {
        if (nbesh < 0) {
            throw new CalculImpotException("Le nombre d'enfants handicapés ne peut pas être négatif");
        }
        this.nbEnfantsHandicapes = nbesh;
    }

    @Override
    public void setParentIsole(boolean pi) {
        this.parentIsole = pi;
    }

    @Override
    public void calculImpotSurRevenuNet() {
        validerDonnees();
        calculerAbattement();
        calculerNombreParts();
        calculerImpotAvantDecote();
        calculerDecote();
        calculerImpotFinal();
    }

    @Override
    public int getRevenuFiscalReference() {
        return revenuFiscalReference;
    }

    @Override
    public int getAbattement() {
        return abattement;
    }

    @Override
    public int getNbPartsFoyerFiscal() {
        return (int) Math.round(nbParts * 100); // Retourne en centièmes pour plus de précision
    }

    @Override
    public int getImpotAvantDecote() {
        return impotAvantDecote;
    }

    @Override
    public int getDecote() {
        return decote;
    }

    @Override
    public int getImpotSurRevenuNet() {
        return impotSurRevenuNet;
    }

    // Méthodes privées de calcul
    private void validerDonnees() {
        if (revenusNet < 0) {
            throw new CalculImpotException("Le revenu net doit être positif");
        }
        if (nbEnfants < 0) {
            throw new CalculImpotException("Le nombre d'enfants doit être positif");
        }
        if (nbEnfantsHandicapes < 0) {
            throw new CalculImpotException("Le nombre d'enfants handicapés doit être positif");
        }
    }

    private void calculerAbattement() {
        double abattementCalc = revenusNet * TAUX_ABATTEMENT;
        abattementCalc = Math.min(abattementCalc, ABATTEMENT_MAX);
        abattementCalc = Math.max(abattementCalc, ABATTEMENT_MIN);
        abattement = (int) Math.round(abattementCalc);
        revenuFiscalReference = revenusNet - abattement;
    }

    private void calculerNombreParts() {
        double nbPartsDeclarants = situationFamiliale.nbParts;

        // Cas particulier des veufs avec enfants
        if (situationFamiliale == SituationFamiliale.VEUF && nbEnfants > 0) {
            nbPartsDeclarants = 2;
        }

        // Calcul des parts pour les enfants
        if (nbEnfants <= 2) {
            nbParts = nbPartsDeclarants + nbEnfants * 0.5;
        } else {
            nbParts = nbPartsDeclarants + 1.0 + (nbEnfants - 2);
        }

        // Majoration pour parent isolé
        if (parentIsole && nbEnfants > 0) {
            nbParts += 0.5;
        }

        // Majoration pour enfants handicapés
        nbParts += nbEnfantsHandicapes * 0.5;
    }

    private void calculerImpotAvantDecote() {
        double quotientFamilial = revenuFiscalReference / nbParts;
        double impotCalcul = calculerImpotTranches(quotientFamilial) * nbParts;

        // Application du plafonnement du quotient familial
        double impotDeclarants = calculerImpotTranches(revenuFiscalReference / situationFamiliale.nbParts) * situationFamiliale.nbParts;
        double ecartParts = nbParts - situationFamiliale.nbParts;
        double plafond = ecartParts * PLAFOND_DEMI_PART * 2;

        if ((impotDeclarants - impotCalcul) > plafond) {
            impotCalcul = impotDeclarants - plafond;
        }

        impotAvantDecote = (int) Math.round(impotCalcul);
    }

    private double calculerImpotTranches(double revenu) {
        double impot = 0;

        for (int i = 0; i < NB_TRANCHES; i++) {
            if (revenu <= LIMITES_TRANCHES[i]) {
                break;
            }

            double montantTranche = Math.min(revenu, LIMITES_TRANCHES[i+1]) - LIMITES_TRANCHES[i];
            impot += montantTranche * TAUX_TRANCHES[i];
        }

        return impot;
    }

    private void calculerDecote() {
        if (situationFamiliale.nbParts == 1 && impotAvantDecote < SEUIL_DECOTE_DECLARANT_SEUL) {
            decote = (int) Math.round(DECOTE_MAX_DECLARANT_SEUL - (impotAvantDecote * TAUX_DECOTE));
        } else if (situationFamiliale.nbParts == 2 && impotAvantDecote < SEUIL_DECOTE_DECLARANT_COUPLE) {
            decote = (int) Math.round(DECOTE_MAX_DECLARANT_COUPLE - (impotAvantDecote * TAUX_DECOTE));
        } else {
            decote = 0;
        }

        decote = Math.max(0, decote);
    }

    private void calculerImpotFinal() {
        impotSurRevenuNet = Math.max(0, impotAvantDecote - decote);
    }
}