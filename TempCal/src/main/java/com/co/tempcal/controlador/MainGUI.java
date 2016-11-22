package com.co.tempcal.controlador;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Timer;

import com.co.tempcal.modelo.CalibrationInformationDTO;
import com.co.tempcal.modelo.CertificateDTO;
import com.co.tempcal.modelo.UtilHistory;
import com.co.tempcal.modelo.UtilTimer;
import com.co.tempcal.modelo.Validations;
import com.co.tempcal.vista.BasicInfoController;
import com.co.tempcal.vista.TemperatureInfoController;
import com.co.tempcal.vista.CalibrationResultController;
import com.co.tempcal.vista.InformationTestController;
import com.co.tempcal.vista.MainGUIController;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

@SuppressWarnings("unused")
public class MainGUI extends Application {

	/**
	 * Main Scene
	 */
	private Stage stgMainGUI;

	/**
	 * Main Panel
	 */
	private BorderPane pnlMain;

	@Override
	public void start(Stage primaryStage) {
		this.stgMainGUI = primaryStage;
		this.stgMainGUI.setTitle("TempCal");
		this.stgMainGUI.getIcons().add(new Image("com/co/tempcal/images/icon.png"));

		URL rootFolder = MainGUI.class.getProtectionDomain().getCodeSource().getLocation();
		File rootFile = new File(rootFolder.getPath());

		File logFile = new File("log");
		logFile.mkdirs();

		if (System.getProperty("tempcal.app.dir") == null) {
			System.setProperty("tempcal.app.dir", logFile.getAbsolutePath());
		}
		try {
			UtilHistory.createHistoryFile();
		} catch (Exception e) {
			Validations.showErrorAlert(
					"Please close the History File or the program will not be able to save this process");
		}

		initGUI();
		showMainGui();
		
		stgMainGUI.setOnCloseRequest(new EventHandler<WindowEvent>() {
	        @Override
	        public void handle(WindowEvent t) {
	            Platform.exit();
	            System.exit(0);
	        }
	    });
		
	}

	/**
	 * Load the main components
	 */
	public void initGUI() {
		try {

			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainGUI.class.getClassLoader().getResource("com/co/tempcal/vista/RootLayout.fxml"));
			pnlMain = (BorderPane) loader.load();

			Scene scene = new Scene(pnlMain);
			stgMainGUI.setScene(scene);
			stgMainGUI.show();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void closeStage() {
		this.stgMainGUI.close();
		System.exit(0);
	}

	/**
	 * Load the main panel
	 */
	public void showMainGui() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainGUI.class.getClassLoader().getResource("com/co/tempcal/vista/MainGUI.fxml"));
			AnchorPane pnlPrincipal = (AnchorPane) loader.load();

			MainGUIController controller = loader.getController();
			controller.setMainApp(this);

			pnlMain.setCenter(pnlPrincipal);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Lod the basic information panel
	 */
	public void showBasicInformationPanel() {
		try {

			Stage dialogStage = new Stage();

			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainGUI.class.getClassLoader().getResource("com/co/tempcal/vista/pnlBasicInfo.fxml"));
			AnchorPane page = (AnchorPane) loader.load();

			dialogStage.getIcons().add(new Image("com/co/tempcal/images/icon.png"));
			dialogStage.setTitle("Basic Information");
			dialogStage.initOwner(stgMainGUI);
			Scene scene = new Scene(page);
			dialogStage.setScene(scene);

			BasicInfoController controller = loader.getController();
			controller.setDialogStage(dialogStage);
			controller.setMainApp(this);

			dialogStage.show();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Load the result process panel
	 */
	public void showResultPanel(Stage dialogStage, CalibrationInformationDTO infoCalibracion,
			CertificateDTO infoCertificate) {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(
					MainGUI.class.getClassLoader().getResource("com/co/tempcal/vista/pnlCalibrationResult.fxml"));
			AnchorPane page = (AnchorPane) loader.load();

			dialogStage.setTitle("Result Process #" + infoCalibracion.getSerial());
			Scene scene = new Scene(page);
			dialogStage.setScene(scene);

			CalibrationResultController controller = loader.getController();
			controller.setDialogStage(dialogStage, infoCalibracion, infoCertificate);
			controller.setMainApp(this);

			dialogStage.show();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Load the information panel
	 */
	public void showInformationPanel(Stage dialogStage, CalibrationInformationDTO infoCalibracion,
			CertificateDTO infoCertificate) {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainGUI.class.getClassLoader().getResource("com/co/tempcal/vista/pnlInfoFactor.fxml"));
			AnchorPane page = (AnchorPane) loader.load();

			dialogStage.setTitle("Result Process #" + infoCalibracion.getSerial());
			Scene scene = new Scene(page);
			dialogStage.setScene(scene);

			InformationTestController controller = loader.getController();
			controller.setDialogStage(dialogStage, infoCalibracion, infoCertificate);
			controller.setMainApp(this);

			dialogStage.show();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Show the Cold Bath Panel
	 */
	public void showColdBathPanel(Stage dialogStage, CalibrationInformationDTO infoCalibracion,
			CertificateDTO infoCertificate) {

		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainGUI.class.getClassLoader().getResource("com/co/tempcal/vista/pnlDataColdBath.fxml"));
			AnchorPane page = (AnchorPane) loader.load();

			dialogStage.setTitle("Process #" + infoCalibracion.getSerial());
			Scene scene = new Scene(page);
			dialogStage.setScene(scene);

			TemperatureInfoController controller = loader.getController();
			controller.setDialogStage(dialogStage, infoCalibracion, infoCertificate);
			controller.setMainApp(this);

			dialogStage.show();

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Load the Cold Bath Panel
	 */
	public void showHotSensorPanel(Stage dialogStage, CalibrationInformationDTO infoCalibracion,
			CertificateDTO infoCertificate) {

		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(
					MainGUI.class.getClassLoader().getResource("com/co/tempcal/vista/pnlDataHotSensor.fxml"));
			AnchorPane page = (AnchorPane) loader.load();

			dialogStage.setTitle("Process #" + infoCalibracion.getSerial());
			Scene scene = new Scene(page);
			dialogStage.setScene(scene);

			TemperatureInfoController controller = loader.getController();
			controller.setDialogStage(dialogStage, infoCalibracion, infoCertificate);
			controller.setMainApp(this);

			dialogStage.show();

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Load the temperature panel for Test
	 * 
	 */
	public void showTestTemperaturePanel(Stage dialogStage, CalibrationInformationDTO infoCalibracion,
			CertificateDTO infoCertificate) {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainGUI.class.getClassLoader().getResource("com/co/tempcal/vista/pnlDataTest.fxml"));
			AnchorPane page = (AnchorPane) loader.load();

			dialogStage.setTitle("Proceso #" + infoCalibracion.getSerial());
			Scene scene = new Scene(page);
			dialogStage.setScene(scene);

			TemperatureInfoController controller = loader.getController();
			controller.setDialogStage(dialogStage, infoCalibracion, infoCertificate);
			controller.setMainApp(this);

			dialogStage.show();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	

	/**
	 * return the main GUI
	 * 
	 * @return
	 */
	public Stage getStgMainGUI() {
		return stgMainGUI;
	}

	/**
	 * Main
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		launch(args);
	}
}
