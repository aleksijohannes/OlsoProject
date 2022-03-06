package controller;

/**
 * Toimii linkkinä ja tiedonvälittäjänä käyttöliittymän, moottorin sekä tietokannan välillä.
 * 
 * @author Jenni Javanainen
 */

import java.util.ArrayList;

/**
 * 
 * @author Jenni Javanainen
 */

import java.util.HashMap;

import eduni.distributions.ContinuousGenerator;
import eduni.distributions.Negexp;
import eduni.distributions.Normal;
import eduni.distributions.Uniform;
import javafx.application.Platform;
import simu.framework.IMoottori;
import simu.model.OmaMoottori;
import view.ISimulaattorinUI;

public class Kontrolleri implements IKontrolleriVtoM, IKontrolleriMtoV{  
	
	private IMoottori moottori; 
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
		
	/**
	 * Käynnistää simulaation luomalla uuden OmaMoottori-säikeen ja starttaamalla sen. Samalla aktivoidaan myös visualisointi.
	 */
	@Override
	public void kaynnistaSimulointi() {
		seed = System.currentTimeMillis();
		
		moottori = new OmaMoottori(this); 
		moottori.setSimulointiaika(ui.getAika());
		moottori.setViive(ui.getViive());
		ui.getVisualisointi().tyhjennaNaytto();
		((Thread)moottori).start();
	}
	
	/**
	 * Luo uuden Simulaatio-olion viimeisimmästä simulaatiosta, ja antaa sen SimulaatioDAO-luokalle tietokantaan tallennettavaksi.
	 */
    @Override	
	public void tallennaSimulaatio() {
		simu = new Simulaatio();
		
		//Parametrit
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
		
		//Tulokset
		
		
		dao.tallenna(simu);
	}
	
    /**
     * Hakee ja palauttaa SimulaatioDAO-luokan avulla kaikki tietokantaan tallennetut simulaatiot
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
		moottori.setViive((long)(moottori.getViive()*1.10));
	}

	/**
	 * Nopeuttaa simulaationopeutta
	 */
	@Override
	public void nopeuta() { 
		moottori.setViive((long)(moottori.getViive()*0.9));
	}
	
	/**
	 * Vie simulaation loppuajan käyttöliittymään	
	 * @param aika simulaation loppuaika/kesto
	 */
	@Override
	public void naytaLoppuaika(double aika) {
		Platform.runLater(()->ui.setLoppuaika(aika)); 
	}

	/**
	 * Aloittaa uuden asiakkaan visualisoinnin käyttöliittymässä
	 */
	@Override
	public void visualisoiAsiakas() {
		Platform.runLater(new Runnable(){
			public void run(){
				ui.getVisualisointi().uusiAsiakas();
			}
		});
	}
	
	/**
	 * 
	 * @param aika
	 */
	@Override
	public void naytaAvgLapimeno(double aika) {
		Platform.runLater(() -> ui.setLapimenoaika(aika));
		
	}
	
	/**
	 * Vie palvelupistekohtaiset käyttöasteet käyttöliittymään
	 * @param palvelupisteet sisältää palvelupisteiden käyttöasteet palvelupisteen tunnus-käyttöaste -pareina
	 */
	@Override
	public void naytaKayttoaste(HashMap<String, Double> palvelupisteet) {
		Platform.runLater(() -> ui.setKayttoasteet(palvelupisteet));
	}
	
