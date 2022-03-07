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
		public void naytaPieninAsiakas(double lapimenoaika);
		public void naytaSuurinAsiakas(double lapimenoaika);
		public void naytaSuurinOviJono(double jonotusaika);
		public void naytaSuurinIlmoJono(double jonotusaika);
		public void naytaSuurinRokJono(double jonotusaika);
		public void naytaSuurinSeurJono(double jonotusaika);
		public void naytaPalvellutAsiakkaat(int kpl);
		public void naytaAvgOviJono(double jonotusaika);
		public void naytaAvgIlmoJono(double jonotusaika);
		public void naytaAvgRokJono(double jonotusaika);
		public void naytaAvgSeurJono(double jonotusaika);
		
		//Visualisointi
		public void visualisoiAsiakas(int jono);
		public void poistaAsiakas(int poista);
		
		// Ajoparametrien haku kontrollerista
		public int getOviMaara();
		public int getIlmoMaara();
		public int getRokMaara();
		public ContinuousGenerator getOviJakauma();
		public ContinuousGenerator getIlmoJakauma();
		public ContinuousGenerator getRokJakauma();
		public ContinuousGenerator getSeurJakauma();
		public ContinuousGenerator getSaapumisjakauma();

}
