package com.co.tempcal.modelo;

public class CertificateDTO {
	
	/**
	 * machine owner
	 */
	private String owner;
	
	/**
	 * certificate number
	 */
	private String certificateNumber;
	
	/**
	 * machine type New or Repaired
	 */
	private String typeMachine;
	
	/**
	 * calibrated date
	 */
	private String calibratedDate;
	
	/**
	 * Machine Model
	 */
	private String machineModel;
	
	/**
	 * Calibration Type Open, Restricted o Closed
	 */
	private String calibrationType;
	
	/**
	 * Serial Length
	 */
	private String serialLength;

	
	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public String getCertificateNumber() {
		return certificateNumber;
	}

	public void setCertificateNumber(String certificateNumber) {
		this.certificateNumber = certificateNumber;
	}

	public String getMachineModel() {
		return machineModel;
	}

	public void setMachineModel(String machineModel) {
		this.machineModel = machineModel;
	}

	public String getTypeMachine() {
		return typeMachine;
	}

	public void setTypeMachine(String typeMachine) {
		this.typeMachine = typeMachine;
	}

	public String getCalibratedDate() {
		return calibratedDate;
	}

	public void setCalibratedDate(String calibratedDate) {
		this.calibratedDate = calibratedDate;
	}

	public String getCalibrationType() {
		return calibrationType;
	}

	public void setCalibrationType(String calibrationType) {
		this.calibrationType = calibrationType;
	}

	public String getSerialLength() {
		return serialLength;
	}

	public void setSerialLength(String serialLength) {
		this.serialLength = serialLength;
	}
	
}
