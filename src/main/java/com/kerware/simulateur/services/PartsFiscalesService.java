package com.kerware.simulateur.services;

import com.kerware.simulateur.model.SituationFamiliale;

public class PartsFiscalesService {
    private static final double TAUX_PART_ENFANT = 0.5;

    /**
     *
     * @param situation Situation familiale
     * @param nbEnfants int nombre total d'enfants à charge
     * @param nbEnfantsHandicapes int nombre d'enfants en situation de handicap
     * @param parentIsole boolean
     * @return double nombre de parts pour ce foyer d'imposition
     */
    public double calculer(SituationFamiliale situation, int nbEnfants,
                           int nbEnfantsHandicapes, boolean parentIsole) {

        double parts = situation.getNbParts();
        if (nbEnfants <= 2) {
            parts += nbEnfants * TAUX_PART_ENFANT;
        } else {
            // A partir du 3ème enfant chaque enfant compte pour une part
            parts += TAUX_PART_ENFANT * 2 + (nbEnfants - 2);
        }
        if (parentIsole && nbEnfants > 0) {
            parts += TAUX_PART_ENFANT;
        }
        return parts + (nbEnfantsHandicapes * TAUX_PART_ENFANT);
    }
}