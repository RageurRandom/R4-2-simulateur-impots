package com.kerware.simulateur;

import com.kerware.simulateur.exceptions.CalculImpotException;
import com.kerware.simulateur.model.SituationFamiliale;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ICalculateurImpotTest {
    @Test
    void testExceptions(){
        ICalculateurImpot simulateurMock = new CalculateurImpot();
        simulateurMock.setRevenusNet(4900);
        simulateurMock.setNbEnfantsACharge(0);
        simulateurMock.setNbEnfantsSituationHandicap(0);
        simulateurMock.setParentIsole(false);
        assertThrows(CalculImpotException.class,()->{
            simulateurMock.calculImpotSurRevenuNet();
        });

        assertThrows(CalculImpotException.class, ()->{
            simulateurMock.setSituationFamiliale(null);
        });

        assertThrows(CalculImpotException.class, ()->{
            simulateurMock.setRevenusNet(-1);
        });

        assertThrows(CalculImpotException.class, ()->{
            simulateurMock.setNbEnfantsACharge(-1);
        });

        assertThrows(CalculImpotException.class, ()->{
            simulateurMock.setNbEnfantsSituationHandicap(-1);
        });
    }

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
    void testSetRevenusNet() {
        ICalculateurImpot simulateur = new CalculateurImpot();
        simulateur.setRevenusNet(5000);
        assertEquals(5000, ((CalculateurImpot) simulateur).getRevenusNet());

        // Test for negative value
        assertThrows(CalculImpotException.class, () -> simulateur.setRevenusNet(-1000));
    }

    @Test
    void testSetSituationFamiliale() {
        ICalculateurImpot simulateur = new CalculateurImpot();
        simulateur.setSituationFamiliale(SituationFamiliale.CELIBATAIRE);
        assertEquals(SituationFamiliale.CELIBATAIRE, ((CalculateurImpot) simulateur).getSituationFamiliale());

        // Test for null value
        assertThrows(CalculImpotException.class, () -> simulateur.setSituationFamiliale(null));
    }

    @Test
    void testSetNbEnfantsACharge() {
        ICalculateurImpot simulateur = new CalculateurImpot();
        simulateur.setNbEnfantsACharge(2);
        assertEquals(2, ((CalculateurImpot) simulateur).getNbEnfants());

        // Test for negative value
        assertThrows(CalculImpotException.class, () -> simulateur.setNbEnfantsACharge(-1));
    }

    @Test
    void testSetNbEnfantsSituationHandicap() {
        ICalculateurImpot simulateur = new CalculateurImpot();
        simulateur.setNbEnfantsSituationHandicap(1);
        assertEquals(1, ((CalculateurImpot) simulateur).getNbEnfantsHandicapes());

        // Test for negative value
        assertThrows(CalculImpotException.class, () -> simulateur.setNbEnfantsSituationHandicap(-1));
    }

    @Test
    void testSetParentIsole() {
        ICalculateurImpot simulateur = new CalculateurImpot();
        simulateur.setParentIsole(true);
        assertEquals(true, ((CalculateurImpot) simulateur).isParentIsole());

        simulateur.setParentIsole(false);
        assertEquals(false, ((CalculateurImpot) simulateur).isParentIsole());
    }

    @Test
    void testGetRevenuFiscalReference() {
        ICalculateurImpot simulateur = new CalculateurImpot();
        simulateur.setRevenusNet(5000);
        simulateur.setSituationFamiliale(SituationFamiliale.CELIBATAIRE);
        simulateur.setNbEnfantsACharge(0);
        simulateur.setNbEnfantsSituationHandicap(0);
        simulateur.setParentIsole(false);
        simulateur.calculImpotSurRevenuNet();

        assertEquals(5000 - simulateur.getAbattement(), simulateur.getRevenuFiscalReference()); // Example value
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