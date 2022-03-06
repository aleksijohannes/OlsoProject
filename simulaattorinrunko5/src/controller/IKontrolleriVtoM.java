package controller;

import java.util.ArrayList;

/**
 * Rajapinta sisältää tiedon metodeista, jotka toteutetaan kontrollerissa, ja joita käyttöliittymä kutsuu.
 * 
 * @author Jenni Javanainen
 */

public interface IKontrolleriVtoM {
		
		// Simulaation ajoon liittyvät metodit
		public void kaynnistaSimulointi();
		public void nopeuta();
		public void hidasta();
		
		// Tietokannan kanssa kommunikointiin tarvittavat metodit
		public void tallennaSimulaatio();
		public ArrayList<Simulaatio> haeSimulaatiot();
		
		
}
