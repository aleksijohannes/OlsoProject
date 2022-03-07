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
	public void setPieninAsiakas(double lapimenoaika);
	public void setSuurinAsiakas(double lapimenoaika);
	public void setSuurinOviJono(double jonotusaika);
	public void setSuurinIlmoJono(double jonotusaika);
	public void setSuurinRokJono(double jonotusaika);
	public void setSuurinSeurJono(double jonotusaika);
	public void setPalvellutAsiakkaat(int kpl);
	public void setAvgOviJono(double jonotusaika);
	public void setAvgIlmoJono(double jonotusaika);
	public void setAvgRokJono(double jonotusaika);
	public void setAvgSeurJono(double jonotusaika);

	
	// Kontrolleri tarvitsee  
	public IVisualisointi getVisualisointi();

}
