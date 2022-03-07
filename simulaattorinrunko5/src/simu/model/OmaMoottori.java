package simu.model;

import java.util.ArrayList;
import java.util.Collections;
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
	private List<Double> asiakkaidenLapimenoajat = new ArrayList<Double>();

	private int ovihenkiloMaara;
	private int ilmotiskiMaara;
	private int rokottajaMaara;
	private int seurantaMaara;

	private List<Double> oviJonotukset = new ArrayList<Double>();
	private List<Double> ilmoJonotukset = new ArrayList<Double>();
	private List<Double> rokJonotukset = new ArrayList<Double>();
	private List<Double> seurJonotukset = new ArrayList<Double>();

	private double jonotusaika;

	private double oviPieninJono;
	private double oviSuurinJono;
	private double oviKeskiarvo;
	private double ilmoPieninJono;
	private double ilmoSuurinJono;
	private double ilmoKeskiarvo;
	private double rokPieninJono;
	private double rokSuurinJono;
	private double rokKeskiarvo;
	private double seurPieninJono;
	private double seurSuurinJono;
	private double seurKeskiarvo;

	private HashMap<String, Double> suoritustehot = new HashMap<String, Double>();
	private HashMap<String, Double> kayttoasteet = new HashMap<String, Double>();

	// Muut palvelupisteiden parametrimuuttujat lisätään myöhemmin

	public OmaMoottori(IKontrolleriMtoV kontrolleri) {

		super(kontrolleri);
		ovihenkiloMaara = kontrolleri.getOviMaara();
		System.out.println("ovihenkilömäärä: " + ovihenkiloMaara);
		ilmotiskiMaara = kontrolleri.getIlmoMaara();
		rokottajaMaara = kontrolleri.getRokMaara();
		seurantaMaara = 1;

		ovihenkilot = new Palvelupiste[ovihenkiloMaara];
		ilmoittautumistiskit = new Palvelupiste[ilmotiskiMaara];
		rokottajat = new Palvelupiste[rokottajaMaara];
		jalkiseurannat = new Palvelupiste[seurantaMaara];

		// Erityyppisten palvelupisteiden luonti

		for (int i = 0; i < ovihenkiloMaara; i++) {
			ovihenkilot[i] = new Palvelupiste(kontrolleri.getOviJakauma(), tapahtumalista, TapahtumanTyyppi.DEP1, i ,
					PalvelupisteenTyyppi.OVI);
		}

		for (int i = 0; i < ilmotiskiMaara; i++) {
			ilmoittautumistiskit[i] = new Palvelupiste(kontrolleri.getIlmoJakauma(), tapahtumalista,
					TapahtumanTyyppi.DEP2, i , PalvelupisteenTyyppi.ILMO);
		}

		for (int i = 0; i < rokottajaMaara; i++) {
			rokottajat[i] = new Palvelupiste(kontrolleri.getRokJakauma(), tapahtumalista, TapahtumanTyyppi.DEP3, i ,
					PalvelupisteenTyyppi.ROK);
		}

		for (int i = 0; i < seurantaMaara; i++) {
			jalkiseurannat[i] = new Palvelupiste(kontrolleri.getSeurJakauma(), tapahtumalista, TapahtumanTyyppi.DEP4,
					i , PalvelupisteenTyyppi.SEUR);
		}

		saapumisprosessi = new Saapumisprosessi(kontrolleri.getSaapumisjakauma(), tapahtumalista, TapahtumanTyyppi.ARR1);
		
		
		
		System.out.println(ilmoittautumistiskit);
		
		for (int i = 0; i < rokottajaMaara; i++) {
			System.out.println("rokottaja: " + rokottajat[i].haeNimi());
		}
		
		System.out.println(ovihenkilot);
		System.out.println(jalkiseurannat);
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
			jonotusaika = etsiValmis(ovihenkilot, t).tallennaJonotus();
			a = etsiValmis(ovihenkilot, t).otaJonosta();
			oviJonotukset.add(jonotusaika);
			pieninJono(ilmoittautumistiskit).lisaaJonoon(a);
			break;
		case DEP2:
			jonotusaika = etsiValmis(ilmoittautumistiskit, t).tallennaJonotus();
			a = etsiValmis(ilmoittautumistiskit, t).otaJonosta();
			ilmoJonotukset.add(jonotusaika);
			pieninJono(rokottajat).lisaaJonoon(a);
			break;
		case DEP3:
			jonotusaika = etsiValmis(rokottajat, t).tallennaJonotus();
			a = etsiValmis(rokottajat, t).otaJonosta();
			rokJonotukset.add(jonotusaika);
			pieninJono(jalkiseurannat).lisaaJonoon(a);
			break;
		case DEP4:
			//jonotusaika = etsiValmis(jalkiseurannat, t).tallennaJonotus();
			a = etsiValmis(jalkiseurannat, t).otaJonosta();
			//seurJonotukset.add(jonotusaika);
			a.setPoistumisaika(Kello.getInstance().getAika());
			asiakkaidenLapimenoajat.add(a.asiakkaanLapimeno());
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
	protected void suoritaBTapahtumat() {
		while (tapahtumalista.getSeuraavanAika() == Kello.getInstance().getAika()) {
			suoritaTapahtuma(tapahtumalista.poista());
		}
	}

	@Override
	protected void yritaCTapahtumat() {
		for (Palvelupiste p : ovihenkilot) {
			if (!p.onVarattu() && p.onJonossa()) {
				p.aloitaPalvelu();
			}
		}

		for (Palvelupiste p : ilmoittautumistiskit) {
			if (!p.onVarattu() && p.onJonossa()) {
				p.aloitaPalvelu();
			}
		}

		for (Palvelupiste p : rokottajat) {
			if (!p.onVarattu() && p.onJonossa()) {
				p.aloitaPalvelu();
			}
		}

		for (Palvelupiste p : jalkiseurannat) {
			if (!p.onVarattu() && p.onJonossa()) {
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

	public HashMap<String, Double>getSuoritustehot() {
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

	public HashMap<String, Double> getKayttoasteet() {
		return kayttoasteet;
	}
	
	protected double pieninAsiakas() {
		Collections.sort(asiakkaidenLapimenoajat);
		return asiakkaidenLapimenoajat.get(0);
	}
	
	protected double suurinAsiakas() {
		Collections.sort(asiakkaidenLapimenoajat);
		return asiakkaidenLapimenoajat.get(asiakkaidenLapimenoajat.size() - 1);
	}

	protected void sorttaaJonot() {
		Collections.sort(oviJonotukset);
		Collections.sort(ilmoJonotukset);
		Collections.sort(rokJonotukset);
		Collections.sort(seurJonotukset);

		oviPieninJono = oviJonotukset.get(0);
		oviSuurinJono = oviJonotukset.get(oviJonotukset.size() - 1);
		
		System.out.println("Min ovi: " + oviPieninJono);
		System.out.println("Max ovi: " + oviSuurinJono);

		ilmoPieninJono = ilmoJonotukset.get(0);
		ilmoSuurinJono = ilmoJonotukset.get(ilmoJonotukset.size() - 1);
		System.out.println("Min ilmo: " + ilmoPieninJono);
		System.out.println("Max ilmo: " + ilmoJonotukset.get(ilmoJonotukset.size() - 1));

		rokPieninJono = rokJonotukset.get(0);
		rokSuurinJono = rokJonotukset.get(rokJonotukset.size() - 1);
		System.out.println("Min rok: " + rokJonotukset.get(0));
		System.out.println("Max rok: " + rokJonotukset.get(rokJonotukset.size() - 1));

		if (seurJonotukset.isEmpty()) {
			System.out.println("Seurannassa ei jonoa");
			
		}
		else {
			seurPieninJono = seurJonotukset.get(0);
			seurSuurinJono = seurJonotukset.get(seurJonotukset.size() - 1);
			System.out.println("Min seur: " + seurJonotukset.get(0));
			System.out.println("Max seur: " + seurJonotukset.get(seurJonotukset.size() - 1));	
		}
	}
	
	protected void laskeJonojenKeskiarvot() {
		double oviSumma = 0;
		double ilmoSumma = 0;
		double rokSumma = 0;
		double seurSumma = 0;
		
		for (int i = 0; i < oviJonotukset.size(); i++) {
			oviSumma += oviJonotukset.get(i);
		}
		oviKeskiarvo = oviSumma / oviJonotukset.size();
		System.out.println("Avg ovi: " + oviKeskiarvo);
		
		
		for (int i = 0; i < ilmoJonotukset.size(); i++) {
			ilmoSumma += ilmoJonotukset.get(i);
		}
		ilmoKeskiarvo = ilmoSumma / ilmoJonotukset.size();
		System.out.println("Avg ilmo: " + ilmoKeskiarvo);
		
		
		for (int i = 0; i < rokJonotukset.size(); i++) {
			rokSumma += rokJonotukset.get(i);
		}
		rokKeskiarvo = rokSumma / rokJonotukset.size();
		System.out.println("Avg rok: " + rokKeskiarvo);
		
		
		if (seurJonotukset.isEmpty()) {
			System.out.println("Seurannassa ei jonoa");
		}
		else {
			for (int i = 0; i < seurJonotukset.size(); i++) {
			seurSumma += seurJonotukset.get(i);
		}
			seurKeskiarvo = seurSumma / seurJonotukset.size();
			System.out.println("Avg seur: " + seurKeskiarvo);
		}	
	}

	
	
	// Pienimmät jonot:
	
	protected double getPieninOviJono() {
		return oviPieninJono;
	}
	
	protected double getPieninIlmoJono() {
		return ilmoPieninJono;
	}
	
	protected double getPieninRokJono() {
		return rokPieninJono;
	}
	
	protected double getPieninSeurJono() {
		return seurPieninJono;
	}
	
	
	// Suurimmat jonot:
	
	protected double getSuurinOviJono() {
		return oviSuurinJono;
	}
	
	protected double getSuurinIlmoJono() {
		return ilmoSuurinJono;
	}
	
	protected double getSuurinRokJono() {
		return rokSuurinJono;
	}
	
	protected double getSuurinSeurJono() {
		return seurSuurinJono;
	}
	
	//Keskiarvot 
	
	protected double getOviKeskiarvo() {
		return oviKeskiarvo;
	}
	
	protected double getIlmoKeskiarvo() {
		return ilmoKeskiarvo;
	}
	
	protected double getRokKeskiarvo() {
		return rokKeskiarvo;
	}
	
	protected double getSeurKeskiarvo() {
		return seurKeskiarvo;
	}
	

	@Override
	protected void tulokset() {
		laskeSuoritustehot();
		laskeKayttoasteet();
		sorttaaJonot();
		laskeJonojenKeskiarvot();
		kontrolleri.naytaLoppuaika(Kello.getInstance().getAika());
		lapimenoajat.add(Kello.getInstance().getAika());
	}

}
