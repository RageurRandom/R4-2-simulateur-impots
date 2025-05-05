package com.kerware.simulateur.model;

public class FoyerFiscal {
    private final int revenusNet;
    private final SituationFamiliale situation;
    private final int nbEnfants;
    private final int nbEnfantsHandicapes;
    private final boolean parentIsole;

    public FoyerFiscal(int revenusNet,
                       SituationFamiliale situation,
                       int nbEnfants,
                       int nbEnfantsHandicapes,
                       boolean parentIsole) {
        this.revenusNet = revenusNet;
        this.situation = situation;
        this.nbEnfants = nbEnfants;
        this.nbEnfantsHandicapes = nbEnfantsHandicapes;
        this.parentIsole = parentIsole;
    }

    // Getters
    public int getRevenusNet() {
        return revenusNet;
    }

    public SituationFamiliale getSituation() {
        return situation;
    }

    public int getNbEnfants() {
        return nbEnfants;
    }

    public int getNbEnfantsHandicapes() {
        return nbEnfantsHandicapes;
    }

    public boolean isParentIsole() {
        return parentIsole;
    }

    // Méthode utilitaire pour la validation
    public boolean isValid() {
        return revenusNet >= 0 &&
                situation != null &&
                nbEnfants >= 0 &&
                nbEnfantsHandicapes >= 0;
    }
}