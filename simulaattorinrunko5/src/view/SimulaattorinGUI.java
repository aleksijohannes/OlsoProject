package view;

import java.text.DecimalFormat;
import controller.*;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
import javafx.scene.layout.*;
import javafx.scene.text.*;

public class SimulaattorinGUI extends Application implements ISimulaattorinUI {

	// Kontrollerin esittely (tarvitaan käyttöliittymässä)
	private IKontrolleriVtoM kontrolleri;

	// Käyttöliittymäkomponentit:
	private TextField aika;
	private TextField viive;
	private Label tulos;
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
	private Label saapuminenJakaumaLabel;

	private TilePane jakaumatOvi;
	private ToggleGroup oviGroup;
	private RadioButton oviNorm;
	private RadioButton oviTasa;
	private RadioButton oviExp;
	private Label oviJakaumaLabel;

	private TilePane jakaumatIlm;
	private ToggleGroup ilmGroup;
	private RadioButton ilmNorm;
	private RadioButton ilmTasa;
	private RadioButton ilmExp;
	private Label ilmJakaumaLabel;

	private TilePane jakaumatRokottaja;
	private ToggleGroup rokottajaGroup;
	private RadioButton rokottajaNorm;
	private RadioButton rokottajaTasa;
	private RadioButton rokottajaExp;
	private Label rokottajaJakaumaLabel;

	private TilePane jakaumatSeuranta;
	private ToggleGroup seurantaGroup;
	private RadioButton seurantaNorm;
	private RadioButton seurantaTasa;
	private RadioButton seurantaExp;
	private Label seurantaJakaumaLabel;

	private TilePane saapuminenNopeus;
	private Label saapuminenNopeusLabel;
	private ToggleGroup saapuminenNopeusGroup;
	private RadioButton saapuminenHidas;
	private RadioButton saapuminenNormaali;
	private RadioButton saapuminenNopea;

	private TilePane oviNopeus;
	private Label oviNopeusLabel;
	private ToggleGroup oviNopeusGroup;
	private RadioButton oviHidas;
	private RadioButton oviNormaali;
	private RadioButton oviNopea;

	private TilePane ilmNopeus;
	private Label ilmNopeusLabel;
	private ToggleGroup ilmNopeusGroup;
	private RadioButton ilmHidas;
	private RadioButton ilmNormaali;
	private RadioButton ilmNopea;

	private TilePane rokottajaNopeus;
	private Label rokottajaNopeusLabel;
	private ToggleGroup rokottajaNopeusGroup;
	private RadioButton rokottajaHidas;
	private RadioButton rokottajaNormaali;
	private RadioButton rokottajaNopea;

	private TilePane seurantaNopeus;
	private Label seurantaNopeusLabel;
	private ToggleGroup seurantaNopeusGroup;
	private RadioButton seurantaHidas;
	private RadioButton seurantaNormaali;
	private RadioButton seurantaNopea;

	private Label ovihenkiloLabel;
	private Slider ovihenkiloMaara;
	private Label ilmoittautuminenLabel;
	private Slider ilmoittautuminenMaara;
	private Label rokottajaLabel;
	private Slider rokottajaMaara;

