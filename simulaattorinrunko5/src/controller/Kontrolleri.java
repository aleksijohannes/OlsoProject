package controller;

import java.util.ArrayList;
import java.util.HashMap;
import eduni.distributions.ContinuousGenerator;
import eduni.distributions.Negexp;
import eduni.distributions.Normal;
import eduni.distributions.Uniform;
import javafx.application.Platform;
import simu.framework.IMoottori;
import simu.model.IOmaMoottori;
import simu.model.OmaMoottori;
import view.ISimulaattorinUI;

/**
 * Toimii linkkinä ja tiedonvälittäjänä käyttöliittymän, moottorin sekä
 * tietokannan välillä. Toteuttaa rajapinnat IKontrolleriVtoM ja
 * IKontrolleriMtoV
 * 
 * @author Jenni Javanainen
 */

public class Kontrolleri implements IKontrolleriVtoM, IKontrolleriMtoV {

	private IOmaMoottori moottori;
	private ISimulaattorinUI ui;
	private ISimulaatioDAO dao;

	private HashMap<String, HashMap<Integer, double[]>> oviJakaumat;
	private HashMap<String, HashMap<Integer, double[]>> ilmoJakaumat;
	private HashMap<String, HashMap<Integer, double[]>> rokJakaumat;
	private HashMap<String, HashMap<Integer, double[]>> seurJakaumat;
	private HashMap<String, HashMap<Integer, double[]>> saapumisJakaumat;

	private long seed;

	private Simulaatio simu;

	public Kontrolleri(ISimulaattorinUI ui) {
		this.ui = ui;
		dao = new SimulaatioDAO();
		alustaJakaumat();
	}

	public Kontrolleri() {

	}

	/**
	 * Käynnistää simulaation luomalla uuden OmaMoottori-säikeen ja starttaamalla
	 * sen. Samalla aktivoidaan myös visualisointi.
	 */
	@Override
	public void kaynnistaSimulointi() {
		seed = System.currentTimeMillis();

		moottori = new OmaMoottori(this);
		moottori.setSimulointiaika(ui.getAika());
		moottori.setViive(ui.getViive());
		ui.getVisualisointi().tyhjennaNaytto();
		((Thread) moottori).start();
	}

	/**
	 * Luo uuden Simulaatio-olion viimeisimmästä simulaatiosta, ja antaa sen
	 * SimulaatioDAO-luokalle tietokantaan tallennettavaksi. Simulaation
	 * ajoparametrit haetaan käyttöliittymästä. Simulaation tulokset haetaan
	 * moottorista.
	 */
	@Override
	public void tallennaSimulaatio() {
		simu = new Simulaatio();

		// Parametrit
		simu.setOviMaara(getOviMaara());
		simu.setOviNopeus(ui.getOviPalvelunopeus());
		simu.setOviJakauma(ui.getOviJakauma());
		simu.setIlmoMaara(getIlmoMaara());
		simu.setIlmoNopeus(ui.getIlmoPalvelunopeus());
		simu.setIlmoJakauma(ui.getIlmoJakauma());
		simu.setRokMaara(getRokMaara());
		simu.setRokNopeus(ui.getRokPalvelunopeus());
		simu.setRokJakauma(ui.getRokJakauma());
		simu.setSeurNopeus(ui.getSeurPalvelunopeus());
		simu.setSeurJakauma(ui.getSeurJakauma());
		simu.setSaapNopeus(ui.getSaapumistiheys());
		simu.setSaapJakauma(ui.getSaapumisjakauma());

		// Tulokset
		simu.setLoppuaika(moottori.getLoppuaika());
		simu.setAvgLapimeno(moottori.getAvgLapimeno());
		simu.setPalvellutAsiakkaat(moottori.getAsiakasmaara());
		simu.setPieninAsiakas(moottori.getPieninAsiakas());
		simu.setSuurinAsiakas(moottori.getSuurinAsiakas());
		simu.setKayttoasteet(moottori.getKayttoasteet());
		simu.setTehot(moottori.getSuoritustehot());
		simu.setSuurinOviJono(moottori.getSuurinOviJono());
		simu.setSuurinIlmoJono(moottori.getSuurinIlmoJono());
		simu.setSuurinRokJono(moottori.getSuurinRokJono());
		simu.setSuurinSeurJono(moottori.getSuurinSeurJono());
		simu.setAvgOvijono(moottori.getOviKeskiarvo());
		simu.setAvgIlmoJono(moottori.getIlmoKeskiarvo());
		simu.setAvgRokJono(moottori.getRokKeskiarvo());
		simu.setAvgSeurJono(moottori.getSeurKeskiarvo());

		dao.tallenna(simu);
	}

