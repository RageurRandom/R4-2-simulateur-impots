package com.kerware.oldsimulateur;

import com.kerware.simulateur.model.SituationFamiliale;

public interface ICalculateurImpot {

    public void setRevenusNet( int rn );
    public void setSituationFamiliale( SituationFamiliale sf );
    public void setNbEnfantsACharge( int nbe );
    public void setNbEnfantsSituationHandicap( int nbesh );
    public void setParentIsole( boolean pi );

    public void calculImpotSurRevenuNet();

    public int getRevenuFiscalReference();
    public int getAbattement();
    public int getNbPartsFoyerFiscal();
    public int getImpotAvantDecote();
    public int getDecote();
    public int getImpotSurRevenuNet();

}
