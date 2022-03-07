package controller;

import java.util.ArrayList;

/**
 * Rajapinta sisältää tiedon metodeista, jotka toteutetaan SimulaatioDAO-luokassa, ja joita kontrolleri kutsuu.
 * @author Jenni Javanainen
 *
 */
public interface ISimulaatioDAO {
	public boolean tallenna(Simulaatio simulaatio);
	public ArrayList<Simulaatio> haeSimulaatiot();
}
