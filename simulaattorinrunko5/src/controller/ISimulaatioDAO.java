package controller;

import java.util.List;

public interface ISimulaatioDAO {
	public boolean tallenna(Simulaatio simulaatio);
	public List<Simulaatio> haeSimulaatiot();
}
