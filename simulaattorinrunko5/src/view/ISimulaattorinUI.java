package view;

import java.util.HashMap;

/**
 * Rajapinta sisältää tiedon metodeista, jotka toteutetaan käyttöliittymässä, ja joita kontrolleri kutsuu.
 * 
 * @author Jenni Javanainen
 */

public interface ISimulaattorinUI {
	
	// Syötteet, jotka kontrolleri hakee käyttöliittymästä ja välittää model-tasolle
	public double getAika();
	public long getViive();
	
	public String getSaapumisjakauma(); 
	public int getSaapumistiheys();		

	public int getOviMaara();			
	public String getOviJakauma();		
	public int getOviPalvelunopeus();	
	
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
	public void setLapimenoaika(double aika);
	public void setKayttoasteet(HashMap<String, Double> palvelupisteet);
	public void setSuoritustehot(HashMap<String, Double> palvelupisteet);
	
	// Kontrolleri tarvitsee  
	public IVisualisointi getVisualisointi();

}
