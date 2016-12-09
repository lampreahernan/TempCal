package com.co.tempcal.modelo;

import com.co.tempcal.vista.BasicInfoController;

public class TemperatureCalculations {
	
	/**
	 * Error Range for Celcius 
	 */
	public final float ERROR_RANGE_CELCIUS = (float) 0.2;
	
	/**
	 * Error Range for Fahrenheit
	 */
	public final float ERROR_RANGE_FAHRENHEIT = (float) 0.5;
	
	/**
	 * This method calculate the temperature factor
	 * 
	 * @param coldBath
	 * @param hotUncalibSensor
	 * @param temperatureType
	 * @return
	 */
	public float calculateFactor(String coldBathTemp, String hotUncalibSensorTemp, String temperatureType) {

		float factor = 0;
		float temperature = Float.parseFloat(temperatureType);
		float coldBath = Float.parseFloat(coldBathTemp);
		float hotUncalibSensor = Float.parseFloat(hotUncalibSensorTemp);

		factor = (float) (hotUncalibSensor * ((temperature - coldBath) / (hotUncalibSensor - coldBath)));

		return factor;
	}
	
	/**
	 * This method validates the result of calibration 
	 * @param coldBathTemp2
	 * @param coldSensorTemp
	 * @param temperatureType
	 * @return
	 */
	public boolean evaluateCalibration(String coldBathTemp2, String coldSensorTemp, String temperatureType){
		
		float coldBath=Float.parseFloat(coldBathTemp2);
		float coldSensor=Float.parseFloat(coldSensorTemp);
		float errorRange = 0;
		boolean resultProcess = false;
		
		// Celsius or Fahrenheit
		if (temperatureType.equals(BasicInfoController.CELSIUS)) {
			errorRange = ERROR_RANGE_CELCIUS;
		} else {
			if (temperatureType.equals(BasicInfoController.FAHRENHEIT)) {
				errorRange = ERROR_RANGE_FAHRENHEIT;
			}
		}
		
		if(coldSensor>(coldBath+errorRange)){
			resultProcess=false;
		}else{
			if(coldSensor<(coldBath-errorRange)){
				resultProcess=false;
			}else{
				resultProcess=true;
			}
		}
		
		return resultProcess;
	
	}

}
