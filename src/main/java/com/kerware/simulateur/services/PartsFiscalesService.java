package com.kerware.simulateur.services;

import com.kerware.simulateur.model.SituationFamiliale;

public class PartsFiscalesService {
    public double calculer(SituationFamiliale situation, int nbEnfants, int nbEnfantsHandicapes, boolean parentIsole) {
        double parts = situation.nbParts;
        parts += (nbEnfants <= 2) ? nbEnfants * 0.5 : 1.0 + (nbEnfants - 2);
        if (parentIsole && nbEnfants > 0) parts += 0.5;
        return parts + (nbEnfantsHandicapes * 0.5);
    }
}