	/**
	 * Vie palvelupistekohtaiset suoritustehot käyttöliittymään
	 * @param palvelupisteet sisältää palvelupisteiden suoritustehot palvelupisteen tunnus-teho -pareina
	 */
	@Override
	public void naytaSuoritusteho(HashMap<String, Double> palvelupisteet) {
		Platform.runLater(() -> ui.setSuoritustehot(palvelupisteet));	
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
	 * Etsii jakaumatyyppiä ja nopeusarvoa vastaavat lukuarvot parmetrina annetusta hajautustaulusta, ja palauttaa 
	 * satunnaislukugeneraattorin, joka saa kyseiset lukuarvot parametrikseen.
	 * @param jakaumataulu hajautustaulu, josta lukuarvot haetaan ajoparametrien perusteella
	 * @param jakauma ajoparametrina saatu jakauman tyyppi
	 * @param nopeus ajoparametrina saatu jakauman nopeus-arvo
	 * @return parametreja vastaava satunnaislukugeneraattori
	 */
	public ContinuousGenerator valitseJakauma(HashMap<String, HashMap<Integer, double[]>> jakaumataulu, String jakauma, int nopeus) {
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
	 * Palauttaa ajoparametrien perusteella luodun satunnaislukugeneraattorin ovihenkilöille
	 * @return satunnaislukugeneraattori
	 */
	@Override
	public ContinuousGenerator getOviJakauma() {
		return valitseJakauma(oviJakaumat, ui.getOviJakauma(), ui.getOviPalvelunopeus());
	}
	
	/**
	 * Palauttaa ajoparametrien perusteella luodun satunnaislukugeneraattorin ilmoittautumistiskeille
	 * @return satunnaislukugeneraattori
	 */
	@Override
	public ContinuousGenerator getIlmoJakauma() {
		return valitseJakauma(ilmoJakaumat, ui.getIlmoJakauma(), ui.getIlmoPalvelunopeus());
	}

	/**
	 * Palauttaa ajoparametrien perusteella luodun satunnaislukugeneraattorin rokottajille
	 * @return satunnaislukugeneraattori
	 */
	@Override
	public ContinuousGenerator getRokJakauma() {
		return valitseJakauma(rokJakaumat, ui.getRokJakauma(), ui.getRokPalvelunopeus());
	}

	/**
	 * Palauttaa ajoparametrien perusteella luodun satunnaislukugeneraattorin seurannalle
	 * @return satunnaislukugeneraattori
	 */
	@Override
	public ContinuousGenerator getSeurJakauma() {
		return valitseJakauma(seurJakaumat, ui.getSeurJakauma(), ui.getSeurPalvelunopeus());
	}

	/**
	 * Palauttaa ajoparametrien perusteella luodun satunnaislukugeneraattorin saapumisprosessille
	 * @return satunnaislukugeneraattori
	 */
	@Override
	public ContinuousGenerator getSaapumisjakauma() {
		return valitseJakauma(saapumisJakaumat, ui.getSaapumisjakauma(), ui.getSaapumistiheys());
	}
	
	/**
	 * Alustaa instanssimuuttujina olevat hajautustaulut, jotka pitävät sisällään eri jakaumien raja-arvojen parametrit.
	 * Kaikilla neljällä eri palvelupistetyypillä + saapumistapahtumalla on eri raja-arvot, sillä jakaumatyypistä ja nopeudesta riippumatta
	 * halutaan, että satunnaisluvut pysyvät tietyissä rajoissa myös suhteessa toisiin palvelupistetyyppeihin (esim ilmoittautuminen on nopeampi kuin rokotus).
	 * Ensimmäinen parametri kertoo nopeuden (1=hidas, 2=normaali, 3=nopea).
	 * Toisena parametrina olevan taulukon sisällä ovat käytettävät lukuarvot, jotka riippuvat sekä jakaumatyypistä, että edellä annetusta nopeudesta:
	 * Uniform: {alaraja, yläraja}
	 * Normal: {keskiarvo, hajonta}
	 * Negexp: {oletusarvo}
	 */
	public void alustaJakaumat() {
		HashMap<Integer, double[]> oviUniform = new HashMap<>();
		oviUniform.put(1, new double[] {3, 4});
		oviUniform.put(2, new double[] {1.5, 3});
		oviUniform.put(3, new double[] {1, 1.5});
		HashMap<Integer, double[]> oviNormal = new HashMap<>();
		oviNormal.put(1, new double[] {3, 1});
		oviNormal.put(2, new double[] {2.5, 1});
		oviNormal.put(3, new double[] {2, 1});
		HashMap<Integer, double[]> oviExp = new HashMap<>();
		oviExp.put(1, new double[] {3});
		oviExp.put(2, new double[] {2});
		oviExp.put(3, new double[] {1});
		oviJakaumat = new HashMap<>();
		oviJakaumat.put("uniform", oviUniform);
		oviJakaumat.put("normal", oviNormal);
		oviJakaumat.put("negexp", oviExp);
		
		HashMap<Integer, double[]> ilmoUniform = new HashMap<>();
		ilmoUniform.put(1, new double[] {3.5, 5});
		ilmoUniform.put(2, new double[] {2, 3.5});
		ilmoUniform.put(3, new double[] {1, 2});
		HashMap<Integer, double[]> ilmoNormal = new HashMap<>();
		ilmoNormal.put(1, new double[] {5, 1});
		ilmoNormal.put(2, new double[] {3.5, 1});
		ilmoNormal.put(3, new double[] {2.5, 1});
		HashMap<Integer, double[]> ilmoExp = new HashMap<>();
		ilmoExp.put(1, new double[] {4});
		ilmoExp.put(2, new double[] {3});
		ilmoExp.put(3, new double[] {2});
		ilmoJakaumat = new HashMap<>();
		ilmoJakaumat.put("uniform", ilmoUniform);
		ilmoJakaumat.put("normal", ilmoNormal);
		ilmoJakaumat.put("negexp", ilmoExp);
		
		HashMap<Integer, double[]> rokUniform = new HashMap<>();
		rokUniform.put(1, new double[] {7, 12});
		rokUniform.put(2, new double[] {5, 8});
		rokUniform.put(3, new double[] {3, 6});
		HashMap<Integer, double[]> rokNormal = new HashMap<>();
		rokNormal.put(1, new double[] {10, 3});
		rokNormal.put(2, new double[] {8, 2});
		rokNormal.put(3, new double[] {6, 2});
		HashMap<Integer, double[]> rokExp = new HashMap<>();
		rokExp.put(1, new double[] {9});
		rokExp.put(2, new double[] {7});
		rokExp.put(3, new double[] {5});
		rokJakaumat = new HashMap<>();
		rokJakaumat.put("uniform", rokUniform);
		rokJakaumat.put("normal", rokNormal);
		rokJakaumat.put("negexp", rokExp);
		
		HashMap<Integer, double[]> seurUniform = new HashMap<>();
		seurUniform.put(1, new double[] {15, 16});
		seurUniform.put(2, new double[] {14.5, 15.5});
		seurUniform.put(3, new double[] {14, 15});
		HashMap<Integer, double[]> seurNormal = new HashMap<>();
		seurNormal.put(1, new double[] {15.5, 1});
		seurNormal.put(2, new double[] {15, 1});
		seurNormal.put(3, new double[] {14.5, 1});
		HashMap<Integer, double[]> seurExp = new HashMap<>();
		seurExp.put(1, new double[] {16});
		seurExp.put(2, new double[] {15});
		seurExp.put(3, new double[] {14});
		seurJakaumat = new HashMap<>();
		seurJakaumat.put("uniform", seurUniform);
		seurJakaumat.put("normal", seurNormal);
		seurJakaumat.put("negexp", seurExp);
		
		HashMap<Integer, double[]> saapumisUniform = new HashMap<>();
		saapumisUniform.put(1, new double[] {6, 10});
		saapumisUniform.put(2, new double[] {4, 7});
		saapumisUniform.put(3, new double[] {2, 5});
		HashMap<Integer, double[]> saapumisNormal = new HashMap<>();
		saapumisNormal.put(1, new double[] {8, 2});
		saapumisNormal.put(2, new double[] {6, 2});
		saapumisNormal.put(3, new double[] {4, 2});
		HashMap<Integer, double[]> saapumisExp = new HashMap<>();
		saapumisExp.put(1, new double[] {7});
		saapumisExp.put(2, new double[] {5});
		saapumisExp.put(3, new double[] {3});
		saapumisJakaumat = new HashMap<>();
		saapumisJakaumat.put("uniform", saapumisUniform);
		saapumisJakaumat.put("normal", saapumisNormal);
		saapumisJakaumat.put("negexp", saapumisExp);
		
	}
	/*
	private int jono;
    private int poista;

	@Override
    public void visualisoiAsiakas(int jono) {
        Platform.runLater(new Runnable(){
            public void run(){
                ui.getVisualisointi().uusiAsiakas(jono);
            }
        });
    }

    @Override
    public void poistaAsiakas(int poista) {
        Platform.runLater(new Runnable(){
            public void run(){
                ui.getVisualisointi().poistaAsiakasJonosta(poista);
            }
        });
    }
    */


	



}
