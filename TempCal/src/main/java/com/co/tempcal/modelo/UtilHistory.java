package com.co.tempcal.modelo;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UtilHistory {

	/**
	 * Logger
	 */
	private static final Logger LoggerHistory = LoggerFactory.getLogger(UtilHistory.class);


	public static void saveHistory(CalibrationInformationDTO info) {
		File file = new File("CalibrationHistory.xlsx");
		FileInputStream fIP;
		XSSFRow row;
		Cell cell;
		int numLastRow = 0;

		try {
			fIP = new FileInputStream(file);

			XSSFWorkbook workbook = new XSSFWorkbook(fIP);
			if (file.isFile() && file.exists()) {

				XSSFSheet historySheet = workbook.getSheetAt(0);
				numLastRow = historySheet.getLastRowNum();

				row = historySheet.createRow(numLastRow + 1);

				cell = row.createCell(0);
				cell.setCellValue(info.getCalibrationDate());

				cell = row.createCell(1);
				cell.setCellValue(info.getTemperatureType());

				cell = row.createCell(2);
				cell.setCellValue(info.getCalibrationPerson());

				cell = row.createCell(3);
				cell.setCellValue(info.getSerial());

				cell = row.createCell(4);
				cell.setCellValue(info.getColdBathTemp1());

				cell = row.createCell(5);
				cell.setCellValue(info.getHotSensorTemp());

				cell = row.createCell(6);
				cell.setCellValue(info.getHotBathTemp());

				cell = row.createCell(7);
				cell.setCellValue(info.getHotSensorTemp2());

				cell = row.createCell(8);
				cell.setCellValue(info.getTestColdBathTemp());

				cell = row.createCell(9);
				cell.setCellValue(info.getTestColdSensorTemp());

				cell = row.createCell(10);
				cell.setCellValue(info.getResultProcess());

				closeHistoryFile(workbook);

			} else {
				LoggerHistory.error("The History File not exist");
			}

		} catch (IOException e) {
			Validations.showErrorAlert("Can't save this process in the history, the file is open");
			LoggerHistory.error(e.toString());
		} catch (Exception e) {
			LoggerHistory.error(e.toString());
		}

	}

	public static void createHistoryFile() throws IOException {
			
		if(!new File("CalibrationHistory.xlsx").exists()) { 
			XSSFWorkbook fileHistory = new XSSFWorkbook();
			XSSFSheet historySheet = fileHistory.createSheet("Calibration History");
			XSSFRow row;

			Object[] headers = new Object[] { "CALIBRATION DATE", "TEMPERATURE TYPE", "CALIBRATION BY", "SERIAL",
					"COLD BATH TEMP", "HOT UNCALIB SENSOR TEMP", "HOT BATH TEMP", "CALCULATE FACTOR", "TEST COLD BATH TEMP",
					"TEST COLD SENSOR TEMP", "RESULT"};

			row = historySheet.createRow(0);
			int cellid = 0;
			for (Object obj : headers) {
				Cell cell = row.createCell(cellid++);
				cell.setCellValue((String) obj);
			}

			closeHistoryFile(fileHistory);
		}

	}

	private static void closeHistoryFile(XSSFWorkbook workbook) throws IOException {
		FileOutputStream out;
		out = new FileOutputStream(new File("CalibrationHistory.xlsx"));
		workbook.write(out);
		out.close();

	}

}
