package controller;

import java.util.HashMap;

import eduni.distributions.ContinuousGenerator;

/**
 * Sisältää tiedon metodeista, jotka toteutetaan kontrollerissa, ja joita
 * moottori kutsuu.
 * 
 * @author Jenni Javanainen
 */

public interface IKontrolleriMtoV {

	/**
	 * Esittää simulaation loppuajan käyttöliittymässä
	 * 
	 * @param aika simulaation loppuaika
	 */
	public void naytaLoppuaika(double aika);

	/**
	 * Esittää asiakkaiden läpimenoaikojen keskiarvon käyttöliittymässä
	 * 
	 * @param aika asiakkaiden läpimenoaikojen keskiarvo
	 */
	public void naytaAvgLapimeno(double aika);

	/**
	 * Esittää palvelupisteiden käyttöasteet käyttöliittymässä
	 * 
	 * @param palvelupisteet simulaatiossa käytetyt palvelupisteet ja niiden
	 *                       käyttöasteet hajautustauluna
	 */
	public void naytaKayttoaste(HashMap<String, Double> palvelupisteet);

	/**
	 * Esittää palvelupisteiden suoritustehot käyttöliittymässä
	 * 
	 * @param palvelupisteet simulaatiossa käytetyt palvelupisteet ja niiden
	 *                       suoritustehot hajautustauluna
	 */
	public void naytaSuoritusteho(HashMap<String, Double> palvelupisteet);

	/**
	 * Esittää simulaation pienimmän läpimenoajan käyttöliittymässä
	 * 
	 * @param lapimenoaika pienin asiakaskohtainen läpimenoaika
	 */
	public void naytaPieninAsiakas(double lapimenoaika);

	/**
	 * Esittää simulaation suurimman läpimenoajan käyttöliittymässä
	 * 
	 * @param lapimenoaika suurin asiakaskohtainen läpimenoaika
	 */
	public void naytaSuurinAsiakas(double lapimenoaika);

	/**
	 * Esittää pisimmän ovihenkilöiden jonotusajan käyttöliittymässä
	 * 
	 * @param jonotusaika pisin jonotusaika ovihenkilöille
	 */
	public void naytaSuurinOviJono(double jonotusaika);

	/**
	 * Esittää pisimmän ilmoittautumistiskien jonotusajan käyttöliittymässä
	 * 
	 * @param jonotusaika pisin jonotusaika ilmoittautumistiskeille
	 */
	public void naytaSuurinIlmoJono(double jonotusaika);

	/**
	 * Esittää pisimmän rokottajien jonotusajan käyttöliittymässä
	 * 
	 * @param jonotusaika pisin jonotusaika rokottajille
	 */
	public void naytaSuurinRokJono(double jonotusaika);

	/**
	 * Esittää pisimmän jälkiseurannan jonotusajan käyttöliittymässä
	 * 
	 * @param jonotusaika pisin jonotusaika jälkiseurantaan
	 */
	public void naytaSuurinSeurJono(double jonotusaika);

	/**
	 * Esittää palveltujen asiakkaiden lukumäärän käyttöliittymässä
	 * 
	 * @param kpl palvellut asiakkaat
	 */
	public void naytaPalvellutAsiakkaat(int kpl);

	/**
	 * Esittää ovihenkilöiden jonotusaikojen keskiarvon käyttöliittymässä
	 * 
	 * @param jonotusaika ovihenkilöille jonotukseen käytettyjen aikojen keskiarvo
	 */
	public void naytaAvgOviJono(double jonotusaika);

	/**
	 * Esittää ilmoittautumistiskien jonotusaikojen keskiarvon käyttöliittymässä
	 * 
	 * @param jonotusaika ilmoittautumistiskeille jonotukseen käytettyjen aikojen
	 *                    keskiarvo
	 */
	public void naytaAvgIlmoJono(double jonotusaika);

	/**
	 * Esittää rokottajien jonotusaikojen keskiarvon käyttöliittymässä
	 * 
	 * @param jonotusaika rokottajille jonotukseen käytettyjen aikojen keskiarvo
	 */
	public void naytaAvgRokJono(double jonotusaika);

	/**
	 * Esittää jälkiseurannan jonotusaikojen keskiarvon käyttöliittymässä
	 * 
	 * @param jonotusaika jälkiseurannan jonotukseen käytettyjen aikojen keskiarvo
	 */
	public void naytaAvgSeurJono(double jonotusaika);

	/**
	 * Lisää asiakkaan jonoon käyttöliittymän visualisoinnissa
	 * 
	 * @param jono asiakkaan käyttämä jono
	 */
	public void visualisoiAsiakas(int jono);

	/**
	 * Poistaa asiakkaan jonosta käyttöliittymän visualisoinnissa
	 * 
	 * @param jono asiakkaan käyttämä jono
	 */
	public void poistaAsiakas(int poista);

	/**
	 * Palauttaa ovihenkilöiden määrän
	 * 
	 * @return ovihenkilöiden määrä
	 */
	public int getOviMaara();

	/**
	 * Palauttaa ilmoittautumistiskien määrän
	 * 
	 * @return ilmoittautumistiskien määrä
	 */
	public int getIlmoMaara();

	/**
	 * Palauttaa rokottajien määrän
	 * 
	 * @return rokottajien määrä
	 */
	public int getRokMaara();

	/**
	 * Palauttaa ovihenkilöiden kanssa käytettävän satunnaislukugeneraattorin
	 * 
	 * @return satunnaislukugeneraattori
	 */
	public ContinuousGenerator getOviJakauma();

	/**
	 * Palauttaa ilmoittautumistiskien kanssa käytettävän satunnaislukugeneraattorin
	 * 
	 * @return satunnaislukugeneraattori
	 */
	public ContinuousGenerator getIlmoJakauma();

	/**
	 * Palauttaa rokottajien kanssa käytettävän satunnaislukugeneraattorin
	 * 
	 * @return satunnaislukugeneraattori
	 */
	public ContinuousGenerator getRokJakauma();

	/**
	 * Palauttaa jäkiseurannan kanssa käytettävän satunnaislukugeneraattorin
	 * 
	 * @return satunnaislukugeneraattori
	 */
	public ContinuousGenerator getSeurJakauma();

	/**
	 * Palauttaa saapumistapahtuman kanssa käytettävän satunnaislukugeneraattorin
	 * 
	 * @return satunnaislukugeneraattori
	 */
	public ContinuousGenerator getSaapumisjakauma();

}
