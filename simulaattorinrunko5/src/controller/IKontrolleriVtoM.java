package controller;

import java.util.ArrayList;

/**
 * Rajapinta sisältää tiedon metodeista, jotka toteutetaan kontrollerissa, ja
 * joita käyttöliittymä kutsuu.
 * 
 * @author Jenni Javanainen
 */

public interface IKontrolleriVtoM {

	/**
	 * Käynnistää simuloinnin
	 */
	public void kaynnistaSimulointi();

	/**
	 * Nopeuttaa simulointia
	 */
	public void nopeuta();

	/**
	 * Hidastaa simulointia
	 */
	public void hidasta();

	/**
	 * Tallentaa uuden simulaation tietokantaan
	 */
	public void tallennaSimulaatio();

	/**
	 * Hakee ja palauttaa tallennetut simulaatiot tietokannasta
	 * 
	 * @return simulaatiot listamuodossa
	 */
	public ArrayList<Simulaatio> haeSimulaatiot();

}
