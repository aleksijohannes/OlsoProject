package testi;

import java.util.HashMap;

import controller.ISimulaatioDAO;
import controller.Simulaatio;
import controller.SimulaatioDAO;

public class TietokantaTest {

	public static void main(String[] args) {
		ISimulaatioDAO dao = new SimulaatioDAO();
		
		Simulaatio simu = new Simulaatio();
		simu.setIlmoJakauma("blaa");
		simu.setOviMaara(3);
		HashMap<String, Double> hash = new HashMap<>();
		hash.put("Blaa", 3.5);
		simu.setKayttoasteet(hash);
		dao.tallenna(simu);

	}

}
