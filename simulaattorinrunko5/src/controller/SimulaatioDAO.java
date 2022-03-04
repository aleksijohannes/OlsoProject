package controller;

import java.util.List;

/**
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
			System.out.println("Toimii!");
		} catch (Exception e) {
			System.err.println("Istuntotehtaan luominen ei onnistunut. " + e.getMessage());
			System.exit(-1);
		}

	}

	public void finalize() {
		try {
			if (istuntotehdas != null)
				istuntotehdas.close();
		} catch (Exception e) {
			System.out.println("Istuntotehtaan sulkeminen epäonnistui: " + e.getMessage());
		}
	}

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

	@Override
	public List<Simulaatio> haeSimulaatiot() {
		try (Session istunto = istuntotehdas.openSession()) {
			istunto.beginTransaction();

			@SuppressWarnings("unchecked")
			List<Simulaatio> result = istunto.createQuery("from Simulaatio").list();
			istunto.getTransaction().commit();
			return result;

		} catch (Exception e) {
			if (transaktio != null)
				transaktio.rollback();
			throw e;
		}

	}

}
