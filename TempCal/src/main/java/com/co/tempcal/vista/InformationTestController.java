package com.co.tempcal.vista;

import com.co.tempcal.controlador.MainGUI;
import com.co.tempcal.modelo.CalibrationInformationDTO;
import com.co.tempcal.modelo.CertificateDTO;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class InformationTestController {
	@FXML
	private Button btnNextTest;
	
	@FXML
	private Label lblCalculateFactor;

	@FXML
	private TextField txtTestCalculationFactor;

	/**
	 * Current Stage
	 */
	private Stage dialogStage;

	/**
	 * Main GUI
	 */
	private MainGUI mainGUI;

	/**
	 * Proces DTO
	 */
	private CalibrationInformationDTO infoCalibracion;

	/**
	 * Certificate DTO
	 */
	private CertificateDTO infoCertificate;
	
	/**
	 * Initializes the controller class. This method is automatically called
	 * after the fxml file has been loaded.
	 */
	@FXML
	private void initialize() {
		
	}
	
	/**
	 * Current Stage
	 * 
	 * @param dialogStage
	 */
	public void setDialogStage(Stage dialogStage, CalibrationInformationDTO infoCalibracion,
			CertificateDTO infoCertificate) {
		this.dialogStage = dialogStage;
		this.infoCalibracion = infoCalibracion;
		this.infoCertificate = infoCertificate;
		
		txtTestCalculationFactor.setText(
				infoCalibracion.getHotSensorTemp2());

		// Celsius or Fahrenheit
		if (infoCalibracion.getTemperatureType().equals(BasicInfoController.CELSIUS)) {
			lblCalculateFactor.setText("70 °C");
		} else {
			if (infoCalibracion.getTemperatureType().equals(BasicInfoController.FAHRENHEIT)) {
				lblCalculateFactor.setText("158 °F");
			}
		}
		
	}
	
	public void setMainApp(MainGUI mainApp) {
		this.mainGUI = mainApp;
	}
	
	@FXML
	private void handleNextTest() {
		mainGUI.showTestTemperaturePanel(dialogStage, infoCalibracion, infoCertificate);
	}
}
