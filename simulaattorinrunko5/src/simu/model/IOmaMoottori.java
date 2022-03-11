package simu.model;

import java.util.HashMap;

import simu.framework.IMoottori;

/**
 * Rajapinta sisältää listan metodeista, jotka toteutetaan OmaMoottorissa, ja
 * joita kontrolleri kutsuu.
 * 
 * @author Jenni Javanainen
 *
 */
public interface IOmaMoottori {

	/**
	 * Asettaa simuloinnin keston
	 * 
	 * @param aika simulointiaika
	 */
	public void setSimulointiaika(double aika);

	/**
	 * Asettaa simuloinnissa käytettävän viiveen
	 * 
	 * @param aika viive
	 */
	public void setViive(long aika);

	/**
	 * Palauttaa simuloinnissa käytettävän viiveen
	 * 
	 * @return viive
	 */
	public long getViive();

	/**
	 * Palauttaa simuloinnin loppuajan
	 * 
	 * @return loppuaika
	 */
	public double getLoppuaika();

	/**
	 * Palauttaa asiakkaiden läpimenoaikojen keskiarvon
	 * 
	 * @return simulaation läpi kulkeneiden asiakkaiden läpimenoaikojen ekskiarvo
	 */
	public double getAvgLapimeno();

	/**
	 * Palauttaa kokonaan palveltujen asiakkaiden lukumäärän
	 * 
	 * @return palveltujen asiakkaiden määrä
	 */
	public int getAsiakasmaara();

	/**
	 * Palauttaa asikkaiden pienimmän läpimenoajan
	 * 
	 * @return lyhyin asiakkaan kokonaisläpimenoaika simulaation läpi
	 */
	public double getPieninAsiakas();

	/**
	 * Palauttaa asikkaiden suurimman läpimenoajan
	 * 
	 * @return pisin asiakkaan kokonaisläpimenoaika simulaation läpi
	 */
	public double getSuurinAsiakas();

	/**
	 * Palauttaa palvelupisteiden käyttöasteet
	 * 
	 * @return simulaatiossa käytettyjen palvelupisteiden nimet ja käyttöasteet
	 *         hajatustauluna
	 */
	public HashMap<String, Double> getKayttoasteet();

	/**
	 * Palauttaa palvelupisteiden suoritustehot
	 * 
	 * @return simulaatiossa käytettyjen palvelupisteiden nimet ja suoritustehot
	 *         hajatustauluna
	 */
	public HashMap<String, Double> getSuoritustehot();

	/**
	 * Palauttaa suurimman jonotusajan ovihenkilöille
	 * 
	 * @return pisin jonotusaika ovihenkilöille
	 */
	public double getSuurinOviJono();

	/**
	 * Palauttaa suurimman jonotusajan ilmoittautumistiskeille
	 * 
	 * @return pisin jonotusaika ilmoittautumistiskeille
	 */
	public double getSuurinIlmoJono();

	/**
	 * Palauttaa suurimman jonotusajan rokottajille
	 * 
	 * @return pisin jonotusaika rokottajille
	 */
	public double getSuurinRokJono();

	/**
	 * Palauttaa suurimman jonotusajan seurantaan
	 * 
	 * @return pisin jonotusaika seurantaan
	 */
	public double getSuurinSeurJono();

	/**
	 * Palauttaa keskiarvoisen jonotusajan ovihenkilöille
	 * 
	 * @return keskiarvoinen jonotusaika ovihenkilöille
	 */
	public double getOviKeskiarvo();

	/**
	 * Palauttaa keskiarvoisen jonotusajan ilmoittautumistiskeille
	 * 
	 * @return keskiarvoinen jonotusaika ilmoittautumistiskeille
	 */
	public double getIlmoKeskiarvo();

	/**
	 * Palauttaa keskiarvoisen jonotusajan rokottajille
	 * 
	 * @return keskiarvoinen jonotusaika rokottajille
	 */
	public double getRokKeskiarvo();

	/**
	 * Palauttaa keskiarvoisen jonotusajan jälkiseurantaan
	 * 
	 * @return keskiarvoinen jonotusaika jälkiseurantaan
	 */
	public double getSeurKeskiarvo();

}
