package com.kerware.simulateur.model;

/**
 * Enumération représentant les différentes situations familiales possibles
 * pour un contribuable, avec le nombre de parts fiscales correspondant.
 */
public enum SituationFamiliale {
    CELIBATAIRE(1),
    PACSE(2),
    MARIE(2),
    DIVORCE(1),
    VEUF(1);

    public final int nbParts;

    SituationFamiliale(int nbParts) {
        this.nbParts = nbParts;
    }
}