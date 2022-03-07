package simu.model;

import java.util.HashMap;

import simu.framework.IMoottori;

/**
 * Rajapinta sisältää listan metodeista, jotka toteutetaan OmaMoottorissa, ja joita kontrolleri kutsuu.
 * 
 * @author Jenni Javanainen
 *
 */
public interface IOmaMoottori extends IMoottori {
	public double getLoppuaika();
	public double getAvgLapimeno();
	public int getAsiakasmaara();
	public double getPieninAsiakas();
	public double getSuurinAsiakas();
	public HashMap<String, Double> getKayttoasteet();
	public HashMap<String, Double> getSuoritustehot();
	public double getSuurinOviJono();
	public double getSuurinIlmoJono();
	public double getSuurinRokJono();
	public double getSuurinSeurJono();
	public double getOviKeskiarvo();
	public double getIlmoKeskiarvo();
	public double getRokKeskiarvo();
	public double getSeurKeskiarvo();

}
