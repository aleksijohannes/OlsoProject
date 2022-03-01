package simu.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import controller.IKontrolleriMtoV;
import eduni.distributions.Negexp;
import eduni.distributions.Normal;
import simu.framework.Kello;
import simu.framework.Moottori;
import simu.framework.Saapumisprosessi;
import simu.framework.Tapahtuma;

public class OmaMoottori extends Moottori {

	private Saapumisprosessi saapumisprosessi;
	private Palvelupiste[] ovihenkilot;
	private Palvelupiste[] ilmoittautumistiskit;
	private Palvelupiste[] rokottajat;
	private Palvelupiste[] jalkiseurannat;
	
	private List<Double> lapimenoajat = new ArrayList<Double>();
	
	private int ovihenkiloMaara;
	private int ilmotiskiMaara;
	private int rokottajaMaara;
	private int seurantaMaara;
	
	private HashMap<String, Double> suoritustehot = new HashMap<String, Double>();
	private HashMap<String, Double> kayttoasteet = new HashMap<String, Double>();
	
	// Muut palvelupisteiden parametrimuuttujat lisätään myöhemmin

	public OmaMoottori(IKontrolleriMtoV kontrolleri) {
		
		super(kontrolleri); 
		ovihenkiloMaara = kontrolleri.getOviMaara();
		ilmotiskiMaara = kontrolleri.getIlmoMaara();
		rokottajaMaara = kontrolleri.getRokMaara();
		seurantaMaara = 1;
		
		ovihenkilot = new Palvelupiste[ovihenkiloMaara];
		ilmoittautumistiskit = new Palvelupiste[ilmotiskiMaara];
		rokottajat = new Palvelupiste[rokottajaMaara];
		jalkiseurannat = new Palvelupiste[seurantaMaara];
		
		// Erityyppisten palvelupisteiden luonti
		
		for (int i = 0; i < ovihenkiloMaara; i++) {
			ovihenkilot[i] = new Palvelupiste(kontrolleri.getOviJakauma(), tapahtumalista, TapahtumanTyyppi.DEP1, i + 1,
					PalvelupisteenTyyppi.OVI);
		}
		
		for (int i = 0; i < ilmotiskiMaara; i++) {
			ilmoittautumistiskit[i] = new Palvelupiste(kontrolleri.getIlmoJakauma(), tapahtumalista, TapahtumanTyyppi.DEP2, i +1,
					PalvelupisteenTyyppi.ILMO);
		}
		
		for (int i = 0; i < rokottajaMaara; i++) {
			rokottajat[i] = new Palvelupiste(kontrolleri.getRokJakauma(), tapahtumalista, TapahtumanTyyppi.DEP3, i + 1,
					PalvelupisteenTyyppi.ROK);
		}
		
		for (int i = 0; i < seurantaMaara; i++) {
			jalkiseurannat[i] = new Palvelupiste(kontrolleri.getSeurJakauma(), tapahtumalista, TapahtumanTyyppi.DEP4,  i + 1,
					PalvelupisteenTyyppi.SEUR);
		}

		saapumisprosessi = new Saapumisprosessi(new Negexp(15, 5), tapahtumalista, TapahtumanTyyppi.ARR1);

	}

	@Override
	protected void alustukset() {
		saapumisprosessi.generoiSeuraava(); // Ensimmäinen saapuminen järjestelmään
	}

	@Override
	protected void suoritaTapahtuma(Tapahtuma t) { // B-vaiheen tapahtumat

		Asiakas a;
		switch (t.getTyyppi()) {

		case ARR1:
			pieninJono(ovihenkilot).lisaaJonoon(new Asiakas());
			saapumisprosessi.generoiSeuraava();
			break;
		case DEP1:
			a = etsiValmis(ovihenkilot, t).otaJonosta();
			pieninJono(ilmoittautumistiskit).lisaaJonoon(a);
			break;
		case DEP2:
			a = etsiValmis(ilmoittautumistiskit, t).otaJonosta();
			pieninJono(rokottajat).lisaaJonoon(a);
			break;
		case DEP3:
			a = etsiValmis(rokottajat, t).otaJonosta();
			pieninJono(jalkiseurannat).lisaaJonoon(a);
			break;
		case DEP4:
			a = etsiValmis(jalkiseurannat, t).otaJonosta();
			a.setPoistumisaika(Kello.getInstance().getAika());
			a.raportti();
		}
	}
	