	/**
	 * Hakee ja palauttaa SimulaatioDAO-luokan avulla kaikki tietokantaan
	 * tallennetut simulaatiot
	 * 
	 * @return kaikki tietokantaan tallennetut Simulaatio-oliot listamuodossa
	 */
	@Override
	public ArrayList<Simulaatio> haeSimulaatiot() {
		return dao.haeSimulaatiot();
	}

	/**
	 * Hidastaa simulaationopeutta
	 */
	@Override
	public void hidasta() {
		moottori.setViive((long) (moottori.getViive() * 1.10));
	}

	/**
	 * Nopeuttaa simulaationopeutta
	 */
	@Override
	public void nopeuta() {
		moottori.setViive((long) (moottori.getViive() * 0.9));
	}

	private int jono;
	private int poista;

	/**
	 * Aloittaa uuden asiakkaan visualisoinnin käyttöliittymässä
	 * 
	 * @author Aleksi Alanko
	 */
	@Override
	public void visualisoiAsiakas(int jono) {
		Platform.runLater(new Runnable() {
			public void run() {
				ui.getVisualisointi().uusiAsiakas(jono);
			}
		});
	}

	/**
	 * Lopettaa uuden asiakkaan visualisoinnin käyttöliittymässä
	 * 
	 * @author Aleksi Alanko
	 */
	@Override
	public void poistaAsiakas(int poista) {
		Platform.runLater(new Runnable() {
			public void run() {
				ui.getVisualisointi().poistaAsiakasJonosta(poista);
			}
		});
	}

	/**
	 * Paluttaa käyttäliittymästä haetun ovihenkilöiden lukumäärän
	 */
	@Override
	public int getOviMaara() {
		return ui.getOviMaara();
	}

	/**
	 * Paluttaa käyttäliittymästä haetun ilmoittautumistiskien lukumäärän
	 */
	@Override
	public int getIlmoMaara() {
		return ui.getIlmoMaara();
	}

	/**
	 * Paluttaa käyttäliittymästä haetun rokottajien lukumäärän
	 */
	@Override
	public int getRokMaara() {
		return ui.getRokMaara();
	}

	/**
	 * Etsii jakaumatyyppiä ja nopeusarvoa vastaavat lukuarvot parmetrina annetusta
	 * hajautustaulusta, ja palauttaa satunnaislukugeneraattorin, joka saa kyseiset
	 * lukuarvot parametrikseen.
	 * 
	 * @param jakaumataulu hajautustaulu, josta lukuarvot haetaan ajoparametrien
	 *                     perusteella
	 * @param jakauma      ajoparametrina saatu jakauman tyyppi
	 * @param nopeus       ajoparametrina saatu jakauman nopeus-arvo
	 * @return parametreja vastaava satunnaislukugeneraattori
	 */
	public ContinuousGenerator valitseJakauma(HashMap<String, HashMap<Integer, double[]>> jakaumataulu, String jakauma,
			int nopeus) {
		HashMap<Integer, double[]> taulu = jakaumataulu.get(jakauma);
		double[] taulukko = taulu.get(nopeus);
		switch (jakauma) {
		case "uniform":
			return new Uniform(taulukko[0], taulukko[1], seed);
		case "normal":
			return new Normal(taulukko[0], taulukko[1], seed);
		case "negexp":
			return new Negexp(taulukko[0], seed);
		}
		return null;
	}

	/**
	 * Palauttaa ajoparametrien perusteella luodun satunnaislukugeneraattorin
	 * ovihenkilöille
	 * 
	 * @return satunnaislukugeneraattori
	 */
	@Override
	public ContinuousGenerator getOviJakauma() {
		return valitseJakauma(oviJakaumat, ui.getOviJakauma(), ui.getOviPalvelunopeus());
	}

