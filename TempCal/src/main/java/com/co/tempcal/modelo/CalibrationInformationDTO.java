package com.co.tempcal.modelo;

import com.co.tempcal.vista.BasicInfoController;

public class CalibrationInformationDTO {
	
	private String calibrationDate;
	
	private String temperatureType;
	
	private String calibrationPerson;
	
	private String serial;
	
	private String coldBathTemp1;
	
	private String hotBathTemp;
	
	private String hotSensorTemp;
	
	private String testColdBathTemp;
	
	private String testColdSensorTemp;
	
	private String hotSensorTemp2;
	
	private String resultProcess;
	
	private Long timeProcedure;

	public String getCalibrationDate() {
		return calibrationDate;
	}

	public void setCalibrationDate(String calibrationDate) {
		this.calibrationDate = calibrationDate;
	}

	public String getTemperatureType() {
		return temperatureType;
	}

	public void setTemperatureType(String temperatureType) {
		this.temperatureType = temperatureType;

		// Celsius or Fahrenheit
		if (temperatureType.equals(BasicInfoController.CELSIUS)) {
			hotBathTemp = "70";
		} else {
			if (temperatureType.equals(BasicInfoController.FAHRENHEIT)) {
				hotBathTemp = "158";
			}
		}

	}

	public String getCalibrationPerson() {
		return calibrationPerson;
	}

	public void setCalibrationPerson(String calibrationPerson) {
		this.calibrationPerson = calibrationPerson;
	}

	public String getSerial() {
		return serial;
	}

	public void setSerial(String serial) {
		this.serial = serial;
	}

	public String getColdBathTemp1() {
		return coldBathTemp1;
	}

	public void setColdBathTemp1(String coldBathTemp1) {
		this.coldBathTemp1 = coldBathTemp1;
	}

	public String getHotSensorTemp() {
		return hotSensorTemp;
	}

	public void setHotSensorTemp(String hotSensorTemp) {
		this.hotSensorTemp = hotSensorTemp;
	}

	public String getHotSensorTemp2() {
		return hotSensorTemp2;
	}

	public void setHotSensorTemp2(String hotSensorTemp2) {
		this.hotSensorTemp2 = hotSensorTemp2;
	}

	public String getTestColdBathTemp() {
		return testColdBathTemp;
	}

	public void setTestColdBathTemp(String testColdBathTemp) {
		this.testColdBathTemp = testColdBathTemp;
	}

	public String getTestColdSensorTemp() {
		return testColdSensorTemp;
	}

	public void setTestColdSensorTemp(String testColdSensorTemp) {
		this.testColdSensorTemp = testColdSensorTemp;
	}

	public String getResultProcess() {
		return resultProcess;
	}

	public void setResultProcess(String resultProcess) {
		this.resultProcess = resultProcess;
	}

	public String getHotBathTemp() {
		return hotBathTemp;
	}

	public void setHotBathTemp(String hotBathTemp) {
		this.hotBathTemp = hotBathTemp;
	}

	public Long getTimeProcedure() {
		return timeProcedure;
	}

	public void setTimeProcedure(Long timeProcedure) {
		this.timeProcedure = timeProcedure;
	}

	
	

	
}
