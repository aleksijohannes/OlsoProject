package simu.model;

import java.util.ArrayList;
import java.util.List;

import simu.framework.Kello;
import simu.framework.Trace;


/**
 * Luokka, joka määrittää simulaattorin läpi kulkevan asiakkaan 
 *
 * @author Jenni Tynkkynen
 */

public class Asiakas {
	private double saapumisaika;
	private double poistumisaika;
	private double lapimenoaika;
	private int id;
	private static int i = 1;
	private static long sum = 0;
	
	public Asiakas(){
	    id = i++;
	    
		saapumisaika = Kello.getInstance().getAika();
		Trace.out(Trace.Level.INFO, "Uusi asiakas nro " + id + " saapui klo "+saapumisaika);
	}

	public double getPoistumisaika() {
		return poistumisaika;
	}

	public void setPoistumisaika(double poistumisaika) {
		this.poistumisaika = poistumisaika;
	}

	public double getSaapumisaika() {
		return saapumisaika;
	}

	public void setSaapumisaika(double saapumisaika) {
		this.saapumisaika = saapumisaika;
	}
	
	public int getId() {
		return id;
	}
	
	/**
     * Kutsutaan, kun halutaan palauttaa asiakkaan id-numero String-muodossa
     * @return idString String, palauttaa asiakkaan id:n String-muodossa
     */
	
	public String asiakasId() {
		String idString = Integer.toString(id);
		return idString;
	}
	
	/**
     * Tallentaa asiakkaan läpimenoajan vähentämällä asiakakaan poistumisajan asiakkaan saapumisajasta
     * @return lapimenoaika int, palauttaa asiakkaan läpmenoajan
     */
	
	public double asiakkaanLapimeno() {
		lapimenoaika = poistumisaika-saapumisaika;
		return lapimenoaika;
	}
	
	/**
     * Asiakasraportti asiakkaan toimista, kutsutaan kun asiakas on mennyt yhden palvelupiseen läpi:
     * Trace.out kirjoittaa konsoliin yhden asiakkaan toiminnot aina, kun raporttia kutsutaan.
     * 
     * Raportti ilmoittaa asiakkaan saapumis-, poistumis- ja läpimenoajan ja laskee kaikkien 
     * palvelupisteistä läpi menneiden asiakkaiden keskiarvoisen läpimenoajan.
     * 
     */
	
	public void raportti(){
		Trace.out(Trace.Level.INFO, "\nAsiakas "+id+ " valmis! ");
		Trace.out(Trace.Level.INFO, "Asiakas "+id+ " saapui: " +saapumisaika);
		Trace.out(Trace.Level.INFO,"Asiakas "+id+ " poistui: " +poistumisaika);
		Trace.out(Trace.Level.INFO,"Asiakas "+id+ " viipyi: " +(poistumisaika-saapumisaika));
		sum += (poistumisaika-saapumisaika);
		double keskiarvo = sum/id;
		System.out.println("Asiakkaiden läpimenoaikojen keskiarvo tähän asti "+ keskiarvo);
	}

}