	/**
	 * Palauttaa ajoparametrien perusteella luodun satunnaislukugeneraattorin
	 * ilmoittautumistiskeille
	 * 
	 * @return satunnaislukugeneraattori
	 */
	@Override
	public ContinuousGenerator getIlmoJakauma() {
		return valitseJakauma(ilmoJakaumat, ui.getIlmoJakauma(), ui.getIlmoPalvelunopeus());
	}

	/**
	 * Palauttaa ajoparametrien perusteella luodun satunnaislukugeneraattorin
	 * rokottajille
	 * 
	 * @return satunnaislukugeneraattori
	 */
	@Override
	public ContinuousGenerator getRokJakauma() {
		return valitseJakauma(rokJakaumat, ui.getRokJakauma(), ui.getRokPalvelunopeus());
	}

	/**
	 * Palauttaa ajoparametrien perusteella luodun satunnaislukugeneraattorin
	 * seurannalle
	 * 
	 * @return satunnaislukugeneraattori
	 */
	@Override
	public ContinuousGenerator getSeurJakauma() {
		return valitseJakauma(seurJakaumat, ui.getSeurJakauma(), ui.getSeurPalvelunopeus());
	}

	/**
	 * Palauttaa ajoparametrien perusteella luodun satunnaislukugeneraattorin
	 * saapumisprosessille
	 * 
	 * @return satunnaislukugeneraattori
	 */
	@Override
	public ContinuousGenerator getSaapumisjakauma() {
		return valitseJakauma(saapumisJakaumat, ui.getSaapumisjakauma(), ui.getSaapumistiheys());
	}

