package controller;

import java.util.ArrayList;

/**
 * Rajapinta sisältää tiedon metodeista, jotka toteutetaan
 * SimulaatioDAO-luokassa, ja joita kontrolleri kutsuu.
 * 
 * @author Jenni Javanainen
 *
 */
public interface ISimulaatioDAO {

	/**
	 * Tallentaa parametrina saadun simulaatio-olion tietokantaan
	 * 
	 * @param simulaatio tallennettava simulaatio
	 * @return jos tallentaminen onnistui, palauttaa true
	 */

	public boolean tallenna(Simulaatio simulaatio);

	/**
	 * Hakee ja palauttaa tietokantaan tallennetut simulaatio-oliot
	 * 
	 * @return tietokannasta haetut simulaatio-oliot listamuodossa
	 */
	public ArrayList<Simulaatio> haeSimulaatiot();
}
