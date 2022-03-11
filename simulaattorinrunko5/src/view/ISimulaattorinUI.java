package view;

import java.util.HashMap;

/**
 * Rajapinta sisältää tiedon metodeista, jotka toteutetaan käyttöliittymässä, ja
 * joita kontrolleri kutsuu.
 * 
 * @author Jenni Javanainen
 */

public interface ISimulaattorinUI {

	/**
	 * Palauttaa syötteenä saadun simulaatioajan
	 * 
	 * @return simulaatioaika
	 */
	public double getAika();

	/**
	 * Palauttaa syötteenä saadun simulaatioviiveen
	 * 
	 * @return viive
	 */
	public long getViive();

	/**
	 * Palauttaa saapumistapahtumassa käytettävän jakaumatyypin
	 * 
	 * @return saapumisjakauma
	 */
	public String getSaapumisjakauma();

	/**
	 * Palauttaa saapumistapahtuman nopeuden lukuarvona
	 * 
	 * @return nopeus
	 */
	public int getSaapumistiheys();

	/**
	 * Palauttaa ovihenkilöiden määrän
	 * 
	 * @return ovihenkilöiden määrä
	 */
	public int getOviMaara();

	/**
	 * Palauttaa ovihenkilöiden kanssa käytettävän jakaumatyypin
	 * 
	 * @return saapumisjakauma
	 */
	public String getOviJakauma();

	/**
	 * Palauttaa ovihenkilöiden palvelunopeuden lukuarvona
	 * 
	 * @return nopeus
	 */
	public int getOviPalvelunopeus();

	/**
	 * Palauttaa ilmoittautumistiskien määrän
	 * 
	 * @return ilmoittautumistiskien määrä
	 */
	public int getIlmoMaara();

	/**
	 * Palauttaa ilmoittautumistiskien kanssa käytettävän jakaumatyypin
	 * 
	 * @return saapumisjakauma
	 */
	public String getIlmoJakauma();

	/**
	 * Palauttaa ilmoittautumistiskien palvelunopeuden lukuarvona
	 * 
	 * @return nopeus
	 */
	public int getIlmoPalvelunopeus();

	/**
	 * Palauttaa rokottajien määrän
	 * 
	 * @return rokottajien määrä
	 */
	public int getRokMaara();

	/**
	 * Palauttaa rokottajien kanssa käytettävän jakaumatyypin
	 * 
	 * @return saapumisjakauma
	 */
	public String getRokJakauma();

	/**
	 * Palauttaa rokottajien palvelunopeuden lukuarvona
	 * 
	 * @return nopeus
	 */
	public int getRokPalvelunopeus();

	/**
	 * Palauttaa jälkiseurannan kanssa käytettävän jakaumatyypin
	 * 
	 * @return saapumisjakauma
	 */
	public String getSeurJakauma();

	/**
	 * Palauttaa jälkiseurannan palvelunopeuden lukuarvona
	 * 
	 * @return nopeus
	 */
	public int getSeurPalvelunopeus();

	/**
	 * Asettaa simuloinnin loppuajan
	 * 
	 * @param simulointiaika
	 */
	public void setLoppuaika(double aika);

	/**
	 * Asettaa simulaation läpimenoaikojen keskiarvon
	 * 
	 * @param aika läpimenoaikojen ksekiarvo
	 */
	public void setLapimenoaika(double aika);

	/**
	 * Asettaa simulaatiossa käytettyjen palvelupisteiden käyttöasteet
	 * 
	 * @param palvelupisteet hajatustaulu, jossa jokaisen palvelupisteen tunnus ja
	 *                       käyttöaste
	 */
	public void setKayttoasteet(HashMap<String, Double> palvelupisteet);

	/**
	 * Asettaa simulaatiossa käytettyjen palvelupisteiden suoritustehot
	 * 
	 * @param palvelupisteet hajatustaulu, jossa jokaisen palvelupisteen tunnus ja
	 *                       suoritusteho
	 */
	public void setSuoritustehot(HashMap<String, Double> palvelupisteet);

	/**
	 * Asettaa pienimmän läpimenoajan
	 * 
	 * @param lapimenoaika pienin asiakaskohtainen läpimenoaika
	 */
	public void setPieninAsiakas(double lapimenoaika);

	/**
	 * Asettaa suurimman läpimenoajan
	 * 
	 * @param lapimenoaika suurin asiakskohtainen läpimenoaika
	 */
	public void setSuurinAsiakas(double lapimenoaika);

	/**
	 * Asettaa suurimman ovihenkilöille muodostuneen jonotusajan
	 * 
	 * @param jonotusaika pisin aika, jonka asiakas on jonottanut ovihenkilöille
	 */
	public void setSuurinOviJono(double jonotusaika);

	/**
	 * Asettaa suurimman ilmoittautumistiskeille muodostuneen jonotusajan
	 * 
	 * @param jonotusaika pisin aika, jonka asiakas on jonottanut
	 *                    ilmoittautumistiskeille
	 */
	public void setSuurinIlmoJono(double jonotusaika);

	/**
	 * Asettaa suurimman rokottajille muodostuneen jonotusajan
	 * 
	 * @param jonotusaika pisin aika, jonka asiakas on jonottanut rokottajille
	 */
	public void setSuurinRokJono(double jonotusaika);

	/**
	 * Asettaa suurimman jälkiseurantaan muodostuneen jonotusajan
	 * 
	 * @param jonotusaika pisin aika, jonka asiakas on jonottanut jälkiseurantaan
	 */
	public void setSuurinSeurJono(double jonotusaika);

	/**
	 * Asettaa palveltujen asiakkaiden lukumäärän
	 * 
	 * @param kpl simulaation aikana palvellut asiakkaat
	 */
	public void setPalvellutAsiakkaat(int kpl);

	/**
	 * Asettaa ovihenkilöiden jonotusaikojen keskiarvon
	 * 
	 * @param jonotusaika ovihenkilöille jonottaneiden asiakkaiden jonotusaikojen
	 *                    keskiarvo
	 */
	public void setAvgOviJono(double jonotusaika);

	/**
	 * Asettaa ilmoittautumistiskien jonotusaikojen keskiarvon
	 * 
	 * @param jonotusaika ilmoittautumistiskeille jonottaneiden asiakkaiden
	 *                    jonotusaikojen keskiarvo
	 */
	public void setAvgIlmoJono(double jonotusaika);

	/**
	 * Asettaa rokottajien jonotusaikojen keskiarvon
	 * 
	 * @param jonotusaika rokottajille jonottaneiden asiakkaiden jonotusaikojen
	 *                    keskiarvo
	 */
	public void setAvgRokJono(double jonotusaika);

	/**
	 * Asettaa jälkiseurannan jonotusaikojen keskiarvon
	 * 
	 * @param jonotusaika jälkiseurantaan jonottaneiden asiakkaiden jonotusaikojen
	 *                    keskiarvo
	 */
	public void setAvgSeurJono(double jonotusaika);

	/**
	 * Palauttaa IVisualisointi-rajapinnan toteuttavan olion
	 * 
	 * @return visualisointi
	 */
	public IVisualisointi getVisualisointi();

}
