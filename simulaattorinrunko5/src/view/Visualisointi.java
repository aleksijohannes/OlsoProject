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
	private double ilmJonoY = 100;
	private double rokJonoX;
	private double rokJonoY = 200;
	private double seurJonoX;
	private double seurJonoY = 300;
	private double poistuneetX = 400;
	private double poistuneetY = 10;
	private double oviPoistaAsiakas;
	private double ilmPoistaAsiakas;
	private double rokPoistaAsiakas;
	private double seurPoistaAsiakas;
	private double ovetPosX = 10;
	private double ovetPosY = 30;
	private double ilmoPosX = 10;
	private double ilmoPosY = 130;
	private double rokPosX = 10;
	private double rokPosY = 230;
	private int w;
	
	public Visualisointi(int w, int h, SimulaattorinGUI visualisointi) {
		super(w, h);
		this.w = w;
		gc = this.getGraphicsContext2D();
		tyhjennaNaytto();
	}
	
	public void tyhjennaNaytto() {
		gc.setFill(Color.WHITE);
		gc.fillRect(0, 0, this.getWidth(), this.getHeight());
	}
	
	
	public void uusiAsiakas(int jono) {
		if(jono == 1) {
		gc.setFill(Color.BLUE);
		gc.fillOval(oviJonoX,oviJonoY,10,10);
		oviJonoX = (oviJonoX + 12) % this.getWidth();
		if (oviJonoX >= w) oviJonoY+=10;	
		} 
		else if(jono == 2) {
			gc.setFill(Color.GREEN);
			gc.fillOval(ilmJonoX,ilmJonoY,10,10);
			ilmJonoX = (ilmJonoX + 12) % this.getWidth();
			if (ilmJonoX >= w) ilmJonoY+=10;
		}
		else if(jono == 3) {
			gc.setFill(Color.YELLOW);
			gc.fillOval(rokJonoX,rokJonoY,10,10);
			rokJonoX = (rokJonoX + 12) % this.getWidth();
			if (rokJonoX >= w) rokJonoY+=10;
		}
		else if(jono == 4) {
			gc.setFill(Color.PINK);
			gc.fillOval(seurJonoX,seurJonoY,10,10);
			seurJonoX = (seurJonoX + 12) % this.getWidth();
			if (seurJonoX >= w) seurJonoY+=10;
		}
		else if(jono == 5) {
			gc.setFill(Color.RED);
			gc.fillOval(poistuneetX,poistuneetY,10,10);
			poistuneetX = (poistuneetX + 12) % this.getWidth();
			if (poistuneetX >= w) poistuneetY+=10;
		}
	}
	
	public void poistaAsiakasJonosta(int poista) {
		if(poista == 1) {
		gc.setFill(Color.WHITE);
		gc.fillOval(oviPoistaAsiakas, oviJonoY,10,10);
		oviPoistaAsiakas = (oviPoistaAsiakas + 12) % this.getWidth();
		if (oviPoistaAsiakas >= w) oviJonoY+=12;			
		}
		else if (poista == 2) {
		gc.setFill(Color.WHITE);
		gc.fillOval(ilmPoistaAsiakas, ilmJonoY,10,10);
		ilmPoistaAsiakas = (ilmPoistaAsiakas + 12) % this.getWidth();
		if (ilmPoistaAsiakas >= w) ilmJonoY+=12;			
		}
		else if (poista == 3) {
			gc.setFill(Color.WHITE);
			gc.fillOval(rokPoistaAsiakas, rokJonoY,10,10);
			rokPoistaAsiakas = (rokPoistaAsiakas + 12) % this.getWidth();
			if (oviPoistaAsiakas >= w) rokJonoY+=12;			
		}
		else if (poista == 4) {
			gc.setFill(Color.WHITE);
			gc.fillOval(seurPoistaAsiakas, seurJonoY,10,10);
			seurPoistaAsiakas = (seurPoistaAsiakas + 12) % this.getWidth();
			if (seurPoistaAsiakas >= w) seurJonoY+=12;			
		}
	}
	
	public void visualisoiPalvelupisteet(int oviMaara, int ilmMaara, int rokMaara) {
		for(int i = 0; i < oviMaara; i++) {
			gc.setFill(Color.BLUE);
			gc.fillRect(ovetPosX, ovetPosY, 50, 50);
			
			ovetPosX = (ovetPosX + 70);
		}
		for(int i = 0; i < ilmMaara; i++) {
			gc.setFill(Color.GREEN);
			gc.fillRect(ilmoPosX, ilmoPosY, 50, 50);
			
			ilmoPosX = (ilmoPosX + 70);
		}
		for(int i = 0; i < rokMaara; i++) {
			gc.setFill(Color.YELLOW);
			gc.fillRect(rokPosX, rokPosY, 50, 50);
			
			rokPosX = (rokPosX + 70);
		}
		gc.setFill(Color.PINK);
		gc.fillRect(10, 330, 680, 50);
	}
	
}

