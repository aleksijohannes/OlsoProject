package controller;

import eduni.distributions.Generator;

public interface IKontrolleriMtoV {
	
		// Rajapinta, joka tarjotaan moottorille:
		
		public void naytaLoppuaika(double aika);
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
