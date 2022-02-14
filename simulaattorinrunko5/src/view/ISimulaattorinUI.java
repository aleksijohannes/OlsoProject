package view;

public interface ISimulaattorinUI {
	
	// Syötteet, jotka kontrolleri hakee käyttöliittymästä ja välittää model-tasolle
	public double getAika();
	public long getViive();
	
	public String getSaapumisjakauma(); // Palauttaa jakauman merkkijonona: "normal"/"uniform"/"negexp"
	public int getSaapumistiheys();		// Palauttaa tiheyden numerona 1-3 (1=hitain)
	
	public int getOviMaara();			// Palauttaa palvelupisteiden määrän väliltä 1-10
	public String getOviJakauma();		// Palauttaa jakauman merkkijonona: "normal"/"uniform"/"negexp"
	public int getOviPalvelunopeus();	// Palauttaa nopeuden numerona 1-3 (1=hitain)
	
	public int getIlmoMaara();
	public String getIlmoJakauma();
	public int getIlmoPalvelunopeus();
	
	public int getRokMaara();
	public String getRokJakauma();
	public int getRokPalvelunopeus();
	
	public String getSeurJakauma();
	public int getSeurPalvelunopeus();
	
	// Model tason tuottamat syötteet, jotka kontrolleri vie käyttöliittymään 
	public void setLoppuaika(double aika);
	
	// Kontrolleri tarvitsee  
	public IVisualisointi getVisualisointi();

}