	/**
	 * Alustaa instanssimuuttujina olevat hajautustaulut, jotka pitävät sisällään
	 * eri jakaumien raja-arvojen parametrit. Kaikilla neljällä eri
	 * palvelupistetyypillä + saapumistapahtumalla on eri raja-arvot, sillä
	 * jakaumatyypistä ja nopeudesta riippumatta halutaan, että satunnaisluvut
	 * pysyvät tietyissä rajoissa myös suhteessa toisiin palvelupistetyyppeihin
	 * (esim ilmoittautuminen on nopeampi kuin rokotus). Ensimmäinen parametri
	 * kertoo nopeuden (1=hidas, 2=normaali, 3=nopea). Toisena parametrina olevan
	 * taulukon sisällä ovat käytettävät lukuarvot, jotka riippuvat sekä
	 * jakaumatyypistä, että edellä annetusta nopeudesta: Uniform: {alaraja,
	 * yläraja} Normal: {keskiarvo, hajonta} Negexp: {oletusarvo}
	 */
	public void alustaJakaumat() {
		HashMap<Integer, double[]> oviUniform = new HashMap<>();
		oviUniform.put(1, new double[] { 2.5, 3.5 });
		oviUniform.put(2, new double[] { 1.5, 2.5 });
		oviUniform.put(3, new double[] { 1, 2 });
		HashMap<Integer, double[]> oviNormal = new HashMap<>();
		oviNormal.put(1, new double[] { 4, 1 });
		oviNormal.put(2, new double[] { 3, 1 });
		oviNormal.put(3, new double[] { 2, 1 });
		HashMap<Integer, double[]> oviExp = new HashMap<>();
		oviExp.put(1, new double[] { 3 });
		oviExp.put(2, new double[] { 2 });
		oviExp.put(3, new double[] { 1 });
		oviJakaumat = new HashMap<>();
		oviJakaumat.put("uniform", oviUniform);
		oviJakaumat.put("normal", oviNormal);
		oviJakaumat.put("negexp", oviExp);

		HashMap<Integer, double[]> ilmoUniform = new HashMap<>();
		ilmoUniform.put(1, new double[] { 4, 6 });
		ilmoUniform.put(2, new double[] { 3, 4.5 });
		ilmoUniform.put(3, new double[] { 2.5, 3.5 });
		HashMap<Integer, double[]> ilmoNormal = new HashMap<>();
		ilmoNormal.put(1, new double[] { 7, 1 });
		ilmoNormal.put(2, new double[] { 4, 1 });
		ilmoNormal.put(3, new double[] { 3, 1 });
		HashMap<Integer, double[]> ilmoExp = new HashMap<>();
		ilmoExp.put(1, new double[] { 5 });
		ilmoExp.put(2, new double[] { 3 });
		ilmoExp.put(3, new double[] { 2 });
		ilmoJakaumat = new HashMap<>();
		ilmoJakaumat.put("uniform", ilmoUniform);
		ilmoJakaumat.put("normal", ilmoNormal);
		ilmoJakaumat.put("negexp", ilmoExp);

		HashMap<Integer, double[]> rokUniform = new HashMap<>();
		rokUniform.put(1, new double[] { 12, 20 });
		rokUniform.put(2, new double[] { 9, 12 });
		rokUniform.put(3, new double[] { 7, 10 });
		HashMap<Integer, double[]> rokNormal = new HashMap<>();
		rokNormal.put(1, new double[] { 19, 3 });
		rokNormal.put(2, new double[] { 12, 2 });
		rokNormal.put(3, new double[] { 10, 2 });
		HashMap<Integer, double[]> rokExp = new HashMap<>();
		rokExp.put(1, new double[] { 6 });
		rokExp.put(2, new double[] { 5 });
		rokExp.put(3, new double[] { 4 });
		rokJakaumat = new HashMap<>();
		rokJakaumat.put("uniform", rokUniform);
		rokJakaumat.put("normal", rokNormal);
		rokJakaumat.put("negexp", rokExp);

		HashMap<Integer, double[]> seurUniform = new HashMap<>();
		seurUniform.put(1, new double[] { 15, 16 });
		seurUniform.put(2, new double[] { 14.5, 15.5 });
		seurUniform.put(3, new double[] { 14, 15 });
		HashMap<Integer, double[]> seurNormal = new HashMap<>();
		seurNormal.put(1, new double[] { 15.5, 1 });
		seurNormal.put(2, new double[] { 15, 1 });
		seurNormal.put(3, new double[] { 14.5, 1 });
		seurJakaumat = new HashMap<>();
		seurJakaumat.put("uniform", seurUniform);
		seurJakaumat.put("normal", seurNormal);

		HashMap<Integer, double[]> saapumisUniform = new HashMap<>();
		saapumisUniform.put(1, new double[] { 4, 8 });
		saapumisUniform.put(2, new double[] { 3.5, 5 });
		saapumisUniform.put(3, new double[] { 0.5, 4.5 });
		HashMap<Integer, double[]> saapumisNormal = new HashMap<>();
		saapumisNormal.put(1, new double[] { 6, 2 });
		saapumisNormal.put(2, new double[] { 4.5, 1.5 });
		saapumisNormal.put(3, new double[] { 2, 1 });
		HashMap<Integer, double[]> saapumisExp = new HashMap<>();
		saapumisExp.put(1, new double[] { 5 });
		saapumisExp.put(2, new double[] { 3 });
		saapumisExp.put(3, new double[] { 1 });
		saapumisJakaumat = new HashMap<>();
		saapumisJakaumat.put("uniform", saapumisUniform);
		saapumisJakaumat.put("normal", saapumisNormal);
		saapumisJakaumat.put("negexp", saapumisExp);

	}

	/**
	 * Vie simulaation loppuajan käyttöliittymään
	 * 
	 * @param aika simulaation loppuaika/kesto
	 */
	@Override
	public void naytaLoppuaika(double aika) {
		Platform.runLater(() -> ui.setLoppuaika(aika));
	}

	/**
	 * Vie läpimenoaikojen keskiarvon käyttöliittymään
	 * 
	 * @param aika simulaation läpi kulkeneiden asiakkaiden läpimenoaikojen
	 *             keskiarvo
	 */
	@Override
	public void naytaAvgLapimeno(double aika) {
		Platform.runLater(() -> ui.setLapimenoaika(aika));

	}

	/**
	 * Vie palvelupistekohtaiset käyttöasteet käyttöliittymään
	 * 
	 * @param palvelupisteet sisältää palvelupisteiden käyttöasteet palvelupisteen
	 *                       tunnus-käyttöaste -pareina
	 */
	@Override
	public void naytaKayttoaste(HashMap<String, Double> palvelupisteet) {
		Platform.runLater(() -> ui.setKayttoasteet(palvelupisteet));
	}

	/**
	 * Vie palvelupistekohtaiset suoritustehot käyttöliittymään
	 * 
	 * @param palvelupisteet sisältää palvelupisteiden suoritustehot palvelupisteen
	 *                       tunnus-teho -pareina
	 */
	@Override
	public void naytaSuoritusteho(HashMap<String, Double> palvelupisteet) {
		Platform.runLater(() -> ui.setSuoritustehot(palvelupisteet));
	}