	@Override
	public void init() {

		Trace.setTraceLevel(Level.INFO);

		kontrolleri = new Kontrolleri(this);
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
					kontrolleri.kaynnistaSimulointi();
					kaynnistaButton.setDisable(true);
				}
			});

			hidastaButton = new Button();
			hidastaButton.setText("Hidasta");
			hidastaButton.setOnAction(e -> kontrolleri.hidasta());

			nopeutaButton = new Button();
			nopeutaButton.setText("Nopeuta");
			nopeutaButton.setOnAction(e -> kontrolleri.nopeuta());

			jakaumatSaapuminen = new TilePane();

			saapuminenGroup = new ToggleGroup();
			saapuminenNorm = new RadioButton("Normaali");
			saapuminenNorm.setToggleGroup(saapuminenGroup);

			saapuminenNorm.setUserData("normal");
			saapuminenNorm.setSelected(true);

			saapuminenTasa = new RadioButton("Tasa");
			saapuminenTasa.setToggleGroup(saapuminenGroup);
			saapuminenTasa.setUserData("uniform");

			saapuminenExp = new RadioButton("Exp");
			saapuminenExp.setToggleGroup(saapuminenGroup);
			saapuminenExp.setUserData("negexp");

			saapuminenJakaumaLabel = new Label("Valitse jakauma:");
			jakaumatSaapuminen.getChildren().add(saapuminenJakaumaLabel);
			jakaumatSaapuminen.getChildren().add(saapuminenNorm);
			jakaumatSaapuminen.getChildren().add(saapuminenTasa);
			jakaumatSaapuminen.getChildren().add(saapuminenExp);

			saapuminenGroup.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
				public void changed(ObservableValue<? extends Toggle> ov, Toggle old_toggle, Toggle new_toggle) {
					if (saapuminenGroup.getSelectedToggle().getUserData() == "normal") {
						jakaumaSaapuminen = "normal";
					} else if (saapuminenGroup.getSelectedToggle().getUserData() == "unoform") {
						jakaumaSaapuminen = "uniform";
					} else if (saapuminenGroup.getSelectedToggle().getUserData() == "negexp") {
						jakaumaSaapuminen = "negexp";
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

			oviJakaumaLabel = new Label("Valitse jakauma:");
			jakaumatOvi.getChildren().add(oviJakaumaLabel);
			jakaumatOvi.getChildren().add(oviNorm);
			jakaumatOvi.getChildren().add(oviTasa);
			jakaumatOvi.getChildren().add(oviExp);

			oviGroup.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
				public void changed(ObservableValue<? extends Toggle> ov, Toggle old_toggle, Toggle new_toggle) {
					if (oviGroup.getSelectedToggle().getUserData() == "normal") {
						jakaumaOvi = "normal";
					} else if (oviGroup.getSelectedToggle().getUserData() == "unoform") {
						jakaumaOvi = "uniform";
					} else if (oviGroup.getSelectedToggle().getUserData() == "negexp") {
						jakaumaOvi = "negexp";
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

			ilmJakaumaLabel = new Label("Valitse jakauma:");
			jakaumatIlm.getChildren().add(ilmJakaumaLabel);
			jakaumatIlm.getChildren().add(ilmNorm);
			jakaumatIlm.getChildren().add(ilmTasa);
			jakaumatIlm.getChildren().add(ilmExp);

			ilmGroup.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
				public void changed(ObservableValue<? extends Toggle> ov, Toggle old_toggle, Toggle new_toggle) {
					if (ilmGroup.getSelectedToggle().getUserData() == "normal") {
						jakaumaIlm = "normal";
					} else if (oviGroup.getSelectedToggle().getUserData() == "unoform") {
						jakaumaIlm = "uniform";
					} else if (ilmGroup.getSelectedToggle().getUserData() == "negexp") {
						jakaumaIlm = "negexp";
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

			rokottajaJakaumaLabel = new Label("Valitse jakauma:");
			jakaumatRokottaja.getChildren().add(rokottajaJakaumaLabel);
			jakaumatRokottaja.getChildren().add(rokottajaNorm);
			jakaumatRokottaja.getChildren().add(rokottajaTasa);
			jakaumatRokottaja.getChildren().add(rokottajaExp);

			rokottajaGroup.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
				public void changed(ObservableValue<? extends Toggle> ov, Toggle old_toggle, Toggle new_toggle) {
					if (rokottajaGroup.getSelectedToggle().getUserData() == "normal") {
						jakaumaRokottaja = "normal";
					} else if (oviGroup.getSelectedToggle().getUserData() == "unoform") {
						jakaumaRokottaja = "uniform";
					} else if (ilmGroup.getSelectedToggle().getUserData() == "negexp") {
						jakaumaRokottaja = "negexp";
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

			seurantaExp = new RadioButton("Exp");
			seurantaExp.setToggleGroup(seurantaGroup);
			seurantaExp.setUserData("negexp");

			seurantaJakaumaLabel = new Label("Valitse jakauma:");
			jakaumatSeuranta.getChildren().add(seurantaJakaumaLabel);
			jakaumatSeuranta.getChildren().add(seurantaNorm);
			jakaumatSeuranta.getChildren().add(seurantaTasa);
			jakaumatSeuranta.getChildren().add(seurantaExp);

			seurantaGroup.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
				public void changed(ObservableValue<? extends Toggle> ov, Toggle old_toggle, Toggle new_toggle) {
					if (seurantaGroup.getSelectedToggle().getUserData() == "normal") {
						jakaumaSeuranta = "normal";
					} else if (oviGroup.getSelectedToggle().getUserData() == "unoform") {
						jakaumaSeuranta = "uniform";
					} else if (ilmGroup.getSelectedToggle().getUserData() == "negexp") {
						jakaumaSeuranta = "negexp";
					}
				}
			});

			saapuminenNopeus = new TilePane();

			saapuminenNopeusLabel = new Label("Valitse saapumisnopeus:");
			saapuminenNopeusGroup = new ToggleGroup();

			saapuminenHidas = new RadioButton("Hidas");
			saapuminenHidas.setToggleGroup(saapuminenNopeusGroup);
			saapuminenHidas.setSelected(true);
			saapuminenHidas.setUserData(1);

			saapuminenNormaali = new RadioButton("Normaali");
			saapuminenNormaali.setToggleGroup(saapuminenNopeusGroup);
			saapuminenNormaali.setUserData(2);

			saapuminenNopea = new RadioButton("Nopea");
			saapuminenNopea.setToggleGroup(saapuminenNopeusGroup);
			saapuminenNopea.setUserData(3);

			saapuminenNopeus.getChildren().add(saapuminenNopeusLabel);
			saapuminenNopeus.getChildren().add(saapuminenHidas);
			saapuminenNopeus.getChildren().add(saapuminenNormaali);
			saapuminenNopeus.getChildren().add(saapuminenNopea);

			saapuminenNopeusGroup.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
				public void changed(ObservableValue<? extends Toggle> ov, Toggle old_toggle, Toggle new_toggle) {
					if ((int) saapuminenNopeusGroup.getSelectedToggle().getUserData() == 1) {
						nopeusSaapuminen = 1;
					} else if ((int) saapuminenNopeusGroup.getSelectedToggle().getUserData() == 2) {
						nopeusSaapuminen = 2;
					} else if ((int) saapuminenNopeusGroup.getSelectedToggle().getUserData() == 3) {
						nopeusSaapuminen = 3;
					}
				}
			});

			oviNopeus = new TilePane();

			oviNopeusLabel = new Label("Valitse oven nopeus:");
			oviNopeusGroup = new ToggleGroup();

			oviHidas = new RadioButton("Hidas");
			oviHidas.setToggleGroup(oviNopeusGroup);
			oviHidas.setSelected(true);
			oviHidas.setUserData(1);

			oviNormaali = new RadioButton("Normaali");
			oviNormaali.setToggleGroup(oviNopeusGroup);
			oviNormaali.setUserData(2);

			oviNopea = new RadioButton("Nopea");
			oviNopea.setToggleGroup(oviNopeusGroup);
			oviNopea.setUserData(3);

			oviNopeus.getChildren().add(oviNopeusLabel);
			oviNopeus.getChildren().add(oviHidas);
			oviNopeus.getChildren().add(oviNormaali);
			oviNopeus.getChildren().add(oviNopea);

			oviNopeusGroup.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
				public void changed(ObservableValue<? extends Toggle> ov, Toggle old_toggle, Toggle new_toggle) {
					if ((int) oviNopeusGroup.getSelectedToggle().getUserData() == 1) {
						nopeusOvi = 1;
					} else if ((int) oviNopeusGroup.getSelectedToggle().getUserData() == 2) {
						nopeusOvi = 2;
					} else if ((int) oviNopeusGroup.getSelectedToggle().getUserData() == 3) {
						nopeusOvi = 3;
					}
				}
			});

			ilmNopeus = new TilePane();

			ilmNopeusLabel = new Label("Valitse ilmoittautumisen nopeus:");
			ilmNopeusGroup = new ToggleGroup();

			ilmHidas = new RadioButton("Hidas");
			ilmHidas.setToggleGroup(ilmNopeusGroup);
			ilmHidas.setSelected(true);
			ilmHidas.setUserData(1);

			ilmNormaali = new RadioButton("Normaali");
			ilmNormaali.setToggleGroup(ilmNopeusGroup);
			ilmNormaali.setUserData(2);

			ilmNopea = new RadioButton("Nopea");
			ilmNopea.setToggleGroup(ilmNopeusGroup);
			ilmNopea.setUserData(3);

			ilmNopeus.getChildren().add(ilmNopeusLabel);
			ilmNopeus.getChildren().add(ilmHidas);
			ilmNopeus.getChildren().add(ilmNormaali);
			ilmNopeus.getChildren().add(ilmNopea);

			ilmNopeusGroup.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
				public void changed(ObservableValue<? extends Toggle> ov, Toggle old_toggle, Toggle new_toggle) {
					if ((int) ilmNopeusGroup.getSelectedToggle().getUserData() == 1) {
						nopeusIlm = 1;
					} else if ((int) ilmNopeusGroup.getSelectedToggle().getUserData() == 2) {
						nopeusIlm = 2;
					} else if ((int) ilmNopeusGroup.getSelectedToggle().getUserData() == 3) {
						nopeusIlm = 3;
					}
				}
			});

			rokottajaNopeus = new TilePane();

			rokottajaNopeusLabel = new Label("Valitse rokottajan nopeus:");
			rokottajaNopeusGroup = new ToggleGroup();

			rokottajaHidas = new RadioButton("Hidas");
			rokottajaHidas.setToggleGroup(rokottajaNopeusGroup);
			rokottajaHidas.setSelected(true);
			rokottajaHidas.setUserData(1);

			rokottajaNormaali = new RadioButton("Normaali");
			rokottajaNormaali.setToggleGroup(rokottajaNopeusGroup);
			rokottajaNormaali.setUserData(2);

			rokottajaNopea = new RadioButton("Nopea");
			rokottajaNopea.setToggleGroup(rokottajaNopeusGroup);
			rokottajaNopea.setUserData(3);

			rokottajaNopeus.getChildren().add(rokottajaNopeusLabel);
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

			seurantaNopeusLabel = new Label("Valitse seurannan nopeus:");
			seurantaNopeusGroup = new ToggleGroup();

			seurantaHidas = new RadioButton("Hidas");
			seurantaHidas.setToggleGroup(seurantaNopeusGroup);
			seurantaHidas.setSelected(true);
			seurantaHidas.setUserData(1);

			seurantaNormaali = new RadioButton("Normaali");
			seurantaNormaali.setToggleGroup(seurantaNopeusGroup);
			seurantaNormaali.setUserData(2);

			seurantaNopea = new RadioButton("Nopea");
			seurantaNopea.setToggleGroup(rokottajaNopeusGroup);
			seurantaNopea.setUserData(3);

			seurantaNopeus.getChildren().add(seurantaNopeusLabel);
			seurantaNopeus.getChildren().add(seurantaHidas);
			seurantaNopeus.getChildren().add(seurantaNormaali);
			seurantaNopeus.getChildren().add(seurantaNopea);

			seurantaNopeusGroup.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
				public void changed(ObservableValue<? extends Toggle> ov, Toggle old_toggle, Toggle new_toggle) {
					if ((int) seurantaNopeusGroup.getSelectedToggle().getUserData() == 1) {
						nopeusSeuranta = 1;
					} else if ((int) seurantaNopeusGroup.getSelectedToggle().getUserData() == 2) {
						nopeusSeuranta = 2;
					} else if ((int) seurantaNopeusGroup.getSelectedToggle().getUserData() == 3) {
						nopeusSeuranta = 3;
					}
				}
			});

			ovihenkiloLabel = new Label("Ovihenkilöiden määrä: ");

			ovihenkiloMaara = new Slider(1, 10, 1);

			ovihenkilot = (int) ovihenkiloMaara.getValue();

			ovihenkiloMaara.setSnapToTicks(true);
			ovihenkiloMaara.setMajorTickUnit(1);
			ovihenkiloMaara.setMinorTickCount(1);
			ovihenkiloMaara.setShowTickLabels(true);

			ilmoittautuminenLabel = new Label("Ilmoittautumispisteiden määrä: ");

			ilmoittautuminenMaara = new Slider(1, 10, 1);

			ilmoittautumispisteet = (int) ilmoittautuminenMaara.getValue();

			ilmoittautuminenMaara.setSnapToTicks(true);
			ilmoittautuminenMaara.setMajorTickUnit(1);
			ilmoittautuminenMaara.setMinorTickCount(1);
			ilmoittautuminenMaara.setShowTickLabels(true);

			rokottajaLabel = new Label("Rokottajien määrä: ");
			rokottajaMaara = new Slider(1, 10, 1);

			rokottajat = (int) rokottajaMaara.getValue();

			rokottajaMaara.setSnapToTicks(true);
			rokottajaMaara.setMajorTickUnit(1);
			rokottajaMaara.setMinorTickCount(1);
			rokottajaMaara.setShowTickLabels(true);

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

			HBox hBox = new HBox();
			hBox.setPadding(new Insets(15, 12, 15, 12)); // marginaalit ylä, oikea, ala, vasen
			hBox.setSpacing(10); // noodien välimatka 10 pikseliä

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
			grid.add(ovihenkiloLabel, 2, 0);
			grid.add(ovihenkiloMaara, 3, 0);
			grid.add(ilmoittautuminenLabel, 2, 1);
			grid.add(ilmoittautuminenMaara, 3, 1);
			grid.add(rokottajaLabel, 2, 2);
			grid.add(rokottajaMaara, 3, 2);

			grid.add(jakaumatSaapuminen, 4, 0);
			grid.add(jakaumatOvi, 4, 1);
			grid.add(jakaumatIlm, 4, 2);
			grid.add(jakaumatRokottaja, 4, 3);
			grid.add(jakaumatSeuranta, 4, 4);

			grid.add(saapuminenNopeus, 4, 5);
			grid.add(oviNopeus, 4, 6);
			grid.add(ilmNopeus, 4, 7);
			grid.add(rokottajaNopeus, 4, 8);
			grid.add(seurantaNopeus, 4, 9);

			naytto = new Visualisointi(400, 200);

			// Täytetään boxi:
			hBox.getChildren().addAll(grid, (Canvas) naytto);

			Scene scene = new Scene(hBox);
			primaryStage.setScene(scene);
			primaryStage.show();

		} catch (Exception e) {
			e.printStackTrace();
		}
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
	public void setLoppuaika(double aika) {
		DecimalFormat formatter = new DecimalFormat("#0.00");
		this.tulos.setText(formatter.format(aika));
	}

	@Override
	public IVisualisointi getVisualisointi() {
		return naytto;
	}

	// Jennin metodit
	public String getSaapumisjakauma() {
		return jakaumaSaapuminen;
	}

	public int getSaapumistiheys() {
		return nopeusSaapuminen;
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

	public int getIlmoMaara() {
		return ilmoittautumispisteet;
	}

	public String getIlmoJakauma() {
		return jakaumaIlm;
	}

	public int getIlmoPalvelunopeus() {
		return nopeusIlm;
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

	// JavaFX-sovelluksen (käyttöliittymän) käynnistäminen

	public static void main(String[] args) {
		launch(args);
	}

}
