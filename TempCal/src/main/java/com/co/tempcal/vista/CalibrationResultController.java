package com.co.tempcal.vista;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.co.tempcal.controlador.MainGUI;
import com.co.tempcal.modelo.CertificateDTO;
import com.co.tempcal.modelo.UtilCertificate;
import com.co.tempcal.modelo.CalibrationInformationDTO;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class CalibrationResultController {
	
	/**
	 * Logger
	 */
	private static final Logger LoggerResult = LoggerFactory.getLogger(CalibrationResultController.class);
	
	@FXML
	private TextField txtDate;

	@FXML
	private TextField txtSerial;

	@FXML
	private TextField txtCalibBy;

	@FXML
	private TextField txtTypeTemp;

	@FXML
	private TextField txtColdBathTemp;
	
	@FXML
	private TextField txtHotBathTemp;

	@FXML
	private TextField txtHotUncalibSensor;

	@FXML
	private TextField txtHotCalibSensor;

	@FXML
	private TextField txtTestColdBath;

	@FXML
	private TextField txtTestColdSensor;

	@FXML
	private TextField txtProcedureResult;

	@FXML
	private TextField txtTimeProcedure;

	@FXML
	private Button btnGeneteCertificate;

	@FXML
	private Button btnResetProcess;

	/**
	 * 
	 */
	private Stage dialogStage;

	/**
	 * Main GUI
	 */
	private MainGUI mainGUI;

	/**
	 * Process DTO
	 */
	private CalibrationInformationDTO infoCalibration;

	/**
	 * Certificate DTO
	 */
	private CertificateDTO infoCertificate;

	@FXML
	private void initialize() {
	}

	/**
	 * Current Stage
	 * 
	 * @param dialogStage
	 */
	public void setDialogStage(Stage dialogStage, CalibrationInformationDTO infoCalibration,
			CertificateDTO infoCertificate) {
		this.dialogStage = dialogStage;
		this.infoCalibration = infoCalibration;
		this.infoCertificate = infoCertificate;
		LoggerResult.info("New Calibration Result Panel");
		loadInformation(this.infoCalibration);

	}

	private void loadInformation(CalibrationInformationDTO infoCalibracion) {

		txtDate.setText(infoCalibracion.getCalibrationDate());
		txtSerial.setText(infoCalibracion.getSerial());
		txtCalibBy.setText(infoCalibracion.getCalibrationPerson());
		txtTypeTemp.setText(infoCalibracion.getTemperatureType());
		txtColdBathTemp.setText(infoCalibracion.getColdBathTemp1());
		txtHotCalibSensor.setText(infoCalibracion.getHotSensorTemp2());
		txtHotUncalibSensor.setText(infoCalibracion.getHotSensorTemp());
		txtTestColdBath.setText(infoCalibracion.getTestColdBathTemp());
		txtTestColdSensor.setText(infoCalibracion.getTestColdSensorTemp());
		txtProcedureResult.setText(infoCalibracion.getResultProcess());
		txtHotBathTemp.setText(infoCalibracion.getHotBathTemp());
		
		Long currentTime = System.currentTimeMillis();
		Long timeElapsed = currentTime - infoCalibracion.getTimeProcedure();
		
		final Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(timeElapsed);
		final SimpleDateFormat sdfParser = new SimpleDateFormat("mm:ss");
		String sTime = sdfParser.format(cal.getTime());
		txtTimeProcedure.setText(sTime);
		
		if(infoCalibracion.getResultProcess().equals("PROCESS PASSED")){
			btnGeneteCertificate.setDisable(false);
			btnResetProcess.setDisable(true);
		}else{
			btnGeneteCertificate.setDisable(true);
			btnResetProcess.setDisable(false);
		}
		
	}

	/**
	 * Llamado cuando el boton siguiente es precionado
	 */
	@FXML
	private void handleGenerate() {
		
		LoggerResult.info("Selecting pdf Folder");
		
		try {	
			FileChooser fileChooser = new FileChooser();
			fileChooser.setTitle("Save Certifcate");
			FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Docx", "*.docx");
			fileChooser.getExtensionFilters().add(extFilter);
			fileChooser.setInitialFileName("CertificateMachine" + this.infoCertificate.getMachineModel());
			File certificateFile = fileChooser.showSaveDialog(this.dialogStage);
			
			LoggerResult.info("File Path Selected");
			
			if(certificateFile != null){
				LoggerResult.info("Creating Certificate");
				createCertificate(certificateFile);
			}
						
		}catch(Exception e){
			LoggerResult.error(e.toString());
		}
			
	}

	private void createCertificate(File file) throws Exception {
		
		UtilCertificate.createCertitificate(infoCertificate, infoCalibration, file);
		dialogStage.close();
		
	}

	
	@FXML
	private void handleReset() {
		this.dialogStage.close();
		mainGUI.showBasicInformationPanel();
	}

	public void setMainApp(MainGUI mainApp) {
		this.mainGUI = mainApp;
	}

}
