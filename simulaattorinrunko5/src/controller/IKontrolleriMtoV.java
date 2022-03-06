package controller;

import java.util.HashMap;

import eduni.distributions.ContinuousGenerator;

/**
 * Rajapinta sisältää tiedon metodeista, jotka toteutetaan kontrollerissa, ja joita moottori kutsuu.
 * 
 * @author Jenni Javanainen
 */

public interface IKontrolleriMtoV {
		
		// Simulointitulosten vieminen ylöspäin
		public void naytaLoppuaika(double aika);
		public void naytaAvgLapimeno(double aika);
		public void naytaKayttoaste(HashMap<String, Double> palvelupisteet);
		public void naytaSuoritusteho(HashMap<String, Double> palvelupisteet);
		public void visualisoiAsiakas();
		
		// Ajoparametrien haku
		public int getOviMaara();
		public int getIlmoMaara();
		public int getRokMaara();
		public ContinuousGenerator getOviJakauma();
		public ContinuousGenerator getIlmoJakauma();
		public ContinuousGenerator getRokJakauma();
		public ContinuousGenerator getSeurJakauma();
		public ContinuousGenerator getSaapumisjakauma();

}
