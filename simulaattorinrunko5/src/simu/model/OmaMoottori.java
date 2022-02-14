package simu.model;

import java.util.ArrayList;
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
	
	private int ovihenkiloMaara = 1;
	
	private int ilmotiskiMaara = 2;
	
	private int rokottajaMaara = 4;
	
	private int seurantaMaara = 1;
	
	// Muut palvelupisteiden parametrimuuttujat lisätään myöhemmin

	public OmaMoottori(IKontrolleriMtoV kontrolleri) {
		
		super(kontrolleri); 
		
		ovihenkilot = new Palvelupiste[ovihenkiloMaara];
		ilmoittautumistiskit = new Palvelupiste[ilmotiskiMaara];
		rokottajat = new Palvelupiste[rokottajaMaara];
		jalkiseurannat = new Palvelupiste[seurantaMaara];
		
		// Erityyppisten palvelupisteiden luonti
		
		for (int i = 0; i < ovihenkiloMaara; i++) {
			ovihenkilot[i] = new Palvelupiste(new Normal(3, 2), tapahtumalista, TapahtumanTyyppi.DEP1, i + 1,
					PalvelupisteenTyyppi.OVI);
		}
		
		for (int i = 0; i < ilmotiskiMaara; i++) {
			ilmoittautumistiskit[i] = new Palvelupiste(new Normal(5, 3), tapahtumalista, TapahtumanTyyppi.DEP2, i +1,
					PalvelupisteenTyyppi.ILMO);
		}
		
		for (int i = 0; i < rokottajaMaara; i++) {
			rokottajat[i] = new Palvelupiste(new Normal(10, 6), tapahtumalista, TapahtumanTyyppi.DEP3, i + 1,
					PalvelupisteenTyyppi.ROK);
		}
		
		for (int i = 0; i < seurantaMaara; i++) {
			jalkiseurannat[i] = new Palvelupiste(new Normal(15, 1), tapahtumalista, TapahtumanTyyppi.DEP4,  i + 1,
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
	
	@Override
	protected void tulokset() {
		kontrolleri.naytaLoppuaika(Kello.getInstance().getAika());
		lapimenoajat.add(Kello.getInstance().getAika());
	}
	


	
	
}