	/**
	 * Vie pienimmän läpimenoajan käyttöliittymään
	 * 
	 * @param lapimenoaika pienin yksittäisen asiakkaan läpimenoaika
	 */
	@Override
	public void naytaPieninAsiakas(double lapimenoaika) {
		Platform.runLater(() -> ui.setPieninAsiakas(lapimenoaika));

	}

	/**
	 * Vie suurimman läpimenoajan käyttöliittymään
	 * 
	 * @param lapimenoaika suurin yksittäisen asiakkaan läpimenoaika
	 */
	@Override
	public void naytaSuurinAsiakas(double lapimenoaika) {
		Platform.runLater(() -> ui.setSuurinAsiakas(lapimenoaika));

	}

	/**
	 * Vie suurimman ovihenkilöiden jonotusajan käyttöliittymään
	 * 
	 * @param jonotusaika suurin jonotusaika ovihenkilöille
	 */
	@Override
	public void naytaSuurinOviJono(double jonotusaika) {
		Platform.runLater(() -> ui.setSuurinOviJono(jonotusaika));

	}

	/**
	 * Vie suurimman ilmoittautumistiskien jonotusajan käyttöliittymään
	 * 
	 * @param jonotusaika suurin jonotusaika ilmoittautumistiskeille
	 */
	@Override
	public void naytaSuurinIlmoJono(double jonotusaika) {
		Platform.runLater(() -> ui.setSuurinIlmoJono(jonotusaika));

	}

	/**
	 * Vie suurimman rokottajien jonotusajan käyttöliittymään
	 * 
	 * @param jonotusaika suurin jonotusaika rokottajille
	 */
	@Override
	public void naytaSuurinRokJono(double jonotusaika) {
		Platform.runLater(() -> ui.setSuurinRokJono(jonotusaika));

	}

	/**
	 * Vie suurimman seurantapisteen jonotusajan käyttöliittymään
	 * 
	 * @param jonotusaika suurin jonotusaika seurantapisteeseen
	 */
	@Override
	public void naytaSuurinSeurJono(double jonotusaika) {
		Platform.runLater(() -> ui.setSuurinSeurJono(jonotusaika));

	}

	/**
	 * Vie palveltujen asiakkaiden määrän käyttäliittymään
	 * 
	 * @param kpl simulaation aikana kokonaan palveltujen asiakkaiden lukumäärä
	 */
	@Override
	public void naytaPalvellutAsiakkaat(int kpl) {
		Platform.runLater(() -> ui.setPalvellutAsiakkaat(kpl));

	}

	/**
	 * Vie ovihenkilöiden jonotusaikajoen keskiarvon käyttöliittymään
	 * 
	 * @param jonotusaika ovihenkilöiden jonotusaikojen keskiarvo
	 */
	@Override
	public void naytaAvgOviJono(double jonotusaika) {
		Platform.runLater(() -> ui.setAvgOviJono(jonotusaika));

	}

	/**
	 * Vie ilmoittautumistiskien jonotusaikajoen keskiarvon käyttöliittymään
	 * 
	 * @param jonotusaika ilmoittautumistiskien jonotusaikojen keskiarvo
	 */
	@Override
	public void naytaAvgIlmoJono(double jonotusaika) {
		Platform.runLater(() -> ui.setAvgIlmoJono(jonotusaika));

	}

	/**
	 * Vie rokotuspisteiden jonotusaikajoen keskiarvon käyttöliittymään
	 * 
	 * @param jonotusaika rokotuspisteiden jonotusaikojen keskiarvo
	 */
	@Override
	public void naytaAvgRokJono(double jonotusaika) {
		Platform.runLater(() -> ui.setAvgRokJono(jonotusaika));

	}

	/**
	 * Vie seurantapisteen jonotusaikajoen keskiarvon käyttöliittymään
	 * 
	 * @param jonotusaika seurantapisteen jonotusaikojen keskiarvo
	 */
	@Override
	public void naytaAvgSeurJono(double jonotusaika) {
		Platform.runLater(() -> ui.setAvgSeurJono(jonotusaika));

	}

}
