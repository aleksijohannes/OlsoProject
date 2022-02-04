package simu.model;

import eduni.distributions.Negexp;
import eduni.distributions.Normal;
import simu.framework.Kello;
import simu.framework.Moottori;
import simu.framework.Saapumisprosessi;
import simu.framework.Tapahtuma;

public class OmaMoottori extends Moottori {

	private Saapumisprosessi saapumisprosessi;
	protected Palvelupiste[] ovihenkilot;
	protected Palvelupiste[] ilmoittautumistiskit;
	protected Palvelupiste[] rokottajat;
	protected Palvelupiste[] jalkiseurannat;

	public OmaMoottori() {

		ovihenkilot = new Palvelupiste[2];
		ilmoittautumistiskit = new Palvelupiste[3];
		rokottajat = new Palvelupiste[5];
		jalkiseurannat = new Palvelupiste[2];

		ovihenkilot[1] = new Palvelupiste(new Normal(10, 6), tapahtumalista, TapahtumanTyyppi.DEP1, 1,
				PalvelupisteenTyyppi.OVI);
		ilmoittautumistiskit[1] = new Palvelupiste(new Normal(10, 6), tapahtumalista, TapahtumanTyyppi.DEP2, 1,
				PalvelupisteenTyyppi.ILMO);
		ilmoittautumistiskit[2] = new Palvelupiste(new Normal(10, 6), tapahtumalista, TapahtumanTyyppi.DEP2, 1,
				PalvelupisteenTyyppi.ILMO);
		rokottajat[1] = new Palvelupiste(new Normal(10, 6), tapahtumalista, TapahtumanTyyppi.DEP3, 1,
				PalvelupisteenTyyppi.ROK);
		rokottajat[2] = new Palvelupiste(new Normal(10, 6), tapahtumalista, TapahtumanTyyppi.DEP3, 1,
				PalvelupisteenTyyppi.ROK);
		rokottajat[3] = new Palvelupiste(new Normal(10, 6), tapahtumalista, TapahtumanTyyppi.DEP3, 1,
				PalvelupisteenTyyppi.ROK);
		rokottajat[4] = new Palvelupiste(new Normal(10, 6), tapahtumalista, TapahtumanTyyppi.DEP3, 1,
				PalvelupisteenTyyppi.ROK);
		jalkiseurannat[1] = new Palvelupiste(new Normal(10, 6), tapahtumalista, TapahtumanTyyppi.DEP4, 1,
				PalvelupisteenTyyppi.SEUR);

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
			a = palvelupisteet[0].otaJonosta();
			pieninJono(ilmoittautumistiskit).lisaaJonoon(a);
			break;
		case DEP2:
			a = palvelupisteet[1].otaJonosta();
			pieninJono(rokottajat).lisaaJonoon(a);
			break;
		case DEP3:
			a = palvelupisteet[1].otaJonosta();
			pieninJono(jalkiseurannat).lisaaJonoon(a);
			break;
		case DEP4:
			a = palvelupisteet[2].otaJonosta();
			a.setPoistumisaika(Kello.getInstance().getAika());
			a.raportti();
		}
	}

	protected Palvelupiste pieninJono(Palvelupiste[] taulukko) {
		Palvelupiste pienin = taulukko[1];
		for (int i = 2; i < taulukko.length; i++) {
			if (taulukko[i].jononKoko() < pienin.jononKoko()) {
				pienin = taulukko[i];
			}
		}
		return pienin;
	}

	@Override
	protected void tulokset() {
		System.out.println("Simulointi päättyi kello " + Kello.getInstance().getAika());
		System.out.println("Tulokset ... puuttuvat vielä");
	}

}
