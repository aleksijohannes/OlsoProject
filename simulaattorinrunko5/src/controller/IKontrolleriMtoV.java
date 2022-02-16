package controller;

import java.util.HashMap;

import eduni.distributions.ContinuousGenerator;

public interface IKontrolleriMtoV {
	
		// Rajapinta, joka tarjotaan moottorille:
		
		public void naytaLoppuaika(double aika);
		public void naytaAvgLapimeno(double aika);
		public void naytaKayttoaste(HashMap<String, Double> palvelupisteet);
		public void naytaSuoritusteho(HashMap<String, Double> palvelupisteet);
		public void visualisoiAsiakas();
		
		public int getOviMaara();
		public int getIlmoMaara();
		public int getRokMaara();
		
		public ContinuousGenerator getOviJakauma();
		public ContinuousGenerator getIlmoJakauma();
		public ContinuousGenerator getRokJakauma();
		public ContinuousGenerator getSeurJakauma();
		
		public ContinuousGenerator getSaapumisjakauma();

}
