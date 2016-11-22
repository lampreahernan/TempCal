package com.co.tempcal.vista;

import java.util.Locale;
import java.util.Timer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.co.tempcal.controlador.MainGUI;
import com.co.tempcal.modelo.CertificateDTO;
import com.co.tempcal.modelo.UtilTimer;
import com.co.tempcal.modelo.Validations;
import com.co.tempcal.modelo.CalibrationInformationDTO;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

public class BasicInfoController {

	private static final Logger LoggerBasic = LoggerFactory.getLogger(BasicInfoController.class);

	public static final String CELSIUS = "CELSIUS";

	public static final String FAHRENHEIT = "FAHRENHEIT";

	/**
	 * Serial Length
	 */
	@FXML
	private TextField txtSerialLength;

	/**
	 * Serial Process
	 */
	@FXML
	private TextField txtSerial;

	/**
	 * Person who realize the process
	 */
	@FXML
	private TextField txtCalibrationPerson;

	/**
	 * Process Date
	 */
	@FXML
	private DatePicker datePicker;

	/**
	 * Tempertature Type
	 */
	@FXML
	private ComboBox<String> cmbTemperatureType;

	/**
	 * Tempertature Type
	 */
	@FXML
	private ComboBox<String> cmbMachineType;

	/**
	 * Tempertature Type
	 */
	@FXML
	private ComboBox<String> cmbCalibrationType;

	/**
	 * Owner Machine
	 */
	@FXML
	private TextField txtOwner;

	/**
	 * Certificate Number
	 */
	@FXML
	private TextField txtCertificateNumber;

	/**
	 * Model Machine
	 */
	@FXML
	private TextField txtMachineModel;

	/**
	 * Button Next
	 */
	@FXML
	private Button btnNext;

	/**
	 * Button Cancel
	 */
	@FXML
	private Button btnCancel;

	/**
	 * Actual Stage
	 */
	private Stage dialogStage;

	/**
	 * Process DTO
	 */
	private CalibrationInformationDTO infoCalibration;

	/**
	 * DTO con la informacion del Certificado
	 */
	private CertificateDTO infoCertificate;

	/**
	 * Referencia al Main Principal
	 */
	private MainGUI mainGUI;

	@FXML
	private void initialize() {

		LoggerBasic.info("Initialize Application");

		Locale.setDefault(Locale.ENGLISH);

		infoCalibration = new CalibrationInformationDTO();
		infoCertificate = new CertificateDTO();

		cmbTemperatureType.getItems().addAll(CELSIUS, FAHRENHEIT);
		cmbCalibrationType.getItems().addAll("NEW", "REPAIRED");
		cmbMachineType.getItems().addAll("OPEN", "CLOSED", "RESTRICTED");

		txtCalibrationPerson.addEventHandler(KeyEvent.KEY_RELEASED, changeUpperCase);
		txtOwner.addEventHandler(KeyEvent.KEY_RELEASED, changeUpperCase);

	}

	/**
	 * Set Actual Stage
	 * 
	 * @param dialogStage
	 */
	public void setDialogStage(Stage dialogStage) {
		this.dialogStage = dialogStage;
	}

	EventHandler<Event> changeUpperCase = new EventHandler<Event>() {

		@Override
		public void handle(Event event) {
			TextField txtField = (TextField) event.getSource();
			int currentPosition = txtField.getCaretPosition();
			txtField.setText(txtField.getText().toUpperCase());
			txtField.positionCaret(currentPosition);

		}
	};

	/**
	 * Action button next
	 */
	@FXML
	private void handleNext() {
		if (isFormValid()) {
			infoCalibration.setCalibrationDate(datePicker.getValue().toString());
			infoCalibration.setSerial(txtSerial.getText());
			infoCalibration.setCalibrationPerson(txtCalibrationPerson.getText());
			infoCalibration.setTemperatureType(cmbTemperatureType.getValue());

			infoCertificate.setOwner(txtOwner.getText());
			infoCertificate.setMachineModel(txtMachineModel.getText());
			infoCertificate.setCertificateNumber(txtCertificateNumber.getText());
			infoCertificate.setCalibratedDate(datePicker.getValue().toString());
			infoCertificate.setCalibrationType(cmbCalibrationType.getValue());
			infoCertificate.setTypeMachine(cmbMachineType.getValue());
			infoCertificate.setSerialLength(txtSerialLength.getText());
			
			
			mainGUI.showColdBathPanel(this.dialogStage, infoCalibration,
					infoCertificate);

		}
	}

	/**
	 * Accion del Boton Cancelar
	 */
	@FXML
	private void handleCancel() {
		dialogStage.close();
	}

	private boolean isFormValid() {
		String errorMessage = "";

		if (datePicker.getValue() == null || datePicker.getValue().toString().length() == 0) {
			errorMessage += "Select a Date \n";
		}
		if (txtSerial.getText() == null || txtSerial.getText().length() == 0) {
			errorMessage += "Set a Serial \n";
		} else {
			if (!Validations.validatedNumber(txtSerial.getText())) {
				errorMessage += "Only a numbers in the Serial field \n";
			}
		}
		if (cmbTemperatureType.getValue() == null || cmbTemperatureType.getValue().length() == 0) {
			errorMessage += "Choose the Temperature Type Celsius or Fahrenheit \n";
		}
		if (txtCalibrationPerson.getText() == null || txtCalibrationPerson.getText().length() == 0) {
			errorMessage += "Set the person who will performed process \n";
		}
		if (txtOwner.getText() == null || txtOwner.getText().length() == 0) {
			errorMessage += "Set the owner \n";
		}
		if (txtCertificateNumber.getText() == null || txtCertificateNumber.getText().length() == 0) {
			errorMessage += "Set the Certificate Number \n";
		}
		if (txtMachineModel.getText() == null || txtMachineModel.getText().length() == 0) {
			errorMessage += "Set the Machine Model \n";
		}

		if (cmbCalibrationType.getValue() == null || cmbCalibrationType.getValue().length() == 0) {
			errorMessage += "Choose the Calibration Type New or Repaired \n";
		}
		if (cmbMachineType.getValue() == null || cmbMachineType.getValue().length() == 0) {
			errorMessage += "Choose the Machine Type Open, Closed or Restricted \n";
		}
		if (txtSerialLength.getText() == null || txtSerialLength.getText().length() == 0) {
			errorMessage += "Set the serial Length \n";
		}

		if (errorMessage.length() == 0) {
			return true;
		} else {
			Validations.showAlert(errorMessage);
			return false;
		}
	}

	/**
	 * Is called by the main application to give a reference back to itself.
	 * 
	 * @param mainApp
	 */
	public void setMainApp(MainGUI mainApp) {
		this.mainGUI = mainApp;
	}

	public void changeUpperCase(TextField txtFIeld) {
		txtFIeld.setText(txtFIeld.getText().toUpperCase());
	}

}
