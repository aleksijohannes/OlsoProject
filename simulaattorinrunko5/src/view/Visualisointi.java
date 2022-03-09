package view;

import controller.Kontrolleri;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Visualisointi extends Canvas implements IVisualisointi{

	private GraphicsContext gc;

	private double oviJonoX;
	private double oviJonoY = 10;
	private double ilmJonoX;
	private double ilmJonoY = 130;
	private double rokJonoX;
	private double rokJonoY = 260;
	private double seurJonoX;
	private double seurJonoY = 400;
	private double poistuneetX = 10;
	private double poistuneetY = 530;
	private double oviPoistaAsiakas;
	private double ilmPoistaAsiakas;
	private double rokPoistaAsiakas;
	private double seurPoistaAsiakas;
	private double ovetPosX = 10;
	private double ovetPosY = 70;
	private double ilmoPosX = 10;
	private double ilmoPosY = 200;
	private double rokPosX = 10;
	private double rokPosY = 330;
	private double oviPoistoY = 10;
	private double ilmPoistoY = 130;
	private double rokPoistoY = 260;
	private double seurPoistoY = 400;
	
	public Visualisointi(int w, int h, SimulaattorinGUI visualisointi) {
		super(w, h);
		gc = this.getGraphicsContext2D();
		tyhjennaNaytto();
	}
	
	public void tyhjennaNaytto() {
		gc.setFill(Color.WHITE);
		gc.fillRect(0, 0, this.getWidth(), this.getHeight());
	}
	
	
	public void uusiAsiakas(int jono) {
		if(jono == 1) {
		gc.setFill(Color.web("#2EC4B6"));
		gc.fillOval(oviJonoX,oviJonoY,10,10);
		oviJonoX = (oviJonoX + 12) % this.getWidth();
		if (oviJonoX >= 1300) oviJonoY+=12;	
		} 
		else if(jono == 2) {
			gc.setFill(Color.web("#009FB7"));
			gc.fillOval(ilmJonoX,ilmJonoY,10,10);
			ilmJonoX = (ilmJonoX + 12) % this.getWidth();
			if (ilmJonoX >= 1300) ilmJonoY+=12;
		}
		else if(jono == 3) {
			gc.setFill(Color.web("#FF9F1C"));
			gc.fillOval(rokJonoX,rokJonoY,10,10);
			rokJonoX = (rokJonoX + 12) % this.getWidth();
			if (rokJonoX >= 1300) rokJonoY+=12;
		}
		else if(jono == 4) {
			gc.setFill(Color.web("#5C5D8D"));
			gc.fillOval(seurJonoX,seurPoistoY,10,10);
			seurJonoX = (seurJonoX + 12) % this.getWidth();
			if (seurJonoX >= 1300) seurPoistoY+=12;
		}
		else if(jono == 5) {
			gc.setFill(Color.web("#D282A6"));
			gc.fillOval(poistuneetX,poistuneetY,10,10);
			poistuneetX = (poistuneetX + 12) % this.getWidth();
			if (poistuneetX >= 1300) poistuneetY+=12;
		}
	}
	
	public void poistaAsiakasJonosta(int poista) {
		if(poista == 1) {
		gc.setFill(Color.WHITE);
		gc.fillOval(oviPoistaAsiakas, oviPoistoY,10,10);
		oviPoistaAsiakas = (oviPoistaAsiakas + 12) % this.getWidth();
		if (oviPoistaAsiakas >= 1300) oviPoistoY+=12;			
		}
		else if (poista == 2) {
		gc.setFill(Color.WHITE);
		gc.fillOval(ilmPoistaAsiakas, ilmPoistoY,10,10);
		ilmPoistaAsiakas = (ilmPoistaAsiakas + 12) % this.getWidth();
		if (ilmPoistaAsiakas >= 1300) ilmPoistoY+=12;			
		}
		else if (poista == 3) {
			gc.setFill(Color.WHITE);
			gc.fillOval(rokPoistaAsiakas, rokPoistoY,10,10);
			rokPoistaAsiakas = (rokPoistaAsiakas + 12) % this.getWidth();
			if (oviPoistaAsiakas >= 1300) rokPoistoY+=12;			
		}
		else if (poista == 4) {
			gc.setFill(Color.WHITE);
			gc.fillOval(seurPoistaAsiakas, seurJonoY,10,10);
			seurPoistaAsiakas = (seurPoistaAsiakas + 12) % this.getWidth();
			if (seurPoistaAsiakas >= 1300) seurJonoY+=12;			
		}
	}
	
	public void visualisoiPalvelupisteet(int oviMaara, int ilmMaara, int rokMaara) {
		for(int i = 0; i < oviMaara; i++) {
			gc.setFill(Color.web("#2EC4B6"));
			gc.fillRect(ovetPosX, ovetPosY, 50, 50);
			
			ovetPosX = (ovetPosX + 70);
		}
		for(int i = 0; i < ilmMaara; i++) {
			gc.setFill(Color.web("#009FB7"));
			gc.fillRect(ilmoPosX, ilmoPosY, 50, 50);
			
			ilmoPosX = (ilmoPosX + 70);
		}
		for(int i = 0; i < rokMaara; i++) {
			gc.setFill(Color.web("#FF9F1C"));
			gc.fillRect(rokPosX, rokPosY, 50, 50);
			
			rokPosX = (rokPosX + 70);
		}
		gc.setFill(Color.web("#5C5D8D"));
		gc.fillRect(10, 460, 50, 50);
	}
	
}

