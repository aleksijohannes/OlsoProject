package simu.model;

import java.util.ArrayList;
import java.util.Arrays;
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

public class OmaMoottori extends Moottori implements IOmaMoottori{

	private Saapumisprosessi saapumisprosessi;
	private Palvelupiste[] ovihenkilot;
	private Palvelupiste[] ilmoittautumistiskit;
	private Palvelupiste[] rokottajat;
	private Palvelupiste[] jalkiseurannat;

	private int asiakasmaara = 0;
	
	private List<Double> lapimenoajat = new ArrayList<Double>();
	private List<Double> asiakkaidenLapimenoajat = new ArrayList<Double>();

	private int ovihenkiloMaara;
	private int ilmotiskiMaara;
	private int rokottajaMaara;
	private int seurantaMaara;
	
	private double seurJonotus;
	private double seurJonotusAloitus;
	private double ilmoJonotus;
	private double ilmoJonotusAloitus;
	private double rokJonotus;
	private double rokJonotusAloitus;
	private double oviJonotus;
	private double oviJonotusAloitus;
	
	private double loppuaika;
	private double avgLapimeno;
	private double suurinAsiakas;
	private double pieninAsiakas;
	

	private List<Double> oviJonotukset = new ArrayList<Double>();
	private List<Double> ilmoJonotukset = new ArrayList<Double>();
	private List<Double> rokJonotukset = new ArrayList<Double>();
	private List<Double> seurJonotukset = new ArrayList<Double>();

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
		ilmotiskiMaara = kontrolleri.getIlmoMaara();
		rokottajaMaara = kontrolleri.getRokMaara();
		seurantaMaara = 75;

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
		
		for (int i = 0; i < rokottajaMaara; i++) {
			System.out.println("rokottaja: " + rokottajat[i].haeNimi());
		}
		
		System.out.println("jälkiseurantojen määrä" + jalkiseurannat.length);
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
			//this.oviJonotusAloitus = (Kello.getInstance().getAika());
			break;
		case DEP1:
			a = etsiValmis(ovihenkilot, t).otaJonosta();
			pieninJono(ilmoittautumistiskit).lisaaJonoon(a);
			//this.ilmoJonotusAloitus = (Kello.getInstance().getAika());
			break;
		case DEP2:
			a = etsiValmis(ilmoittautumistiskit, t).otaJonosta();
			pieninJono(rokottajat).lisaaJonoon(a);
			//this.rokJonotusAloitus = (Kello.getInstance().getAika());
			break;
		case DEP3:
			a = etsiValmis(rokottajat, t).otaJonosta();
			pieninJono(jalkiseurannat).lisaaJonoon(a);
			//this.seurJonotusAloitus = (Kello.getInstance().getAika());
			break;
		case DEP4:
			a = etsiValmis(jalkiseurannat, t).otaJonosta();
			a.setPoistumisaika(Kello.getInstance().getAika());
			asiakkaidenLapimenoajat.add(a.asiakkaanLapimeno());
			
			
			a.raportti();
			asiakasmaara++;
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
				//oviJonotus = (Kello.getInstance().getAika()) - oviJonotusAloitus;
				//oviJonotukset.add(oviJonotus);
			}
		}

		for (Palvelupiste p : ilmoittautumistiskit) {
			if (!p.onVarattu() && p.onJonossa()) {
				p.aloitaPalvelu();
				//ilmoJonotus = (Kello.getInstance().getAika()) - ilmoJonotusAloitus;
				//ilmoJonotukset.add(ilmoJonotus);
			}
		}

		for (Palvelupiste p : rokottajat) {
			if (!p.onVarattu() && p.onJonossa()) {
				p.aloitaPalvelu();
				//rokJonotus = (Kello.getInstance().getAika()) - rokJonotusAloitus;
				//rokJonotukset.add(rokJonotus);
			}
		}

		for (Palvelupiste p : jalkiseurannat) {
			if (!p.onVarattu() && p.onJonossa()) {
				p.aloitaPalvelu();
				//seurJonotus = (Kello.getInstance().getAika()) - seurJonotusAloitus;
				//seurJonotukset.add(seurJonotus);
				
				
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
		/*for (int i = 0; i < seurantaMaara; i++) {
			suoritustehot.put(jalkiseurannat[i].haeNimi(), jalkiseurannat[i].suoritusteho());
		}*/
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
		/*for (int i = 0; i < seurantaMaara; i++) {
			kayttoasteet.put(jalkiseurannat[i].haeNimi(), jalkiseurannat[i].kayttoaste());
		}*/
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
	
	@SuppressWarnings("unchecked")
	public void haeJonotukset() {
		for (int i = 0; i < ovihenkiloMaara; i++) {
			oviJonotukset.addAll(ovihenkilot[i].getJonotusajat());
		}
		for (int i = 0; i < ovihenkiloMaara; i++) {
			ilmoJonotukset.addAll(ilmoittautumistiskit[i].getJonotusajat());
		}
		for (int i = 0; i < ovihenkiloMaara; i++) {
			rokJonotukset.addAll(rokottajat[i].getJonotusajat());
		}
		for (int i = 0; i < ovihenkiloMaara; i++) {
			seurJonotukset.addAll(jalkiseurannat[i].getJonotusajat());
		}
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
	
	public double getSuurinOviJono() {
		return oviSuurinJono;
	}
	
	public double getSuurinIlmoJono() {
		return ilmoSuurinJono;
	}
	
	public double getSuurinRokJono() {
		return rokSuurinJono;
	}
	
	public double getSuurinSeurJono() {
		return seurSuurinJono;
	}
	
	//Keskiarvot 
	
	public double getOviKeskiarvo() {
		return oviKeskiarvo;
	}
	
	public double getIlmoKeskiarvo() {
		return ilmoKeskiarvo;
	}
	
	public double getRokKeskiarvo() {
		return rokKeskiarvo;
	}
	
	public double getSeurKeskiarvo() {
		return seurKeskiarvo;
	}
	
	public int getAsiakasmaara() {
		return asiakasmaara;
	}

	@Override
	protected void tulokset() {
		laskeSuoritustehot();
		laskeKayttoasteet();
		haeJonotukset();
		sorttaaJonot();

		System.out.println(Arrays.asList(kayttoasteet));
		System.out.println(Arrays.asList(suoritustehot));
		
		
		laskeJonojenKeskiarvot();
		System.out.println("pienin asiakas" + pieninAsiakas());
		System.out.println("suurin asiakas" + suurinAsiakas());
		System.out.println("Simulaation läpi meni " + getAsiakasmaara() + " asiakasta");
		kontrolleri.naytaLoppuaika(getLoppuaika());
		lapimenoajat.add(Kello.getInstance().getAika());
	}

	@Override
	public double getLoppuaika() {
		loppuaika = Kello.getInstance().getAika();
		return loppuaika;
	}

	@Override
	public double getAvgLapimeno() {
		avgLapimeno = avgLapimeno();
		return avgLapimeno;
	}

	@Override
	public double getPieninAsiakas() {
		pieninAsiakas = pieninAsiakas();
		return pieninAsiakas;
	}

	@Override
	public double getSuurinAsiakas() {
		suurinAsiakas = suurinAsiakas();
		return suurinAsiakas;
	}

}