	protected Palvelupiste etsiValmis(Palvelupiste[] taulukko, Tapahtuma t) {
		for (int i = 0; i < taulukko.length; i++) {
			if (taulukko[i].getPoistumisAika() == t.getAika()) {
				return taulukko[i];
			}
				
		}
		return null;
	}
	
	@Override
	protected void suoritaBTapahtumat(){
		while (tapahtumalista.getSeuraavanAika() == Kello.getInstance().getAika()){
			suoritaTapahtuma(tapahtumalista.poista());
		}
	}

	@Override
	protected void yritaCTapahtumat(){
		for (Palvelupiste p: ovihenkilot){
			if (!p.onVarattu() && p.onJonossa()){
				p.aloitaPalvelu();
			}
		}
		
		for (Palvelupiste p: ilmoittautumistiskit){
			if (!p.onVarattu() && p.onJonossa()){
				p.aloitaPalvelu();
			}
		}
		
		for (Palvelupiste p: rokottajat){
			if (!p.onVarattu() && p.onJonossa()){
				p.aloitaPalvelu();
			}
		}
		
		for (Palvelupiste p: jalkiseurannat){
			if (!p.onVarattu() && p.onJonossa()){
				p.aloitaPalvelu();
			}
		}
	}

	protected Palvelupiste pieninJono(Palvelupiste[] taulukko) {
		Palvelupiste pienin = taulukko[0];
		for (int i = 0; i < taulukko.length; i++) {
			if (taulukko[i].jononKoko() < pienin.jononKoko()) {
				pienin = taulukko[i];
			}
		}
		return pienin;
	}
	
	protected double avgLapimeno() {
		double avg = lapimenoajat.stream().mapToDouble(Double::doubleValue).sum() / lapimenoajat.size();
		return avg;
	}
	
	public void laskeSuoritustehot() {
		for (int i = 0; i < ovihenkiloMaara; i++) {
			suoritustehot.put(ovihenkilot[i].haeNimi(), ovihenkilot[i].suoritusteho());
			}
		for (int i = 0; i < ilmotiskiMaara; i++) {
			suoritustehot.put(ilmoittautumistiskit[i].haeNimi(), ilmoittautumistiskit[i].suoritusteho());
			}
		for (int i = 0; i < rokottajaMaara; i++) {
			suoritustehot.put(rokottajat[i].haeNimi(), rokottajat[i].suoritusteho()); 
			}
		for (int i = 0; i < seurantaMaara; i++) {
			suoritustehot.put(jalkiseurannat[i].haeNimi(), jalkiseurannat[i].suoritusteho());
			}	
	}
	
	
	public HashMap getSuoritustehot() {
		return suoritustehot;
	}
	
	
	public void laskeKayttoasteet() {
		for (int i = 0; i < ovihenkiloMaara; i++) {
			kayttoasteet.put(ovihenkilot[i].haeNimi(), ovihenkilot[i].kayttoaste());
			}
		for (int i = 0; i < ilmotiskiMaara; i++) {
			kayttoasteet.put(ilmoittautumistiskit[i].haeNimi(), ilmoittautumistiskit[i].kayttoaste());
			}
		for (int i = 0; i < rokottajaMaara; i++) {
			kayttoasteet.put(rokottajat[i].haeNimi(), rokottajat[i].kayttoaste()); 
			}
		for (int i = 0; i < seurantaMaara; i++) {
			kayttoasteet.put(jalkiseurannat[i].haeNimi(), jalkiseurannat[i].kayttoaste());
			}	
	}
	
	//tekstiä lalalalalalalalalalala
	
	public HashMap getKayttoasteet() {
		return kayttoasteet;
	}
	
	
	
	@Override
	protected void tulokset() {
		laskeSuoritustehot();
		laskeKayttoasteet();
		kontrolleri.naytaLoppuaika(Kello.getInstance().getAika());
		lapimenoajat.add(Kello.getInstance().getAika());
	}
	


	
	
}
