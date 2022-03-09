package view;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;

import controller.*;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import simu.framework.Trace;
import simu.framework.Trace.Level;
import javafx.scene.*;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.*;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.*;

public class SimulaattorinGUI extends Application implements ISimulaattorinUI {

	// Kontrollerin esittely (tarvitaan käyttöliittymässä)
	private IKontrolleriVtoM kontrolleri;

	// Käyttöliittymäkomponentit:
	private TextField aika;
	private TextField viive;
	private Label tulos;
	private Label lapimenoaika;
	private Label lapimenoaikaLabel;
	private Label aikaLabel;
	private Label viiveLabel;
	private Label tulosLabel;

	private Button kaynnistaButton;
	private Button hidastaButton;
	private Button nopeutaButton;

	private IVisualisointi naytto;

	private int ovihenkilot;
	private int ilmoittautumispisteet;
	private int rokottajat;

	private int nopeusSaapuminen;
	private String jakaumaSaapuminen;

	private int nopeusOvi;
	private String jakaumaOvi;

	private int nopeusIlm;
	private String jakaumaIlm;

	private int nopeusRokottaja;
	private String jakaumaRokottaja;

	private int nopeusSeuranta;
	private String jakaumaSeuranta;

	private TilePane jakaumatSaapuminen;
	private ToggleGroup saapuminenGroup;
	private RadioButton saapuminenNorm;
	private RadioButton saapuminenTasa;
	private RadioButton saapuminenExp;

	private TilePane jakaumatOvi;
	private ToggleGroup oviGroup;
	private RadioButton oviNorm;
	private RadioButton oviTasa;
	private RadioButton oviExp;

	private TilePane jakaumatIlm;
	private ToggleGroup ilmGroup;
	private RadioButton ilmNorm;
	private RadioButton ilmTasa;
	private RadioButton ilmExp;

	private TilePane jakaumatRokottaja;
	private ToggleGroup rokottajaGroup;
	private RadioButton rokottajaNorm;
	private RadioButton rokottajaTasa;
	private RadioButton rokottajaExp;

	private TilePane jakaumatSeuranta;
	private ToggleGroup seurantaGroup;
	private RadioButton seurantaNorm;
	private RadioButton seurantaTasa;
	//private RadioButton seurantaExp;

	private TilePane saapuminenNopeus;
	private ToggleGroup saapuminenNopeusGroup;
	private RadioButton saapuminenHidas;
	private RadioButton saapuminenNormaali;
	private RadioButton saapuminenNopea;

	private TilePane oviNopeus;
	private ToggleGroup oviNopeusGroup;
	private RadioButton oviHidas;
	private RadioButton oviNormaali;
	private RadioButton oviNopea;

	private TilePane ilmNopeus;
	private ToggleGroup ilmNopeusGroup;
	private RadioButton ilmHidas;
	private RadioButton ilmNormaali;
	private RadioButton ilmNopea;

	private TilePane rokottajaNopeus;
	private ToggleGroup rokottajaNopeusGroup;
	private RadioButton rokottajaHidas;
	private RadioButton rokottajaNormaali;
	private RadioButton rokottajaNopea;

	private TilePane seurantaNopeus;
	private ToggleGroup seurantaNopeusGroup;
	private RadioButton seurantaHidas;
	private RadioButton seurantaNormaali;
	private RadioButton seurantaNopea;

	private Slider ovihenkiloMaara;
	private Slider ilmoittautuminenMaara;
	private Slider rokottajaMaara;
	
	private Label saapuminenLabel;
	private Label oviLabel;
	private Label ilmLabel;
	private Label rokotusLabel;
	private Label seurantaLabel;
	private Label maaraLabel;
	private Label nopeusLabel;
	private Label jakaumaLabel;
	private Label palvelupisteetLabel;
	private Label seurantaMaara;
	
	private Text kayttoasteet;
	private Text suoritustehot;
	private Label palvellutAsiakkaat;
	private Text loppuaika;
	private Text suurin;
	private Text pienin;
	private Text saap;
	private Text ovi;
	private Text ilm;
	private Text rok;
	private Text seur;
	private Text avg;
	private Text max;
	private Text maara;
	private Text nopeus;
	private Text jakauma;
	
	private Text saapKesk;
	private Text ilmKesk;
	private Text oviKesk;
	private Text rokKesk;
	private Text seurKesk;
	private Text saapSuur;
	private Text ilmSuur;
	private Text oviSuur;
	private Text rokSuur;
	private Text seurSuur;
	
	private Text saapMaara;
	private Text saapNopeus;
	private Text saapJakauma;
	private Text ovMaara;
	private Text ovNopeus;
	private Text ovJakauma;
	private Text ilmoMaara;
	private Text ilmoNopeus;
	private Text ilmoJakauma;
	private Text rokMaara;
	private Text rokNopeus;
	private Text rokJakauma;
	private Text seurMaara;
	private Text seurNopeus;
	private Text seurJakauma;
	
	private Label palvelupisteetOvi;
	private Label palvelupisteetIlm;
	private Label palvelupisteetRok;
	private Label palvelupisteetSeur;
	private Label poistuneet;
	
	@Override
	public void init() {

		Trace.setTraceLevel(Level.INFO);

		kontrolleri = new Kontrolleri(this);
		setAlkuarvot();
	}

	@Override
	public void start(Stage primaryStage) {
		// Käyttöliittymän rakentaminen
		try {

			primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
				@Override
				public void handle(WindowEvent t) {
					Platform.exit();
					System.exit(0);
				}
			});

			primaryStage.setTitle("Simulaattori");

