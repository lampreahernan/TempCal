package com.co.tempcal.modelo;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimerTask;

import javafx.scene.control.TextField;

public class UtilTimer extends TimerTask{

	SimpleDateFormat format = new SimpleDateFormat("mm:ss"); 
	TextField currentTextField;
	long currentMiliSeconds;
	final Calendar currentTime;
	
	public UtilTimer(TextField textField) {
		currentTextField = textField;
		currentMiliSeconds = 0;
		currentTime = Calendar.getInstance();
	}
	
	@Override
	public void run() {
		currentMiliSeconds+=1000;
		currentTime.setTimeInMillis(currentMiliSeconds);
		currentTextField.setText(this.format.format(currentTime.getTime()));
	}
}
