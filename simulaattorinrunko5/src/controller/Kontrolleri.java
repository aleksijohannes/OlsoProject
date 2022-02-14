package controller;

import java.util.HashMap;

import eduni.distributions.Generator;
import eduni.distributions.Negexp;
import eduni.distributions.Normal;
import eduni.distributions.Uniform;
import javafx.application.Platform;
import simu.framework.IMoottori;
import simu.model.OmaMoottori;
import view.ISimulaattorinUI;

public class Kontrolleri implements IKontrolleriVtoM, IKontrolleriMtoV{   // UUSI
	
	private IMoottori moottori; 
	private ISimulaattorinUI ui;
	
	private HashMap<String, HashMap<Integer, double[]>> oviJakaumat;
	private HashMap<String, HashMap<Integer, double[]>> ilmoJakaumat;
	private HashMap<String, HashMap<Integer, double[]>> rokJakaumat;
	private HashMap<String, HashMap<Integer, double[]>> seurJakaumat;
	private HashMap<String, HashMap<Integer, double[]>> saapumisJakaumat;
	
	private long seed;
	
	public Kontrolleri(ISimulaattorinUI ui) {
		this.ui = ui;
		seed = System.currentTimeMillis();
		alustaJakaumat();
	}
		
	// Moottorin ohjausta:
		
	@Override
	public void kaynnistaSimulointi() {
		moottori = new OmaMoottori(this); // luodaan uusi moottorisäie jokaista simulointia varten
		moottori.setSimulointiaika(ui.getAika());
		moottori.setViive(ui.getViive());
		ui.getVisualisointi().tyhjennaNaytto();
		((Thread)moottori).start();
	}
	
	@Override
	public void hidasta() { // hidastetaan moottorisäiettä
		moottori.setViive((long)(moottori.getViive()*1.10));
	}

	@Override
	public void nopeuta() { // nopeutetaan moottorisäiettä
		moottori.setViive((long)(moottori.getViive()*0.9));
	}
	
	
	
	// Simulointitulosten välittämistä käyttöliittymään.
	// Koska FX-ui:n päivitykset tulevat moottorisäikeestä, ne pitää ohjata JavaFX-säikeeseen:
		
	@Override
	public void naytaLoppuaika(double aika) {
		Platform.runLater(()->ui.setLoppuaika(aika)); 
	}

	
	@Override
	public void visualisoiAsiakas() {
		Platform.runLater(new Runnable(){
			public void run(){
				ui.getVisualisointi().uusiAsiakas();
			}
		});
	}


	@Override
	public int getOviMaara() {
		return ui.getOviMaara();
	}


	@Override
	public int getIlmoMaara() {
		return ui.getIlmoMaara();
	}


