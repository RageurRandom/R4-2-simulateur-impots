package com.kerware.simulateur;

import com.kerware.simulateur.model.SituationFamiliale;
import com.kerware.simulateur.services.*;
import com.kerware.simulateur.exceptions.CalculImpotException;

public class CalculateurImpot implements ICalculateurImpot {
    // Services
    private final AbattementService abattementService = new AbattementService();
    private final PartsFiscalesService partsService = new PartsFiscalesService();
    private final TrancheImpotService trancheService = new TrancheImpotService();
    private final PlafonnementService plafonnementService = new PlafonnementService();
    private final DecoteService decoteService = new DecoteService();

    // Données du foyer
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

    @Override
    public void setRevenusNet(int rn) {
        if (rn < 0) throw new CalculImpotException("Le revenu net ne peut pas être négatif");
        this.revenusNet = rn;
    }

    @Override
    public void setSituationFamiliale(SituationFamiliale sf) {
        if (sf == null) throw new CalculImpotException("La situation familiale ne peut pas être nulle");
        this.situationFamiliale = sf;
    }

    @Override
    public void setNbEnfantsACharge(int nbe) {
        if (nbe < 0) throw new CalculImpotException("Le nombre d'enfants ne peut pas être négatif");
        this.nbEnfants = nbe;
    }

    @Override
    public void setNbEnfantsSituationHandicap(int nbesh) {
        if (nbesh < 0) throw new CalculImpotException("Le nombre d'enfants handicapés ne peut pas être négatif");
        this.nbEnfantsHandicapes = nbesh;
    }

    @Override
    public void setParentIsole(boolean pi) {
        this.parentIsole = pi;
    }

    @Override
    public void calculImpotSurRevenuNet() {
        // Validation implicite via les setters
        if (situationFamiliale == null) {
            throw new CalculImpotException("La situation familiale doit être définie avant le calcul");
        }

        // 1. Calcul abattement et RFR
        this.abattement = abattementService.calculer(revenusNet);
        this.revenuFiscalReference = revenusNet - abattement;

        // 2. Calcul parts fiscales
        this.nbParts = partsService.calculer(
                situationFamiliale,
                nbEnfants,
                nbEnfantsHandicapes,
                parentIsole
        );

        // 3. Calcul impôt par tranches
        double quotientFamilial = revenuFiscalReference / nbParts;
        double impotFoyer = trancheService.calculer(quotientFamilial) * nbParts;

        // 4. Plafonnement QF
        double quotientDeclarants = revenuFiscalReference / situationFamiliale.nbParts;
        double impotDeclarants = trancheService.calculer(quotientDeclarants) * situationFamiliale.nbParts;

        this.impotAvantDecote = (int) Math.round(
                plafonnementService.appliquer(
                        impotDeclarants,
                        impotFoyer,
                        nbParts,
                        situationFamiliale.nbParts
                )
        );

        // 5. Calcul décote
        this.decote = decoteService.calculer(
                situationFamiliale.nbParts,
                impotAvantDecote
        );

        // 6. Impôt final
        this.impotSurRevenuNet = Math.max(0, impotAvantDecote - decote);
    }

    // Getters
    @Override public int getRevenuFiscalReference() { return revenuFiscalReference; }
    @Override public int getAbattement() { return abattement; }
    @Override public int getNbPartsFoyerFiscal() { return (int) Math.round(nbParts * 100); }
    @Override public int getImpotAvantDecote() { return impotAvantDecote; }
    @Override public int getDecote() { return decote; }
    @Override public int getImpotSurRevenuNet() { return impotSurRevenuNet; }

    // Getters pour la validation (package-private)
    int getRevenusNet() { return revenusNet; }
    SituationFamiliale getSituationFamiliale() { return situationFamiliale; }
    int getNbEnfants() { return nbEnfants; }
    int getNbEnfantsHandicapes() { return nbEnfantsHandicapes; }
    boolean isParentIsole() { return parentIsole; }
}