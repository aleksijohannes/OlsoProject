package view;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Visualisointi extends Canvas implements IVisualisointi{

	private GraphicsContext gc;
	private SimulaattorinGUI visualisointi = new SimulaattorinGUI();
	
	double i = 0;
	double j = 10;
	double ovetPosX = 20;
	double ovetPosY = 10;
	double ilmoPosX = 40;
	double ilmoPosY = 10;
	double rokPosX = 60;
	double rokPosY = 10;
	double seurPosX = 80;
	double seurPosY = 10;
	private int oviMaara = visualisointi.getOviMaara();
	private int ilmMaara = visualisointi.getIlmoMaara();
	private int rokMaara = visualisointi.getRokMaara();
	
	public Visualisointi(int w, int h) {
		super(w, h);
		gc = this.getGraphicsContext2D();
		tyhjennaNaytto();
	}
	
	public Visualisointi(SimulaattorinGUI visualisointi) {
		this.visualisointi = visualisointi;
	}
	

	public void tyhjennaNaytto() {
		gc.setFill(Color.WHITE);
		gc.fillRect(0, 0, this.getWidth(), this.getHeight());
	}
	
	public void uusiAsiakas() {
		gc.setFill(Color.RED);
		gc.fillOval(i,j,10,10);
		
		i = (i + 10 % this.getWidth());
		//j = (j + 12) % this.getHeight();
		if (i==0) j+=10;			
	}
	
	public void poistaAsiakas() {
		gc.setFill(Color.WHITE);
		gc.fillOval(i,j,10,10);
		
		i = (i - 10 % this.getWidth());
		//j = (j + 12) % this.getHeight();
		if (i==0) j+=10;			
	}
	
	public void visualisoiPalvelupisteet() {
		for(int i = 0; i < oviMaara; i++) {
			gc.setFill(Color.BLUE);
			gc.fillRect(ovetPosX, ovetPosY, 80, 50);
			
			ovetPosY = (ovetPosY + 60) % this.getHeight();
		}
		for(int i = 0; i < ilmMaara; i++) {
			gc.setFill(Color.GREEN);
			gc.fillRect(ilmoPosX, ilmoPosY, 80, 50);
			
			ilmoPosY = (ilmoPosY + 60) % this.getHeight();
		}
		for(int i = 0; i < rokMaara; i++) {
			gc.setFill(Color.YELLOW);
			gc.fillRect(rokPosX, rokPosY, 80, 50);
			
			rokPosY = (rokPosY + 60) % this.getHeight();
		}
		gc.setFill(Color.PINK);
		gc.fillRect(seurPosX, 10, 80, 50);
	}
	
}
