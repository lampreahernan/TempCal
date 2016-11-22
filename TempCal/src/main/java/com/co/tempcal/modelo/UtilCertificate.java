package com.co.tempcal.modelo;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

import org.apache.poi.xwpf.converter.pdf.PdfConverter;
import org.apache.poi.xwpf.converter.pdf.PdfOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UtilCertificate {

	private static final Logger LoggerUtil = LoggerFactory.getLogger(UtilCertificate.class);

	public static final String FILE_PATH_SRC = new File("CertificateTemplate.docx").getAbsolutePath();
	public static final String FILE_PATH_DIR = new File("temp").getAbsolutePath();
	public static final String FILE_PATH_PDF = new File("certificates").getAbsolutePath();

	public static void createCertitificate(CertificateDTO certificate, CalibrationInformationDTO calibration,
			File file) {

		LoggerUtil.info("Path Template: " + FILE_PATH_SRC);

		String nombreArchivoTemp = FILE_PATH_DIR + "\\CertificateMachine" + certificate.getMachineModel() + ".docx";
		FileInputStream fs = null;
		XWPFDocument doc = null;

		LoggerUtil.info("Path Archivo: " + nombreArchivoTemp);

		try {

			createFolders();
			fs = new FileInputStream(FILE_PATH_SRC);
			doc = new XWPFDocument(fs);

			doc = replaceText(doc, certificate, calibration);

			saveWord(nombreArchivoTemp, doc);
			saveWord(file.getPath(), doc);

			//createPDF(nombreArchivoTemp, file.getPath());
		} catch (FileNotFoundException e) {
			LoggerUtil.error(e.toString());
		} catch (IOException e) {
			LoggerUtil.error(e.toString());
		}

	}

	private static void createFolders() throws IOException {
		File path_dir = new File(FILE_PATH_DIR);
		path_dir.mkdirs();

		File path_pdf = new File(FILE_PATH_PDF);
		path_pdf.mkdirs();
	}

	private static XWPFDocument replaceText(XWPFDocument doc, CertificateDTO certificate,
			CalibrationInformationDTO calibration) {

		List<XWPFParagraph> listaParrafos = doc.getParagraphs();

		for (Iterator<XWPFParagraph> iterator = listaParrafos.iterator(); iterator.hasNext();) {
			XWPFParagraph xwpfParagraph = (XWPFParagraph) iterator.next();

			for (XWPFRun run : xwpfParagraph.getRuns()) {
				if (run != null) {
					String texto = run.getText(0);
					if (texto != null) {
						if (texto.contains("ownername")) {
							texto = texto.replace("ownername", certificate.getOwner());
							run.setText(texto, 0);
						}
						if (texto.contains("certificatenumber")) {
							texto = texto.replace("certificatenumber", certificate.getCertificateNumber() + "-"
									+ certificate.getCalibrationType().substring(0, 1));
							run.setText(texto, 0);
						}
						if (texto.contains("typetemp")) {
							texto = texto.replace("typetemp", calibration.getTemperatureType());
							run.setText(texto, 0);
						}
						if (texto.contains("datecalibrated")) {
							texto = texto.replace("datecalibrated", certificate.getCalibratedDate());
							run.setText(texto, 0);
						}
						if (texto.contains("modelmachine")) {
							texto = texto.replace("modelmachine", certificate.getMachineModel());
							run.setText(texto, 0);
						}
						if (texto.contains("serial")) {
							texto = texto.replace("serial", calibration.getSerial());
							run.setText(texto, 0);
						}
						if (texto.contains("length")) {
							texto = texto.replace("length", certificate.getSerialLength());
							run.setText(texto, 0);
						}
						if (texto.contains("typecertificate")) {
							texto = texto.replace("typecertificate", certificate.getCalibrationType());
							run.setText(texto, 0);
						}
					}
				}

			}

		}

		return doc;
	}

	private static void saveWord(String filePath, XWPFDocument doc) throws FileNotFoundException, IOException {
		FileOutputStream out = null;
		try {
			out = new FileOutputStream(filePath);
			doc.write(out);
		} finally {
			out.close();
		}
	}

	private static void createPDF(String fileSource, String fileDir) throws FileNotFoundException, IOException {

		String filePath = fileSource;
		FileInputStream fInputStream = new FileInputStream(new File(filePath));
		XWPFDocument document = new XWPFDocument(fInputStream);

		LoggerUtil.info("Path Pdf: " + fileDir);

		File outFile = new File(fileDir);
		outFile.getParentFile().mkdirs();

		OutputStream out = new FileOutputStream(outFile);
		PdfOptions options = PdfOptions.create();
		PdfConverter.getInstance().convert(document, out, options);

	}

}
