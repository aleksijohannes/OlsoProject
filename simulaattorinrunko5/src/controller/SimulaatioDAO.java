package controller;

import java.util.ArrayList;
import java.util.List;

/**
 * Luokka toimii välittäjänä SQL-tietokannan ja kontrollerin välillä. SQL-tietokantaan tallennetaan Simulaatio-olioita, ja sieltä 
 * pystytään hakemaan kaikki Simulaatio-oliot listamuodossa.
 * 
 * 
 * @author Jenni Javanainen
 */

import org.hibernate.*;
import org.hibernate.cfg.Configuration;

public class SimulaatioDAO implements ISimulaatioDAO {

	private SessionFactory istuntotehdas = null;
	private Transaction transaktio = null;

	public SimulaatioDAO() {

		try {
			istuntotehdas = new Configuration().configure().buildSessionFactory();
			System.out.println("Istuntotehdas luotu onnistuneesti");
		} catch (Exception e) {
			System.err.println("Istuntotehtaan luominen ei onnistunut. " + e.getMessage());
			System.exit(-1);
		}

	}

	/**
	 * Destruktori, joka huolehtii istuntotehtaan sulkemisesta.
	 */
	@Override
	public void finalize() {
		try {
			if (istuntotehdas != null)
				istuntotehdas.close();
		} catch (Exception e) {
			System.out.println("Istuntotehtaan sulkeminen epäonnistui: " + e.getMessage());
		}
	}

	/**
	 * Luo istunnon ja tallentaa parametrina annetun Simulaatio-olion tietokantaan. Mikäli oliota ei pystytä tallentamaan kokonaan, ei tallenneta mitään.
	 * Heittää poikkeuksen, jos tallennus ei onnistu.
	 * @param simulaatio tallennettava simulaatio-olio 
	 * @return palauttaa true mikäli tallennus onnistui
	 */
	@Override
	public boolean tallenna(Simulaatio simulaatio) {
		try (Session istunto = istuntotehdas.openSession()) {
			istunto.beginTransaction();

			istunto.save(simulaatio);

			istunto.getTransaction().commit();
			return true;
		} catch (Exception e) {
			if (transaktio != null)
				transaktio.rollback();
			throw e;
		}

	}

	/**
	 * Luo istunnon ja hakee tietokannasta kaikki Simulaatio-oliot ja palauttaa ne listamuodossa. Mikäli haku ei onnistu kokonaan, ei haeta mitään.
	 * Heittää poikkeuksen, jos haku ei onnistu.
	 * @return palauttaa Simulaatio-oliot sisälätävän ArrayListin
	 */
	@Override
	public ArrayList<Simulaatio> haeSimulaatiot() {
		try (Session istunto = istuntotehdas.openSession()) {
			istunto.beginTransaction();

			@SuppressWarnings("unchecked")
			List<Simulaatio> result = istunto.createQuery("from Simulaatio").list();
			istunto.getTransaction().commit();
			ArrayList<Simulaatio> simulaatiot = new ArrayList<>(result);
			return simulaatiot;

		} catch (Exception e) {
			if (transaktio != null)
				transaktio.rollback();
			throw e;
		}

	}

}
