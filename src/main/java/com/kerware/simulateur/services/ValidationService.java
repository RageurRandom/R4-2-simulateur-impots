package com.kerware.simulateur.services;

import com.kerware.simulateur.exceptions.CalculImpotException;
import com.kerware.simulateur.model.FoyerFiscal;

public class ValidationService {
    public static void valider(FoyerFiscal foyer) {
        if (foyer.getRevenusNet() < 0) throw new CalculImpotException("Revenus négatifs");
        if (foyer.getNbEnfants() < 0) throw new CalculImpotException("Nombre d'enfants invalide");
    }
}