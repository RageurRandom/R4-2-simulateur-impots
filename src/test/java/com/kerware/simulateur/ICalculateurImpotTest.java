package com.kerware.simulateur;

import com.kerware.simulateur.model.SituationFamiliale;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ICalculateurImpotTest {
    @Test
    void getAbattement(){
        ICalculateurImpot simulateurMock = new CalculateurImpot();
        simulateurMock.setRevenusNet(4900);
        simulateurMock.setSituationFamiliale(SituationFamiliale.CELIBATAIRE);
        simulateurMock.setNbEnfantsACharge(0);
        simulateurMock.setNbEnfantsSituationHandicap(0);
        simulateurMock.setParentIsole(false);
        simulateurMock.calculImpotSurRevenuNet();

        assertEquals(simulateurMock.getAbattement(), 495);

    }

    @Test
    void getImpotSurRevenuNet() {
        ICalculateurImpot simulateur = new CalculateurImpot();

        simulateur.setRevenusNet(65000);
        simulateur.setSituationFamiliale(SituationFamiliale.MARIE);
        simulateur.setNbEnfantsACharge(3);
        simulateur.setNbEnfantsSituationHandicap(0);
        simulateur.setParentIsole(false);
        simulateur.calculImpotSurRevenuNet();
        assertEquals(685, simulateur.getImpotSurRevenuNet());

        simulateur.setRevenusNet(65000);
        simulateur.setSituationFamiliale(SituationFamiliale.MARIE);
        simulateur.setNbEnfantsACharge(3);
        simulateur.setNbEnfantsSituationHandicap(1);
        simulateur.setParentIsole(false);
        simulateur.calculImpotSurRevenuNet();
        assertEquals(0, simulateur.getImpotSurRevenuNet());

        simulateur.setRevenusNet(35000);
        simulateur.setSituationFamiliale(SituationFamiliale.DIVORCE);
        simulateur.setNbEnfantsACharge(1);
        simulateur.setNbEnfantsSituationHandicap(0);
        simulateur.setParentIsole(true);
        simulateur.calculImpotSurRevenuNet();
        assertEquals(550, simulateur.getImpotSurRevenuNet());

        simulateur.setRevenusNet(35000);
        simulateur.setSituationFamiliale(SituationFamiliale.DIVORCE);
        simulateur.setNbEnfantsACharge(2);
        simulateur.setNbEnfantsSituationHandicap(0);
        simulateur.setParentIsole(true);
        simulateur.calculImpotSurRevenuNet();
        assertEquals(0, simulateur.getImpotSurRevenuNet());

        simulateur.setRevenusNet(50000);
        simulateur.setSituationFamiliale(SituationFamiliale.DIVORCE);
        simulateur.setNbEnfantsACharge(3);
        simulateur.setNbEnfantsSituationHandicap(0);
        simulateur.setParentIsole(true);
        simulateur.calculImpotSurRevenuNet();
        assertEquals(1, simulateur.getImpotSurRevenuNet());

        simulateur.setRevenusNet(50000);
        simulateur.setSituationFamiliale(SituationFamiliale.DIVORCE);
        simulateur.setNbEnfantsACharge(3);
        simulateur.setNbEnfantsSituationHandicap(1);
        simulateur.setParentIsole(true);
        simulateur.calculImpotSurRevenuNet();
        assertEquals(0, simulateur.getImpotSurRevenuNet());

        simulateur.setRevenusNet(200000);
        simulateur.setSituationFamiliale(SituationFamiliale.CELIBATAIRE);
        simulateur.setNbEnfantsACharge(0);
        simulateur.setNbEnfantsSituationHandicap(0);
        simulateur.setParentIsole(true);
        simulateur.calculImpotSurRevenuNet();
        assertEquals(60768, simulateur.getImpotSurRevenuNet());

        simulateur.setRevenusNet(200000);
        simulateur.setSituationFamiliale(SituationFamiliale.VEUF);
        simulateur.setNbEnfantsACharge(0);
        simulateur.setNbEnfantsSituationHandicap(0);
        simulateur.setParentIsole(false);
        simulateur.calculImpotSurRevenuNet();
        assertEquals(60768, simulateur.getImpotSurRevenuNet());

        simulateur.setRevenusNet(200000);
        simulateur.setSituationFamiliale(SituationFamiliale.VEUF);
        simulateur.setNbEnfantsACharge(1);
        simulateur.setNbEnfantsSituationHandicap(0);
        simulateur.setParentIsole(false);
        simulateur.calculImpotSurRevenuNet();
        assertEquals(59009, simulateur.getImpotSurRevenuNet());


        simulateur.setRevenusNet(4900);
        simulateur.setSituationFamiliale(SituationFamiliale.CELIBATAIRE);
        simulateur.setNbEnfantsACharge(0);
        simulateur.setNbEnfantsSituationHandicap(0);
        simulateur.setParentIsole(false);
        simulateur.calculImpotSurRevenuNet();
        assertEquals(0, simulateur.getImpotSurRevenuNet());
    }
}