	@Override
	public int getRokMaara() {
		return ui.getRokMaara();
	}

	
	public Generator valitseJakauma(HashMap<String, HashMap<Integer, double[]>> jakaumataulu, String jakauma, int nopeus) {
		double[] taulukko = jakaumataulu.get(jakauma).get(nopeus);
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

	@Override
	public Generator getOviJakauma() {
		return valitseJakauma(oviJakaumat, ui.getOviJakauma(), ui.getOviPalvelunopeus());
	}

	@Override
	public Generator getIlmoJakauma() {
		return valitseJakauma(ilmoJakaumat, ui.getIlmoJakauma(), ui.getIlmoPalvelunopeus());
	}

	@Override
	public Generator getRokJakauma() {
		return valitseJakauma(rokJakaumat, ui.getRokJakauma(), ui.getRokPalvelunopeus());
	}

	@Override
	public Generator getSeurJakauma() {
		return valitseJakauma(seurJakaumat, ui.getSeurJakauma(), ui.getSeurPalvelunopeus());
	}

	@Override
	public Generator getSaapumisjakauma() {
		return valitseJakauma(saapumisJakaumat, ui.getSaapumisjakauma(), ui.getSaapumistiheys());
	}
	
	// Eri jakaumien raja-arvojen alustus
	public void alustaJakaumat() {
		HashMap<Integer, double[]> oviUniform = new HashMap<>();
		oviUniform.put(1, new double[] {2,4});
		oviUniform.put(2, new double[] {1.5,3});
		oviUniform.put(3, new double[] {1,1.5});
		HashMap<Integer, double[]> oviNormal = new HashMap<>();
		oviNormal.put(1, new double[] {3,1});
		oviNormal.put(2, new double[] {2.5,1});
		oviNormal.put(3, new double[] {2,1});
		HashMap<Integer, double[]> oviExp = new HashMap<>();
		oviExp.put(1, new double[] {3});
		oviExp.put(2, new double[] {2});
		oviExp.put(3, new double[] {1});
		oviJakaumat = new HashMap<>();
		oviJakaumat.put("uniform", oviUniform);
		oviJakaumat.put("normal", oviNormal);
		oviJakaumat.put("negexp", oviExp);
		
		HashMap<Integer, double[]> ilmoUniform = new HashMap<>();
		ilmoUniform.put(1, new double[] {3,5});
		ilmoUniform.put(2, new double[] {1.5,3});
		ilmoUniform.put(3, new double[] {1,2});
		HashMap<Integer, double[]> ilmoNormal = new HashMap<>();
		ilmoNormal.put(1, new double[] {4,2});
		ilmoNormal.put(2, new double[] {3,1});
		ilmoNormal.put(3, new double[] {2.5,1});
		HashMap<Integer, double[]> ilmoExp = new HashMap<>();
		ilmoExp.put(1, new double[] {4});
		ilmoExp.put(2, new double[] {3});
		ilmoExp.put(3, new double[] {2});
		ilmoJakaumat = new HashMap<>();
		ilmoJakaumat.put("uniform", ilmoUniform);
		ilmoJakaumat.put("normal", ilmoNormal);
		ilmoJakaumat.put("negexp", ilmoExp);
		
		HashMap<Integer, double[]> rokUniform = new HashMap<>();
		rokUniform.put(1, new double[] {5,10});
		rokUniform.put(2, new double[] {4,8});
		rokUniform.put(3, new double[] {3,6});
		HashMap<Integer, double[]> rokNormal = new HashMap<>();
		rokNormal.put(1, new double[] {9,3});
		rokNormal.put(2, new double[] {7,3});
		rokNormal.put(3, new double[] {6,3});
		HashMap<Integer, double[]> rokExp = new HashMap<>();
		rokExp.put(1, new double[] {9});
		rokExp.put(2, new double[] {7});
		rokExp.put(3, new double[] {5});
		rokJakaumat = new HashMap<>();
		rokJakaumat.put("uniform", rokUniform);
		rokJakaumat.put("normal", rokNormal);
		rokJakaumat.put("negexp", rokExp);
		
		HashMap<Integer, double[]> seurUniform = new HashMap<>();
		seurUniform.put(1, new double[] {15,16});
		seurUniform.put(2, new double[] {14.5,15.5});
		seurUniform.put(3, new double[] {14,15});
		HashMap<Integer, double[]> seurNormal = new HashMap<>();
		seurNormal.put(1, new double[] {15.5,1});
		seurNormal.put(2, new double[] {15,1});
		seurNormal.put(3, new double[] {14.5,1});
		HashMap<Integer, double[]> seurExp = new HashMap<>();
		seurExp.put(1, new double[] {16});
		seurExp.put(2, new double[] {15});
		seurExp.put(3, new double[] {14});
		seurJakaumat = new HashMap<>();
		seurJakaumat.put("uniform", seurUniform);
		seurJakaumat.put("normal", seurNormal);
		seurJakaumat.put("negexp", seurExp);
		
		HashMap<Integer, double[]> saapumisUniform = new HashMap<>();
		saapumisUniform.put(1, new double[] {4,7});
		saapumisUniform.put(2, new double[] {3,6});
		saapumisUniform.put(3, new double[] {2,5});
		HashMap<Integer, double[]> saapumisNormal = new HashMap<>();
		saapumisNormal.put(1, new double[] {7,2});
		saapumisNormal.put(2, new double[] {5,2});
		saapumisNormal.put(3, new double[] {4,1});
		HashMap<Integer, double[]> saapumisExp = new HashMap<>();
		saapumisExp.put(1, new double[] {7});
		saapumisExp.put(2, new double[] {5});
		saapumisExp.put(3, new double[] {3});
		saapumisJakaumat = new HashMap<>();
		saapumisJakaumat.put("uniform", saapumisUniform);
		saapumisJakaumat.put("normal", saapumisNormal);
		saapumisJakaumat.put("negexp", saapumisExp);
		
	}
	



}
