package controller;

import java.util.ArrayList;

public interface ISimulaatioDAO {
	public boolean tallenna(Simulaatio simulaatio);
	public ArrayList<Simulaatio> haeSimulaatiot();
}