			kaynnistaButton = new Button();
			kaynnistaButton.setText("Käynnistä simulointi");
			kaynnistaButton.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {					
					setOviMaara();
					setIlmoMaara();
					setRokMaara();
					
					kontrolleri.kaynnistaSimulointi();
							
					naytto.visualisoiPalvelupisteet(getOviMaara(), getIlmoMaara(), getRokMaara());	
					
					System.out.println(getSaapumisjakauma());
					System.out.println(getOviJakauma());
					System.out.println(getIlmoJakauma());
					System.out.println(getRokJakauma());
					System.out.println(getSeurJakauma());
										
					System.out.println(getOviMaara());
					System.out.println(getIlmoMaara());
					System.out.println(getRokMaara());
					disableValinnat();
				}
			});

			// Napit
			
			hidastaButton = new Button();
			hidastaButton.setText("Hidasta");
			hidastaButton.setOnAction(e -> kontrolleri.hidasta());

			nopeutaButton = new Button();
			nopeutaButton.setText("Nopeuta");
			nopeutaButton.setOnAction(e -> kontrolleri.nopeuta());

			// Jakaumat
			
			jakaumatSaapuminen = new TilePane();

			saapuminenGroup = new ToggleGroup();
			saapuminenNorm = new RadioButton("Normaali");
			saapuminenNorm.setToggleGroup(saapuminenGroup);

			saapuminenNorm.setUserData("normal");
		
			saapuminenTasa = new RadioButton("Tasa");
			saapuminenTasa.setToggleGroup(saapuminenGroup);
			saapuminenTasa.setUserData("uniform");

			saapuminenExp = new RadioButton("Exp");
			saapuminenExp.setToggleGroup(saapuminenGroup);
			saapuminenExp.setUserData("negexp");
			saapuminenExp.setSelected(true);

			jakaumatSaapuminen.getChildren().add(saapuminenNorm);
			jakaumatSaapuminen.getChildren().add(saapuminenTasa);
			jakaumatSaapuminen.getChildren().add(saapuminenExp);
			
			saapuminenGroup.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
			      public void changed(ObservableValue<? extends Toggle> ov,
			          Toggle old_toggle, Toggle new_toggle) {
			        if (saapuminenGroup.getSelectedToggle() != null) {
			          jakaumaSaapuminen = saapuminenGroup.getSelectedToggle().getUserData().toString();
			          System.out.println(jakaumaSaapuminen + "testi 1");
			        }
			      }
			    });

			jakaumatOvi = new TilePane();

			oviGroup = new ToggleGroup();
			oviNorm = new RadioButton("Normaali");
			oviNorm.setToggleGroup(oviGroup);

			oviNorm.setUserData("normal");
			oviNorm.setSelected(true);

			oviTasa = new RadioButton("Tasa");
			oviTasa.setToggleGroup(oviGroup);
			oviTasa.setUserData("uniform");

			oviExp = new RadioButton("Exp");
			oviExp.setToggleGroup(oviGroup);
			oviExp.setUserData("negexp");

			jakaumatOvi.getChildren().add(oviNorm);
			jakaumatOvi.getChildren().add(oviTasa);
			jakaumatOvi.getChildren().add(oviExp);
			
			oviGroup.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
			      public void changed(ObservableValue<? extends Toggle> ov,
			          Toggle old_toggle, Toggle new_toggle) {
			        if (oviGroup.getSelectedToggle() != null) {
			          jakaumaOvi = oviGroup.getSelectedToggle().getUserData().toString();
			          System.out.println(jakaumaOvi + "testi 1");
			        }
			      }
			    });

			jakaumatIlm = new TilePane();

			ilmGroup = new ToggleGroup();
			ilmNorm = new RadioButton("Normaali");
			ilmNorm.setToggleGroup(ilmGroup);

			ilmNorm.setUserData("normal");
			ilmNorm.setSelected(true);

			ilmTasa = new RadioButton("Tasa");
			ilmTasa.setToggleGroup(ilmGroup);
			ilmTasa.setUserData("uniform");

			ilmExp = new RadioButton("Exp");
			ilmExp.setToggleGroup(ilmGroup);
			ilmExp.setUserData("negexp");

			jakaumatIlm.getChildren().add(ilmNorm);
			jakaumatIlm.getChildren().add(ilmTasa);
			jakaumatIlm.getChildren().add(ilmExp);
			
			ilmGroup.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
			      public void changed(ObservableValue<? extends Toggle> ov,
			          Toggle old_toggle, Toggle new_toggle) {
			        if (ilmGroup.getSelectedToggle() != null) {
			          jakaumaIlm = ilmGroup.getSelectedToggle().getUserData().toString();
			          System.out.println(jakaumaIlm + "testi 1");
			        }
			      }
			    });

			jakaumatRokottaja = new TilePane();

			rokottajaGroup = new ToggleGroup();
			rokottajaNorm = new RadioButton("Normaali");
			rokottajaNorm.setToggleGroup(rokottajaGroup);

			rokottajaNorm.setUserData("normal");
			rokottajaNorm.setSelected(true);

			rokottajaTasa = new RadioButton("Tasa");
			rokottajaTasa.setToggleGroup(rokottajaGroup);
			rokottajaTasa.setUserData("uniform");

			rokottajaExp = new RadioButton("Exp");
			rokottajaExp.setToggleGroup(rokottajaGroup);
			rokottajaExp.setUserData("negexp");

			jakaumatRokottaja.getChildren().add(rokottajaNorm);
			jakaumatRokottaja.getChildren().add(rokottajaTasa);
			jakaumatRokottaja.getChildren().add(rokottajaExp);
			
			rokottajaGroup.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
			      public void changed(ObservableValue<? extends Toggle> ov,
			          Toggle old_toggle, Toggle new_toggle) {
			        if (rokottajaGroup.getSelectedToggle() != null) {
			          jakaumaRokottaja = rokottajaGroup.getSelectedToggle().getUserData().toString();
			          System.out.println(jakaumaRokottaja + "testi 1");
			        }
			      }
			    });

			jakaumatSeuranta = new TilePane();

			seurantaGroup = new ToggleGroup();
			seurantaNorm = new RadioButton("Normaali");
			seurantaNorm.setToggleGroup(seurantaGroup);

			seurantaNorm.setUserData("normal");
			seurantaNorm.setSelected(true);

			seurantaTasa = new RadioButton("Tasa");
			seurantaTasa.setToggleGroup(seurantaGroup);
			seurantaTasa.setUserData("uniform");

			//seurantaExp = new RadioButton("Exp");
			//seurantaExp.setToggleGroup(seurantaGroup);
			//seurantaExp.setUserData("negexp");

			jakaumatSeuranta.getChildren().add(seurantaNorm);
			jakaumatSeuranta.getChildren().add(seurantaTasa);
			//jakaumatSeuranta.getChildren().add(seurantaExp);
			
			seurantaGroup.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
			      public void changed(ObservableValue<? extends Toggle> ov,
			          Toggle old_toggle, Toggle new_toggle) {
			        if (seurantaGroup.getSelectedToggle() != null) {
			          jakaumaSeuranta = seurantaGroup.getSelectedToggle().getUserData().toString();
			          System.out.println(jakaumaSeuranta + "testi 1");
			        }
			      }
			    });

			// Nopeudet
			
			saapuminenNopeus = new TilePane();

			saapuminenNopeusGroup = new ToggleGroup();

			saapuminenHidas = new RadioButton("Hidas");
			saapuminenHidas.setToggleGroup(saapuminenNopeusGroup);			
			saapuminenHidas.setUserData(1);

			saapuminenNormaali = new RadioButton("Normaali");
			saapuminenNormaali.setToggleGroup(saapuminenNopeusGroup);
			saapuminenNormaali.setUserData(2);
			saapuminenNormaali.setSelected(true);
			
			saapuminenNopea = new RadioButton("Nopea");
			saapuminenNopea.setToggleGroup(saapuminenNopeusGroup);
			saapuminenNopea.setUserData(3);

			saapuminenNopeus.getChildren().add(saapuminenHidas);
			saapuminenNopeus.getChildren().add(saapuminenNormaali);
			saapuminenNopeus.getChildren().add(saapuminenNopea);
			
			saapuminenNopeusGroup.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
			      public void changed(ObservableValue<? extends Toggle> ov,
			          Toggle old_toggle, Toggle new_toggle) {
			        if (saapuminenNopeusGroup.getSelectedToggle() != null) {
			          nopeusSaapuminen = (int)saapuminenNopeusGroup.getSelectedToggle().getUserData();
			        }
			      }
			    });

			oviNopeus = new TilePane();

			oviNopeusGroup = new ToggleGroup();

			oviHidas = new RadioButton("Hidas");
			oviHidas.setToggleGroup(oviNopeusGroup);			
			oviHidas.setUserData(1);

			oviNormaali = new RadioButton("Normaali");
			oviNormaali.setToggleGroup(oviNopeusGroup);
			oviNormaali.setUserData(2);
			oviNormaali.setSelected(true);
			
			oviNopea = new RadioButton("Nopea");
			oviNopea.setToggleGroup(oviNopeusGroup);
			oviNopea.setUserData(3);

			oviNopeus.getChildren().add(oviHidas);
			oviNopeus.getChildren().add(oviNormaali);
			oviNopeus.getChildren().add(oviNopea);
			
			oviNopeusGroup.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
			      public void changed(ObservableValue<? extends Toggle> ov,
			          Toggle old_toggle, Toggle new_toggle) {
			        if (oviNopeusGroup.getSelectedToggle() != null) {
			          nopeusOvi = (int)oviNopeusGroup.getSelectedToggle().getUserData();
			        }
			      }
			    });

			ilmNopeus = new TilePane();

			ilmNopeusGroup = new ToggleGroup();

			ilmHidas = new RadioButton("Hidas");
			ilmHidas.setToggleGroup(ilmNopeusGroup);			
			ilmHidas.setUserData(1);

			ilmNormaali = new RadioButton("Normaali");
			ilmNormaali.setToggleGroup(ilmNopeusGroup);
			ilmNormaali.setUserData(2);
			ilmNormaali.setSelected(true);

			ilmNopea = new RadioButton("Nopea");
			ilmNopea.setToggleGroup(ilmNopeusGroup);
			ilmNopea.setUserData(3);

			ilmNopeus.getChildren().add(ilmHidas);
			ilmNopeus.getChildren().add(ilmNormaali);
			ilmNopeus.getChildren().add(ilmNopea);
			
			ilmNopeusGroup.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
			      public void changed(ObservableValue<? extends Toggle> ov,
			          Toggle old_toggle, Toggle new_toggle) {
			        if (ilmNopeusGroup.getSelectedToggle() != null) {
			          nopeusIlm = (int)ilmNopeusGroup.getSelectedToggle().getUserData();
			        }
			      }
			    });

			rokottajaNopeus = new TilePane();

			rokottajaNopeusGroup = new ToggleGroup();

			rokottajaHidas = new RadioButton("Hidas");
			rokottajaHidas.setToggleGroup(rokottajaNopeusGroup);			
			rokottajaHidas.setUserData(1);

			rokottajaNormaali = new RadioButton("Normaali");
			rokottajaNormaali.setToggleGroup(rokottajaNopeusGroup);
			rokottajaNormaali.setUserData(2);
			rokottajaNormaali.setSelected(true);

			rokottajaNopea = new RadioButton("Nopea");
			rokottajaNopea.setToggleGroup(rokottajaNopeusGroup);
			rokottajaNopea.setUserData(3);

			rokottajaNopeus.getChildren().add(rokottajaHidas);
			rokottajaNopeus.getChildren().add(rokottajaNormaali);
			rokottajaNopeus.getChildren().add(rokottajaNopea);

			rokottajaNopeusGroup.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
				public void changed(ObservableValue<? extends Toggle> ov, Toggle old_toggle, Toggle new_toggle) {
					if ((int) rokottajaNopeusGroup.getSelectedToggle().getUserData() == 1) {
						nopeusRokottaja = 1;
					} else if ((int) rokottajaNopeusGroup.getSelectedToggle().getUserData() == 2) {
						nopeusRokottaja = 2;
					} else if ((int) rokottajaNopeusGroup.getSelectedToggle().getUserData() == 3) {
						nopeusRokottaja = 3;
					}
				}
			});

			seurantaNopeus = new TilePane();

			seurantaNopeusGroup = new ToggleGroup();

			seurantaHidas = new RadioButton("Hidas");
			seurantaHidas.setToggleGroup(seurantaNopeusGroup);			
			seurantaHidas.setUserData(1);

			seurantaNormaali = new RadioButton("Normaali");
			seurantaNormaali.setToggleGroup(seurantaNopeusGroup);
			seurantaNormaali.setUserData(2);
			seurantaNormaali.setSelected(true);

			seurantaNopea = new RadioButton("Nopea");
			seurantaNopea.setToggleGroup(seurantaNopeusGroup);
			seurantaNopea.setUserData(3);

			seurantaNopeus.getChildren().add(seurantaHidas);
			seurantaNopeus.getChildren().add(seurantaNormaali);
			seurantaNopeus.getChildren().add(seurantaNopea);
			
			seurantaNopeusGroup.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
			      public void changed(ObservableValue<? extends Toggle> ov,
			          Toggle old_toggle, Toggle new_toggle) {
			        if (seurantaNopeusGroup.getSelectedToggle() != null) {
			          nopeusSeuranta = (int)seurantaNopeusGroup.getSelectedToggle().getUserData();
			        }
			      }
			    });


			// Palvelupisteiden määrät
			
			ovihenkiloMaara = new Slider(1, 3, 1);

			ovihenkiloMaara.setSnapToTicks(true);
			ovihenkiloMaara.setMajorTickUnit(1);
			ovihenkiloMaara.setMinorTickCount(1);
			ovihenkiloMaara.setShowTickLabels(true);

			ilmoittautuminenMaara = new Slider(1, 4, 1);

			ilmoittautuminenMaara.setSnapToTicks(true);
			ilmoittautuminenMaara.setMajorTickUnit(1);
			ilmoittautuminenMaara.setMinorTickCount(1);
			ilmoittautuminenMaara.setShowTickLabels(true);

			rokottajaMaara = new Slider(1, 9, 1);

			rokottajaMaara.setSnapToTicks(true);
			rokottajaMaara.setMajorTickUnit(1);
			rokottajaMaara.setMinorTickCount(1);
			rokottajaMaara.setShowTickLabels(true);

			// Asettelu
			
			aikaLabel = new Label("Simulointiaika:");
			aikaLabel.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
			aika = new TextField("Syötä aika");
			aika.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
			aika.setPrefWidth(150);

			viiveLabel = new Label("Viive:");
			viiveLabel.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
			viive = new TextField("Syötä viive");
			viive.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
			viive.setPrefWidth(150);

			tulosLabel = new Label("Kokonaisaika:");
			tulosLabel.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
			tulos = new Label();
			tulos.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
			tulos.setPrefWidth(150);
			
			lapimenoaikaLabel = new Label("Läpimenoaika");
			lapimenoaikaLabel.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
			lapimenoaikaLabel.setPrefWidth(150);
			lapimenoaika = new Label();
			lapimenoaika.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
			lapimenoaika.setPrefWidth(150);					
			
			saapuminenLabel = new Label("Saapuminen");
			saapuminenLabel.setFont(Font.font("Tahoma", FontWeight.BOLD, 17));
			saapuminenLabel.setPrefWidth(150);
			saapuminenLabel.setTextFill(Color.web("#417B5A"));
			oviLabel = new Label("Ovihenkilöt");
			oviLabel.setFont(Font.font("Tahoma", FontWeight.BOLD, 17));
			oviLabel.setPrefWidth(150);
			oviLabel.setTextFill(Color.web("#2EC4B6"));
			ilmLabel = new Label("Ilmoittautumispisteet");
			ilmLabel.setFont(Font.font("Tahoma", FontWeight.BOLD, 17));
			ilmLabel.setPrefWidth(150);
			ilmLabel.setTextFill(Color.web("#009FB7"));
			rokotusLabel = new Label("Rokottajat");
			rokotusLabel.setFont(Font.font("Tahoma", FontWeight.BOLD, 17));
			rokotusLabel.setPrefWidth(150);
			rokotusLabel.setTextFill(Color.web("#FF9F1C"));
			seurantaLabel = new Label("Jälkiseuranta");
			seurantaLabel.setFont(Font.font("Tahoma", FontWeight.BOLD, 17));
			seurantaLabel.setPrefWidth(150);
			seurantaLabel.setTextFill(Color.web("#5C5D8D"));
			
			maaraLabel = new Label("Määrä");
			maaraLabel.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
			maaraLabel.setPrefWidth(150);
			nopeusLabel = new Label("Nopeus");
			nopeusLabel.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
			nopeusLabel.setPrefWidth(150);
			jakaumaLabel = new Label("Jakauma");
			jakaumaLabel.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
			jakaumaLabel.setPrefWidth(150);
			palvelupisteetLabel = new Label("Palvelupisteet");
			palvelupisteetLabel.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
			palvelupisteetLabel.setPrefWidth(150);
			seurantaMaara = new Label("75 paikkaa");
			seurantaMaara.setFont(Font.font("Tahoma", FontWeight.NORMAL, 15));
			seurantaMaara.setPrefWidth(150);
			
			palvelupisteetOvi = new Label("Ovihenkilöt");
			palvelupisteetOvi.setFont(Font.font("Tahoma", FontWeight.BOLD, 15));
			palvelupisteetOvi.setPrefWidth(150);
			palvelupisteetOvi.setTextFill(Color.web("#2EC4B6"));
			palvelupisteetIlm = new Label("Ilmoittautumispisteet");
			palvelupisteetIlm.setFont(Font.font("Tahoma", FontWeight.BOLD, 15));
			palvelupisteetIlm.setPrefWidth(150);
			palvelupisteetIlm.setTextFill(Color.web("009FB7"));
			palvelupisteetRok = new Label("Rokottajat");
			palvelupisteetRok.setFont(Font.font("Tahoma", FontWeight.BOLD, 15));
			palvelupisteetRok.setPrefWidth(150);
			palvelupisteetRok.setTextFill(Color.web("#FF9F1C"));
			palvelupisteetSeur = new Label("Jälkiseuranta");
			palvelupisteetSeur.setFont(Font.font("Tahoma", FontWeight.BOLD, 15));
			palvelupisteetSeur.setPrefWidth(150);
			palvelupisteetSeur.setTextFill(Color.web("#5C5D8D"));
			poistuneet = new Label("Poistuneet asiakkaat");
			poistuneet.setFont(Font.font("Tahoma", FontWeight.BOLD, 15));
			poistuneet.setPrefWidth(150);
			poistuneet.setTextFill(Color.web("#D282A6"));
			
			saapuminenLabel.setMaxWidth(Double.MAX_VALUE);
			oviLabel.setMaxWidth(Double.MAX_VALUE);
			ilmLabel.setMaxWidth(Double.MAX_VALUE);
			rokotusLabel.setMaxWidth(Double.MAX_VALUE);
			seurantaLabel.setMaxWidth(Double.MAX_VALUE);
			
			GridPane valinnat = new GridPane();
			valinnat.setAlignment(Pos.CENTER);
			valinnat.setVgap(20);
			valinnat.setHgap(20);
			valinnat.add(palvelupisteetLabel, 0, 0);
			valinnat.add(maaraLabel, 1, 0);
			valinnat.add(nopeusLabel, 2, 0);
			valinnat.add(jakaumaLabel, 3, 0);
			valinnat.add(saapuminenLabel, 0, 1);
			valinnat.add(oviLabel, 0, 2);
			valinnat.add(ilmLabel, 0, 3);
			valinnat.add(rokotusLabel, 0, 4);
			valinnat.add(seurantaLabel, 0, 5);
			
			valinnat.add(ovihenkiloMaara, 1, 2);
			valinnat.add(ilmoittautuminenMaara, 1, 3);
			valinnat.add(rokottajaMaara, 1, 4);
			valinnat.add(seurantaMaara, 1, 5);
			
			valinnat.add(saapuminenNopeus, 2, 1);
			valinnat.add(oviNopeus, 2, 2);
			valinnat.add(ilmNopeus, 2, 3);
			valinnat.add(rokottajaNopeus, 2, 4);
			valinnat.add(seurantaNopeus, 2, 5);
			
			valinnat.add(jakaumatSaapuminen, 3, 1);
			valinnat.add(jakaumatOvi, 3, 2);
			valinnat.add(jakaumatIlm, 3, 3);
			valinnat.add(jakaumatRokottaja, 3, 4);
			valinnat.add(jakaumatSeuranta, 3, 5);

			/*
			 * Tulokset
			 */
			
			Label tuloksetLabel = new Label();
			tuloksetLabel.setText("Tulokset");
			tuloksetLabel.setStyle("-fx-font: 24 arial;");
			
			//suoritustehot
			suoritustehot = new Text();
			Label suoritustehotLabel = new Label();
			suoritustehotLabel.setText("Suoritustehot:");
			ScrollPane suoritustehotScrollPane = new ScrollPane();
			suoritustehotScrollPane.setVbarPolicy(ScrollBarPolicy.AS_NEEDED);
			suoritustehotScrollPane.setHbarPolicy(ScrollBarPolicy.AS_NEEDED);
			suoritustehotScrollPane.setContent(suoritustehot);
			suoritustehotScrollPane.setPrefSize(400, 200);
			
			//käyttöasteet
			kayttoasteet = new Text();
			Label kayttoasteetLabel = new Label();
			kayttoasteetLabel.setText("Käyttöasteet:");
			ScrollPane kayttoasteetScrollPane = new ScrollPane();
			kayttoasteetScrollPane.setVbarPolicy(ScrollBarPolicy.AS_NEEDED);
			kayttoasteetScrollPane.setHbarPolicy(ScrollBarPolicy.AS_NEEDED);
			kayttoasteetScrollPane.setContent(kayttoasteet);
			kayttoasteetScrollPane.setPrefSize(400, 200);
			
			//keskimääräinen jonotusaika
			Label keskimLabel = new Label();
			keskimLabel.setText("Keskimääräinen jonotusaika:");
			ScrollPane keskimScrollPane = new ScrollPane();
			keskimScrollPane.setVbarPolicy(ScrollBarPolicy.AS_NEEDED);
			keskimScrollPane.setHbarPolicy(ScrollBarPolicy.AS_NEEDED);
			//keskimScrollPane.setContent();
			keskimScrollPane.setPrefSize(400, 200);
		
			//Loppuaika
			loppuaika = new Text();
			Label loppuaikaLabel = new Label();
			loppuaikaLabel.setText("Loppuaika:");
			
			//Suurin asiakas
			Label suurinLabel = new Label();
			suurinLabel.setText("Pisin aika:");
			suurin = new Text();
			
			//Pienin asiakas
			Label pieninLabel = new Label();
			pieninLabel.setText("Lyhin aika:");
			pienin = new Text();
			
			//Tallenna nappi
			Button tallennaButton = new Button();
			tallennaButton.setText("Tallenna tulokset");
			tallennaButton.setOnAction(e -> kontrolleri.tallennaSimulaatio());

			//Simulaation parametrit
			Label parametritLabel = new Label();
			parametritLabel.setText("Parametrit");
			parametritLabel.setStyle("-fx-font: 24 arial;");
			
			//Jonotusajat
			Label jonotusajatLabel = new Label();
			//jonotusajatLabel.setText("Jonotusajat");
			jonotusajatLabel.setStyle("-fx-font: 24 arial;");
			
			palvellutAsiakkaat = new Label();
			
			saap = new Text("Saapuminen");
			ovi = new Text("Ovi");
			ilm = new Text("Ilmoittautuminen");
			rok = new Text("Rokottajat");
			seur = new Text("Seurannat");
			avg = new Text("Keskim. jonotusaika");
			max = new Text("Suurin jonotusaika");
			maara = new Text("Määrä");
			nopeus = new Text("Nopeus");
			jakauma = new Text("Jakauma");
			
			//saapKesk = new Text();
			oviKesk = new Text();
			
			ilmKesk = new Text();
			rokKesk = new Text();
			seurKesk = new Text();
			
			//saapSuur = new Text();
			oviSuur = new Text();
			ilmSuur = new Text();
			rokSuur = new Text();
			seurSuur = new Text();
			
			saapMaara = new Text("1");
			ovMaara = new Text();
			ilmoMaara = new Text();
			rokMaara = new Text();
			seurMaara = new Text("1");
			
			//testi
			oviKesk.setText("11");
			oviSuur.setText("21");
			
			GridPane tulokset = new GridPane();
			tulokset.setAlignment(Pos.CENTER);
			tulokset.setVgap(20);
			tulokset.setHgap(20);
			tulokset.add(tuloksetLabel, 0, 0);
			tulokset.add(loppuaikaLabel, 0, 1);
			tulokset.add(loppuaika, 0, 2);
			tulokset.add(suurinLabel, 1, 1);
			tulokset.add(suurin, 1, 2);
			tulokset.add(pieninLabel, 2, 1);
			tulokset.add(pienin, 2, 2);
			tulokset.add(suoritustehotLabel, 0,3);
			tulokset.add(kayttoasteetLabel, 1, 3);
			tulokset.add(keskimLabel, 2, 3);
			tulokset.add(suoritustehotScrollPane, 0, 4);
			tulokset.add(kayttoasteetScrollPane, 1, 4);
			tulokset.add(keskimScrollPane, 2, 4);
			tulokset.add(tallennaButton, 1, 0);
			tulokset.add(palvellutAsiakkaat, 2, 0);
			
			GridPane tulokset1 = new GridPane();
			tulokset1.setVgap(20);
			tulokset1.setHgap(100);
			//tulokset1.add(jonotusajatLabel, 0, 0);
			tulokset1.add(avg, 1, 1);
			tulokset1.add(max, 2, 1);
			tulokset1.add(maara, 3, 1);
			tulokset1.add(nopeus, 4, 1);
			tulokset1.add(jakauma, 5, 1);
			tulokset1.add(saap, 0, 2);
			tulokset1.add(ovi, 0, 3);
			tulokset1.add(ilm, 0, 4);
			tulokset1.add(rok, 0, 5);
			tulokset1.add(seur, 0, 6);
			
			//tulokset1.add(saapKesk, 1, 2);
			tulokset1.add(oviKesk, 1, 3);
			tulokset1.add(ilmKesk, 1, 4);
			tulokset1.add(rokKesk, 1, 5);
			tulokset1.add(seurKesk, 1, 6);
			
			//tulokset1.add(saapSuur, 2, 2);
			tulokset1.add(oviSuur, 2, 3);
			tulokset1.add(ilmSuur, 2, 4);
			tulokset1.add(rokSuur, 2, 5);
			tulokset1.add(seurSuur, 2, 6);
			
			tulokset1.add(saapMaara, 3, 2);
			tulokset1.add(ovMaara, 3, 3);
			tulokset1.add(ilmoMaara, 3, 4);
			tulokset1.add(rokMaara, 3, 5);
			tulokset1.add(seurMaara, 3, 6);
			//tulokset1.add(rokMaara, 1, 4);
			//tulokset1.add(seurMaara, 1, 5);
		
			VBox tuloksetHBox = new VBox();
			tuloksetHBox.setPadding(new Insets(15, 12, 15, 12));
			tuloksetHBox.setSpacing(10);
			tuloksetHBox.getChildren().addAll(tulokset);
			tuloksetHBox.getChildren().addAll(tulokset1);
			tuloksetHBox.setPrefSize(1000, 600);
			
			ScrollPane tuloksetScrollPane = new ScrollPane();
			tuloksetScrollPane.setContent(tuloksetHBox);
			
			/*
			 * Menu
			 */
			final Menu menu = new Menu("Simulaatiot");
			final Menu tuloksetMenu = new Menu("Tulokset");
			
			//tulokset
			Scene secondScene = new Scene(tuloksetScrollPane, 1050, 600);
			
			MenuItem tuloksetItem = new MenuItem("Näytä tulokset");
			tuloksetItem.setOnAction(new EventHandler<ActionEvent>() {
		    @Override 
		    public void handle(ActionEvent e) {			

				Stage newWindow = new Stage();
				newWindow.setTitle("Tulokset");
				newWindow.setScene(secondScene);

				newWindow.setX(primaryStage.getX() + 100);
				newWindow.setY(primaryStage.getY() + 150);

				newWindow.show();
		    	} 
			});
			
			tuloksetMenu.getItems().add(tuloksetItem);
		    
			ListView<String> simulaatiotList = new ListView<String>();
			
			ArrayList<String> testi = new ArrayList<String>();
			testi.add("mirri");
			testi.add("mouku");
			testi.add("matti");
			
			for (int i = 0; i < testi.size(); i++) {
				simulaatiotList.getItems().add(testi.get(i));
			    }
			      
			//tietokanta
			MenuItem simulaatiot = new MenuItem("Tallennetut simulaatiot");
			simulaatiot.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent e) {

					ScrollPane simulaatiotLayout = new ScrollPane();

					simulaatiotLayout.setContent(simulaatiotList);
						
					
					
					Scene secondScene = new Scene(simulaatiotLayout, 600, 400);

					Stage simulaatiotWindow = new Stage();
					simulaatiotWindow.setTitle("Simulaatiot");
					simulaatiotWindow.setScene(secondScene);

					simulaatiotWindow.setX(primaryStage.getX() + 200);
					simulaatiotWindow.setY(primaryStage.getY() + 100);

					simulaatiotWindow.show();
				}

			});

			menu.getItems().add(simulaatiot);
			
			MenuBar menuBar = new MenuBar();
			menuBar.getMenus().addAll(tuloksetMenu);
			menuBar.getMenus().addAll(menu);
			
			HBox hBox = new HBox();
			hBox.setPadding(new Insets(15, 12, 15, 12)); // marginaalit ylä, oikea, ala, vasen
			hBox.setSpacing(10); // noodien välimatka 10 pikseliä

			GridPane mainGrid = new GridPane();

			GridPane grid = new GridPane();
			grid.setAlignment(Pos.CENTER);
			grid.setVgap(10);
			grid.setHgap(5);
			grid.add(aikaLabel, 0, 0); // sarake, rivi
			grid.add(aika, 1, 0); // sarake, rivi
			grid.add(viiveLabel, 0, 1); // sarake, rivi
			grid.add(viive, 1, 1); // sarake, rivi
			grid.add(tulosLabel, 0, 2); // sarake, rivi
			grid.add(tulos, 1, 2); // sarake, rivi
			grid.add(kaynnistaButton, 0, 3); // sarake, rivi
			grid.add(nopeutaButton, 0, 4); // sarake, rivi
			grid.add(hidastaButton, 1, 4); // sarake, rivi
			
			GridPane palvelupisteet = new GridPane();
			palvelupisteet.setVgap(100);
			palvelupisteet.setAlignment(Pos.CENTER);
			palvelupisteet.add(palvelupisteetOvi, 0, 0);
			palvelupisteet.add(palvelupisteetIlm, 0, 1);
			palvelupisteet.add(palvelupisteetRok, 0, 2);
			palvelupisteet.add(palvelupisteetSeur, 0, 3);
			palvelupisteet.add(poistuneet, 0, 4);

			HBox canvas = new HBox();			
			naytto = new Visualisointi(1310, 600, this);
			canvas.getChildren().addAll(palvelupisteet, (Node) naytto);
			canvas.setPadding(new Insets(20, 20, 20, 20));

			GridPane gridView = new GridPane();
			
			gridView.add(hBox, 0, 1);
			gridView.add(menuBar, 0, 0);
			
			// Täytetään boxi:
			hBox.getChildren().addAll(grid, valinnat);

			mainGrid.add(gridView, 0, 0);
			mainGrid.add(hBox, 0, 1);
			mainGrid.add(canvas, 0, 2);
			Scene scene = new Scene(mainGrid);
			primaryStage.setScene(scene);
			primaryStage.show();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// GUI Metodit
	
	public void setAlkuarvot() {
		jakaumaSaapuminen = "negexp";
		jakaumaOvi = "normal";
		jakaumaIlm = "normal";
		jakaumaRokottaja = "normal";
		jakaumaSeuranta = "normal";
		
		nopeusSaapuminen = 2;
		nopeusOvi = 2;
		nopeusIlm = 2;
		nopeusRokottaja = 2;
		nopeusSeuranta = 2;
		
		ovihenkilot = 1;
		ilmoittautumispisteet = 1;
		rokottajat = 1;
	}
	
	public void disableValinnat() {
		kaynnistaButton.setDisable(true);
		ovihenkiloMaara.setDisable(true);
		ilmoittautuminenMaara.setDisable(true);
		rokottajaMaara.setDisable(true);
		
		saapuminenGroup.getToggles().forEach(toggle -> {
		    Node node = (Node) toggle ;
		    node.setDisable(true);
		});
		oviGroup.getToggles().forEach(toggle -> {
		    Node node = (Node) toggle ;
		    node.setDisable(true);
		});
		ilmGroup.getToggles().forEach(toggle -> {
		    Node node = (Node) toggle ;
		    node.setDisable(true);
		});
		rokottajaGroup.getToggles().forEach(toggle -> {
		    Node node = (Node) toggle ;
		    node.setDisable(true);
		});
		seurantaGroup.getToggles().forEach(toggle -> {
		    Node node = (Node) toggle ;
		    node.setDisable(true);
		});
		saapuminenNopeusGroup.getToggles().forEach(toggle -> {
		    Node node = (Node) toggle ;
		    node.setDisable(true);
		});
		oviNopeusGroup.getToggles().forEach(toggle -> {
		    Node node = (Node) toggle ;
		    node.setDisable(true);
		});
		ilmNopeusGroup.getToggles().forEach(toggle -> {
		    Node node = (Node) toggle ;
		    node.setDisable(true);
		});
		rokottajaNopeusGroup.getToggles().forEach(toggle -> {
		    Node node = (Node) toggle ;
		    node.setDisable(true);
		});
		seurantaNopeusGroup.getToggles().forEach(toggle -> {
		    Node node = (Node) toggle ;
		    node.setDisable(true);
		});
	}
	
	// Käyttöliittymän rajapintametodit (kutsutaan kontrollerista)

	@Override
	public double getAika() {
		return Double.parseDouble(aika.getText());
	}

	@Override
	public long getViive() {
		return Long.parseLong(viive.getText());
	}

	@Override
	public IVisualisointi getVisualisointi() {
		return naytto;
	}
	
	public String getSaapumisjakauma() {
		return jakaumaSaapuminen;
	}

	public int getSaapumistiheys() {
		return nopeusSaapuminen;
	}

	public void setOviMaara() {
		ovihenkilot = (int) ovihenkiloMaara.getValue();
		ovMaara.setText(Integer.toString(ovihenkilot));
	}
	
	public int getOviMaara() {
		return ovihenkilot;
	}

	public String getOviJakauma() {
		return jakaumaOvi;
	}

	public int getOviPalvelunopeus() {
		return nopeusOvi;
	}

	public void setIlmoMaara() {
		ilmoittautumispisteet = (int) ilmoittautuminenMaara.getValue();
		ilmoMaara.setText(Integer.toString(ilmoittautumispisteet));
	}
	
	public int getIlmoMaara() {
		return ilmoittautumispisteet;
	}
	
	public String getIlmoJakauma() {
		return jakaumaIlm;
	}

	public int getIlmoPalvelunopeus() {
		return nopeusIlm;
	}
	
	public void setRokMaara() {
		rokottajat = (int) rokottajaMaara.getValue();
		rokMaara.setText(Integer.toString(rokottajat));
	}
	
	public int getRokMaara() {
		return rokottajat;
	}
	
	public String getRokJakauma() {
		return jakaumaRokottaja;
	}

	public int getRokPalvelunopeus() {
		return nopeusRokottaja;
	}
	
	public String getSeurJakauma() {
		return jakaumaSeuranta;
	}

	public int getSeurPalvelunopeus() {
		return nopeusSeuranta;
	}	
	

	@Override
	public void setLoppuaika(double aika) {
		DecimalFormat formatter = new DecimalFormat("#0.00");
		this.loppuaika.setText(formatter.format(aika));
		this.tulos.setText(formatter.format(aika));
	}
	@Override
	public void setLapimenoaika(double aika) {
		DecimalFormat formatter = new DecimalFormat("#0.00");
		this.loppuaika.setText(formatter.format(aika));
	}
	@Override
	public void setKayttoasteet(HashMap<String, Double> palvelupisteet) {
		for (String i : palvelupisteet.keySet()) {
			this.kayttoasteet.setText(i + " " + palvelupisteet.get(i));
			}
	}
	@Override
	public void setSuoritustehot(HashMap<String, Double> palvelupisteet) {
		for (String i : palvelupisteet.keySet()) {
			this.suoritustehot.setText(i + " " + palvelupisteet.get(i));
			}
	}
    public void setPieninAsiakas(double lapimenoaika) {
    	DecimalFormat formatter = new DecimalFormat("#0.00");
		this.pienin.setText(formatter.format(lapimenoaika));
    }
    public void setSuurinAsiakas(double lapimenoaika) {
    	DecimalFormat formatter = new DecimalFormat("#0.00");
		this.suurin.setText(formatter.format(lapimenoaika));
    }
    public void setSuurinOviJono(double jonotusaika) {
    	DecimalFormat formatter = new DecimalFormat("#0.00");
		this.oviSuur.setText(formatter.format(jonotusaika));
    }
    public void setSuurinIlmoJono(double jonotusaika) {
    	DecimalFormat formatter = new DecimalFormat("#0.00");
		this.ilmSuur.setText(formatter.format(jonotusaika));
    }
    public void setSuurinRokJono(double jonotusaika) {
    	DecimalFormat formatter = new DecimalFormat("#0.00");
		this.rokSuur.setText(formatter.format(jonotusaika));
    }
    public void setSuurinSeurJono(double jonotusaika) {
    	DecimalFormat formatter = new DecimalFormat("#0.00");
		this.seurSuur.setText(formatter.format(jonotusaika));
    }
    public void setPalvellutAsiakkaat(int kpl) {
    	palvellutAsiakkaat.setText("Palvellut asiakkaat: " + kpl);
    }
    public void setAvgOviJono(double jonotusaika) {
    	DecimalFormat formatter = new DecimalFormat("#0.00");
		this.oviKesk.setText(formatter.format(jonotusaika));
    }
    public void setAvgIlmoJono(double jonotusaika) {
    	DecimalFormat formatter = new DecimalFormat("#0.00");
		this.ilmKesk.setText(formatter.format(jonotusaika));
    }
    public void setAvgRokJono(double jonotusaika) {
    	DecimalFormat formatter = new DecimalFormat("#0.00");
		this.rokKesk.setText(formatter.format(jonotusaika));;
    }
    public void setAvgSeurJono(double jonotusaika) {
    	DecimalFormat formatter = new DecimalFormat("#0.00");
		this.seurKesk.setText(formatter.format(jonotusaika));
    }
	
	// JavaFX-sovelluksen (käyttöliittymän) käynnistäminen

	public static void main(String[] args) {
		launch(args);
	}
}