package controller;

import java.util.Date;
import java.util.HashMap;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 
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
	
	
	
	
	public Simulaatio() {
		this.aika = new Date(System.currentTimeMillis());
	}
	
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



}
