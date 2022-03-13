package view;

/**
 * Rajapinta sisältää tiedon metodeista, jotka toteutetaan visualisoinnissa.
 * 
 * @author Aleksi Alanko
 */

public interface IVisualisointi {

	/**
	 * Tyhjentää visualisoinnin.
	 */
	
	public void tyhjennaNaytto();
	
	/**
	 * Visualisoi asiakkaan jonoon
	 * @param jono Valitsee mihin jonoon asiakas visualisoidaan.
	 */
	
	public void uusiAsiakas(int jono);
	
	/**
	 * Visualisoi palvelupisteet
	 * @param oviMaara Määrittää kuinka monta ovihenkilö-palvelupistettä luodaan
	 * @param ilmMaara Määrittää kuinka monta ilmoittautumispiste-palvelupistettä luodaan
	 * @param rokMaara Määrittää kuinka monta rokottaja-palvelupistettä luodaan
	 */
	
	public void visualisoiPalvelupisteet(int oviMaara, int ilmMaara, int rokMaara);
	
	/**
	 * Poistaa visualisoidun asiakkaan jonosta.
	 * @param poista Valitsee mistä jonosta asiakkaan visualisointi poistetaan.
	 */

	public void poistaAsiakasJonosta(int poista);
}

