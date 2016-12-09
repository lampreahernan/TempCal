package com.co.tempcal.vista;

import com.co.tempcal.controlador.MainGUI;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;

public class MainGUIController {

	@FXML
	private Button btnNewProcess;

	@FXML
	private Button btnExit;
	
	@FXML
	private MenuItem menuClose;
	
	/**
	 * Referencia al Main Principal
	 */
	private MainGUI mainVentana;
	
	
	/**
	 * Constructor
	 */
	public MainGUIController() {
	}
	
	/**
	 * Called when the user clicks on the delete button.
	 */
	@FXML
	private void handlerNewProcess() {		
		mainVentana.showBasicInformationPanel();
	}
	
	/**
	 * Called when the user clicks on the Exit button.
	 */
	@FXML
	private void handlerExit() {		
		mainVentana.closeStage();
	}
	
		
	 /**
     * Is called by the main application to give a reference back to itself.
     * 
     * @param mainApp
     */
    public void setMainApp(MainGUI mainApp) {
        this.mainVentana = mainApp;
    }

	
	
	
}
