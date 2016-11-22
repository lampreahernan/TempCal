package com.co.tempcal.vista;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.co.tempcal.controlador.MainGUI;
import com.co.tempcal.modelo.CalibrationInformationDTO;
import com.co.tempcal.modelo.CertificateDTO;
import com.co.tempcal.modelo.TemperatureCalculations;
import com.co.tempcal.modelo.UtilHistory;
import com.co.tempcal.modelo.UtilTimer;
import com.co.tempcal.modelo.Validations;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class TemperatureInfoController {

	/**
	 * Logger
	 */
	private static final Logger LoggerInfoTemp = LoggerFactory.getLogger(TemperatureInfoController.class);

	@FXML
	private Button btnNext;

	@FXML
	private Button btnCancel;

	@FXML
	private Button btnCalculate;

	@FXML
	private Button btnValidate;

	@FXML
	private TextField txtColdBathTemp;

	@FXML
	private TextField txtHotUncalibSensorTemp;

	@FXML
	private TextField txtHotCalibSensorTemp;

	@FXML
	private TextField txtTestColdBathTemp;

	@FXML
	private TextField txtTestColdSensorTemp;

	@FXML
	private TextField txtTimeElapsedColdBath;

	@FXML
	private TextField txtTimeElapsedHotSensor;

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
	 * Temperature Calculations
	 */
	private TemperatureCalculations calculations;

	/**
	 * Time Elapsed
	 */
	private Timer timer;
	
	/**
	 * Timer Task
	 */
	private UtilTimer timerTask;

	/**
	 * Initializes the controller class. This method is automatically called
	 * after the fxml file has been loaded.
	 */
	@FXML
	private void initialize() {

		this.calculations = new TemperatureCalculations();
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

		
		if (this.txtTimeElapsedColdBath != null) {
			timer = new Timer();
			timerTask = new UtilTimer(this.getTxtTimeElapsedColdBath());
			timer.scheduleAtFixedRate(timerTask, 0, 1000);
		} else {
			if (this.txtTimeElapsedHotSensor != null) {
				timer = new Timer();
				timerTask = new UtilTimer(this.getTxtTimeElapsedHotSensor());
				timer.scheduleAtFixedRate(timerTask, 0, 1000);
			}
		}

		if (this.infoCalibracion.getTimeProcedure() == null) {

			this.infoCalibracion.setTimeProcedure(System.currentTimeMillis());
			Date currentTime = new Date();
			SimpleDateFormat format = new SimpleDateFormat("hh:mm:ss");
			LoggerInfoTemp.info("Hora Actual: " + format.format(currentTime));

		}

	}

	public void setMainApp(MainGUI mainApp) {
		this.mainGUI = mainApp;
	}

	@FXML
	private void handleNext() {

		String errorMessage = "";

		if (txtColdBathTemp.getText() == null || txtColdBathTemp.getText().length() == 0) {
			errorMessage += "Set the Cold Bath Temp \n";
		} else {
			if (!Validations.validatedDecimals(txtColdBathTemp.getText())) {
				errorMessage += "Only a numbers in the Cold Bath field with ONE decimal \n";
			}
		}

		if (errorMessage.length() == 0) {
			this.timerTask.cancel();
			this.timer.cancel();
			this.timer.purge();
			infoCalibracion.setColdBathTemp1(txtColdBathTemp.getText());
			mainGUI.showHotSensorPanel(this.dialogStage, this.infoCalibracion, this.infoCertificate);
		} else {
			Validations.showAlert(errorMessage);
		}

	}

	@FXML
	private void handleCalculate() {

		String errorMessage = "";

		if (txtHotUncalibSensorTemp.getText() == null || txtHotUncalibSensorTemp.getText().length() == 0) {
			errorMessage += "Set the Cold Bath Temp \n";
		} else {
			if (!Validations.validatedDecimals(txtHotUncalibSensorTemp.getText())) {
				errorMessage += "Only a numbers in the Hot Uncalib Sensor field with ONE decimal \n";
			}
		}

		if (errorMessage.length() == 0) {
			infoCalibracion.setHotSensorTemp(txtHotUncalibSensorTemp.getText());
			float factor = calculations.calculateFactor(infoCalibracion.getColdBathTemp1(),
					infoCalibracion.getHotSensorTemp(), infoCalibracion.getHotBathTemp());
			infoCalibracion.setHotSensorTemp2(String.valueOf(factor));
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Calculation Factor");
			alert.setHeaderText("The Factor is: ");
			alert.setContentText(String.valueOf(factor));
			alert.showAndWait();
			
			this.timerTask.cancel();
			this.timer.cancel();
			this.timer.purge();
			
			mainGUI.showInformationPanel(dialogStage, infoCalibracion, infoCertificate);

		} else {
			Validations.showAlert(errorMessage);
		}

	}

	@FXML
	private void handleValidate() {

		if (validateInfoTest()) {
			infoCalibracion.setTestColdBathTemp(txtTestColdBathTemp.getText());
			infoCalibracion.setTestColdSensorTemp(txtTestColdSensorTemp.getText());

			if (calculations.evaluateCalibration(infoCalibracion.getTestColdBathTemp(),
					infoCalibracion.getTestColdSensorTemp(), infoCalibracion.getTemperatureType())) {
				infoCalibracion.setResultProcess("PROCESS PASSED");
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Result Process");
				alert.setHeaderText("PROCESS PASSED");
				alert.showAndWait();
			} else {
				infoCalibracion.setResultProcess("PROCESS FAILED");
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Result Process");
				alert.setHeaderText("PROCESS FAILED");
				alert.setContentText("Please restart the process");
				alert.showAndWait();
			}
			
			mainGUI.showResultPanel(dialogStage, infoCalibracion, infoCertificate);
			UtilHistory.saveHistory(infoCalibracion);
			
		}

	}

	private boolean validateInfoTest() {
		String errorMessage = "";

		if (this.txtTestColdBathTemp.getText() == null || this.txtTestColdBathTemp.getText().length() == 0) {
			errorMessage += "Set the Cold Bath Temp \n";
		} else {
			if (!Validations.validatedDecimals(this.txtTestColdBathTemp.getText())) {
				errorMessage += "Only a numbers in the Cold Bath Temp field with ONE decimal \n";
			}
		}

		if (this.txtTestColdSensorTemp.getText() == null || this.txtTestColdSensorTemp.getText().length() == 0) {
			errorMessage += "Set the Cold Sensor Temp \n";
		} else {
			if (!Validations.validatedDecimals(this.txtTestColdSensorTemp.getText())) {
				errorMessage += "Only a numbers in the Cold Sensor Temp field with ONE decimal \n";
			}
		}

		if (errorMessage.length() == 0) {
			return true;
		} else {
			Validations.showAlert(errorMessage);
			return false;
		}

	}

	public TextField getTxtTimeElapsedColdBath() {
		return txtTimeElapsedColdBath;
	}

	public void setTxtTimeElapsedColdBath(TextField txtTimeElapsedColdBath) {
		this.txtTimeElapsedColdBath = txtTimeElapsedColdBath;
	}

	public TextField getTxtTimeElapsedHotSensor() {
		return txtTimeElapsedHotSensor;
	}

	public void setTxtTimeElapsedHotSensor(TextField txtTimeElapsedHotSensor) {
		this.txtTimeElapsedHotSensor = txtTimeElapsedHotSensor;
	}

}
