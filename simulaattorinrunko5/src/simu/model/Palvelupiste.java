package simu.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import eduni.distributions.ContinuousGenerator;
import simu.framework.Kello;
import simu.framework.Tapahtuma;
import simu.framework.Tapahtumalista;
import simu.framework.Trace;

// TODO:
// Palvelupistekohtaiset toiminnallisuudet, laskutoimitukset (+ tarvittavat muuttujat) ja raportointi koodattava
public class Palvelupiste {

	private LinkedList<Asiakas> jono = new LinkedList<Asiakas>(); // Tietorakennetoteutus

	private ContinuousGenerator generator;
	private Tapahtumalista tapahtumalista;
	private TapahtumanTyyppi skeduloitavanTapahtumanTyyppi;
	private int id;
	private PalvelupisteenTyyppi palvelupisteenTyyppi;
	private double poistumisaika;
	private double jonotuksenAlku;
	private List<Double> palveluajat = new ArrayList<Double>();
	private List<Double> jonotusajat = new ArrayList<Double>();
	private List<Double> vasteajat = new ArrayList<Double>();
	private int asiakkaat = 0;
	private double jonotusaika;

	

	// JonoStartegia strategia; //optio: asiakkaiden järjestys

	private boolean varattu = false;

	public Palvelupiste(ContinuousGenerator generator, Tapahtumalista tapahtumalista, TapahtumanTyyppi tapahtumanTyyppi,
			int id, PalvelupisteenTyyppi palvelupisteenTyyppi) {
		this.tapahtumalista = tapahtumalista;
		this.generator = generator;
		this.skeduloitavanTapahtumanTyyppi = tapahtumanTyyppi;
		this.id = id;
		this.palvelupisteenTyyppi = palvelupisteenTyyppi;

	}

	public int jononKoko() {
		if(jono.size() <= 0) {
			return 0;
		}
		
		return jono.size();

	}

	public void lisaaJonoon(Asiakas a) { // Jonon 1. asiakas aina palvelussa
		jono.add(a);
		jonotuksenAlku = (Kello.getInstance().getAika());
		
	}
	
	
	public void lisaaPoistumisAika(Tapahtuma t) {
		poistumisaika = t.getAika();
	}
	
	public double getPoistumisAika() {
		return poistumisaika;
	}
	
	public Asiakas otaJonosta() { // Poistetaan palvelussa ollut
		varattu = false;
		return jono.poll();
	}
	
	/*public double tallennaJonotus() {
		jonotusaika = Kello.getInstance().getAika()-jonotuksenAlku;
		return jonotusaika;
	}*/

	public void aloitaPalvelu() { // Aloitetaan uusi palvelu, asiakas on jonossa palvelun aikana

		Trace.out(Trace.Level.INFO, "Aloitetaan uusi palvelu asiakkaalle " + jono.peek().getId() + " palvelupisteessä " + palvelupisteenTyyppi + id);

		varattu = true;
		double palveluaika = generator.sample();
		poistumisaika = Kello.getInstance().getAika() + palveluaika;
		jonotusaika = Kello.getInstance().getAika() - jonotuksenAlku;
		
		tapahtumalista.lisaa(new Tapahtuma(skeduloitavanTapahtumanTyyppi, poistumisaika));
		palveluajat.add(palveluaika);
		asiakkaat++;
		
	}
	
	
	public double getJonotusaika() {
		return jonotusaika;
	}
	
	
	public boolean onVarattu() {
		return varattu;
	}

	public boolean onJonossa() {
		return jono.size() != 0;
	}
	
	public double palvelutYht() {
		double summa = 0;
	    for (int i = 0; i < palveluajat.size(); i++) {
	        double aika = palveluajat.get(i);
	        summa += aika;
	    }
	    return summa;
	}
	
	public double kayttoaste() {
		double kayttoaste = palvelutYht()/Kello.getInstance().getAika();
		return kayttoaste;	
	}
	
	public double suoritusteho() {
		double suoritusteho = Kello.getInstance().getAika()/asiakkaat;
		return suoritusteho;
	}
	
	
	public String haeNimi() {
		String nimi = this.id + " " + this.palvelupisteenTyyppi;
		return nimi;
	}


	/*public void vasteaikaTestaus() {
		double palv;
		double jono;
		double vast = 0;
		
		for (int i = 0; i < palveluajat.size(); i++) {
			for (i = 0; i < palveluajat.size(); i++) {
				palv = palveluajat.get(i);
				vast =+ palv;
				for (i = 0; i < jonotusajat.size(); i++) {
					jono = jonotusajat.get(i);
					vast=+ jono;
					}
				}
			vasteajat.add(vast);
		}*/	

}
