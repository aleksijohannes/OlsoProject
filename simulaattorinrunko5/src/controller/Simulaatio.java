package controller;

import java.util.Date;
import java.util.HashMap;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Simulaatio-olio sisältää yhtä simulaatio-ajoa koskettavat tiedot. Simulaatiolla on oliomuuttujina päiväys, kaikki ajoparametrit, sekä
 * simulaation tulokset.
 * 
 * @author Jenni Javanainen
 *
 */

@Entity
@Table(name="SIMULAATIO")
public class Simulaatio {
	@Id
	@GeneratedValue
	private int nro;

	private Date aika;
	
	// Ajoparametrit
	private int oviMaara;
	private int oviNopeus;
	private String oviJakauma;

	private int ilmoMaara;
	private int ilmoNopeus;
	private String ilmoJakauma;
	
	private int rokMaara;
	private int rokNopeus;
	private String rokJakauma;
	
	private int seurNopeus;
	private String seurJakauma;
	
	private int saapNopeus;
	private String saapJakauma;
	
	// Tulokset
	private double loppuaika;
	private double avgLapimeno;
	private HashMap<String, Double> kayttoasteet; 
	private HashMap<String, Double> tehot;
	private double pieninAsiakas;
	private double suurinAsiakas;
	private double suurinOviJono;
	private double suurinIlmoJono;
	private double suurinRokJono;
	private double suurinSeurJono;
	private int palvellutAsiakkaat;
	private double avgOvijono;
	private double avgIlmoJono;
	private double avgRokJono;
	private double avgSeurJono;
	
	
	
	public Simulaatio() {
		this.aika = new Date(System.currentTimeMillis());
	}
	
	// Getterit ja setterit
	
	public Date getAika() {
		return aika;
	}

	public void setAika(Date aika) {
		this.aika = aika;
	}

	public int getOviMaara() {
		return oviMaara;
	}

	public void setOviMaara(int oviMaara) {
		this.oviMaara = oviMaara;
	}

	public int getOviNopeus() {
		return oviNopeus;
	}

	public void setOviNopeus(int oviNopeus) {
		this.oviNopeus = oviNopeus;
	}

	public String getOviJakauma() {
		return oviJakauma;
	}

	public void setOviJakauma(String oviJakauma) {
		this.oviJakauma = oviJakauma;
	}

	public int getIlmoMaara() {
		return ilmoMaara;
	}

	public void setIlmoMaara(int ilmoMaara) {
		this.ilmoMaara = ilmoMaara;
	}

	public int getIlmoNopeus() {
		return ilmoNopeus;
	}

	public void setIlmoNopeus(int ilmoNopeus) {
		this.ilmoNopeus = ilmoNopeus;
	}

	public String getIlmoJakauma() {
		return ilmoJakauma;
	}

	public void setIlmoJakauma(String ilmoJakauma) {
		this.ilmoJakauma = ilmoJakauma;
	}

	public int getRokMaara() {
		return rokMaara;
	}

	public void setRokMaara(int rokMaara) {
		this.rokMaara = rokMaara;
	}

	public int getRokNopeus() {
		return rokNopeus;
	}

	public void setRokNopeus(int rokNopeus) {
		this.rokNopeus = rokNopeus;
	}

	public String getRokJakauma() {
		return rokJakauma;
	}

	public void setRokJakauma(String rokJakauma) {
		this.rokJakauma = rokJakauma;
	}

	public int getSeurNopeus() {
		return seurNopeus;
	}

	public void setSeurNopeus(int seurNopeus) {
		this.seurNopeus = seurNopeus;
	}

	public String getSeurJakauma() {
		return seurJakauma;
	}

	public void setSeurJakauma(String seurJakauma) {
		this.seurJakauma = seurJakauma;
	}

	public int getSaapNopeus() {
		return saapNopeus;
	}

	public void setSaapNopeus(int saapNopeus) {
		this.saapNopeus = saapNopeus;
	}

	public String getSaapJakauma() {
		return saapJakauma;
	}

	public void setSaapJakauma(String saapJakauma) {
		this.saapJakauma = saapJakauma;
	}

	public double getLoppuaika() {
		return loppuaika;
	}

	public void setLoppuaika(double loppuaika) {
		this.loppuaika = loppuaika;
	}

	public double getAvgLapimeno() {
		return avgLapimeno;
	}

	public void setAvgLapimeno(double avgLapimeno) {
		this.avgLapimeno = avgLapimeno;
	}

	public HashMap<String, Double> getTehot() {
		return tehot;
	}

	public void setTehot(HashMap<String, Double> tehot) {
		this.tehot = tehot;
	}

	public HashMap<String, Double> getKayttoasteet() {
		return kayttoasteet;
	}

	public void setKayttoasteet(HashMap<String, Double> kayttoasteet) {
		this.kayttoasteet = kayttoasteet;
	}

	public double getPieninAsiakas() {
		return pieninAsiakas;
	}

	public void setPieninAsiakas(double pieninAsiakas) {
		this.pieninAsiakas = pieninAsiakas;
	}

	public double getSuurinSeurJono() {
		return suurinSeurJono;
	}

	public void setSuurinSeurJono(double suurinSeurJono) {
		this.suurinSeurJono = suurinSeurJono;
	}

	public double getSuurinAsiakas() {
		return suurinAsiakas;
	}

	public void setSuurinAsiakas(double suurinAsiakas) {
		this.suurinAsiakas = suurinAsiakas;
	}

	public double getSuurinOviJono() {
		return suurinOviJono;
	}

	public void setSuurinOviJono(double suurinOviJono) {
		this.suurinOviJono = suurinOviJono;
	}

	public double getSuurinIlmoJono() {
		return suurinIlmoJono;
	}

	public void setSuurinIlmoJono(double suurinIlmoJono) {
		this.suurinIlmoJono = suurinIlmoJono;
	}

	public double getSuurinRokJono() {
		return suurinRokJono;
	}

	public void setSuurinRokJono(double suurinRokJono) {
		this.suurinRokJono = suurinRokJono;
	}

	public int getPalvellutAsiakkaat() {
		return palvellutAsiakkaat;
	}

	public void setPalvellutAsiakkaat(int palvellutAsiakkaat) {
		this.palvellutAsiakkaat = palvellutAsiakkaat;
	}

	public double getAvgOvijono() {
		return avgOvijono;
	}

	public void setAvgOvijono(double avgOvijono) {
		this.avgOvijono = avgOvijono;
	}

	public double getAvgIlmoJono() {
		return avgIlmoJono;
	}

	public void setAvgIlmoJono(double avgIlmoJono) {
		this.avgIlmoJono = avgIlmoJono;
	}

	public double getAvgRokJono() {
		return avgRokJono;
	}

	public void setAvgRokJono(double avgRokJono) {
		this.avgRokJono = avgRokJono;
	}

	public double getAvgSeurJono() {
		return avgSeurJono;
	}

	public void setAvgSeurJono(double avgSeurJono) {
		this.avgSeurJono = avgSeurJono;
	}



}
