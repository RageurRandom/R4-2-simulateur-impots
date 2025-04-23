package com.kerware.simulateur;

public enum SituationFamiliale {
    CELIBATAIRE(1),
    PACSE(2),
    MARIE(2),
    DIVORCE(1),
    VEUF(1);

    public final int nbParts;

    private SituationFamiliale(int nbParts){
        this.nbParts = nbParts;
    }


}
