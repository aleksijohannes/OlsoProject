package controller;

import eduni.distributions.Generator;
import simu.model.PalvelupisteenTyyppi;

public interface IKontrolleriMtoV {
	
		// Rajapinta, joka tarjotaan moottorille:
		
		public void naytaLoppuaika(double aika);
		public void naytaAvgLapimeno(double aika);
		public void naytaKayttoaste(int id, PalvelupisteenTyyppi tyyppi, double kayttoaste);
		public void naytaSuoritusteho(int id, PalvelupisteenTyyppi tyyppi, double suoritusteho);
		public void visualisoiAsiakas();
		
		public int getOviMaara();
		public int getIlmoMaara();
		public int getRokMaara();
		
		public Generator getOviJakauma();
		public Generator getIlmoJakauma();
		public Generator getRokJakauma();
		public Generator getSeurJakauma();
		
		public Generator getSaapumisjakauma();

}
