package com.kerware.simulateur;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ICalculateurImpotTest {

    @Test
    void getImpotSurRevenuNet() {
        Simulateur simulateur = new Simulateur();
        long impot = simulateur.calculImpot(65000, SituationFamiliale.MARIE, 3, 0, false);
        assertEquals(685, impot);
        impot = simulateur.calculImpot(65000, SituationFamiliale.MARIE, 3, 1, false);
        assertEquals(0, impot);
        impot = simulateur.calculImpot(35000, SituationFamiliale.DIVORCE, 1, 0, true);
        assertEquals(550, impot);
        impot = simulateur.calculImpot(35000, SituationFamiliale.DIVORCE, 2, 0, true);
        assertEquals(0, impot);
        impot = simulateur.calculImpot(50000, SituationFamiliale.DIVORCE, 3, 0, true);
        assertEquals(1, impot);
        impot = simulateur.calculImpot(50000, SituationFamiliale.DIVORCE, 3, 1, true);
        assertEquals(0, impot);
        impot = simulateur.calculImpot(200000, SituationFamiliale.CELIBATAIRE, 0, 0, true);
        assertEquals(60768, impot);
    }
}