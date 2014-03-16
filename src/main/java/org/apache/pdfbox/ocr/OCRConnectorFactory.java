package org.apache.pdfbox.ocr;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;


public class OCRConnectorFactory {
	public static OCRConnector createOCRConnector(String type) throws IOException, ClassNotFoundException, InstantiationException, IllegalAccessException{
		Properties config = new Properties();
		InputStream input = new FileInputStream("src/main/resources/ocr_config.properties");
		config.load(input);
		String ocrClassName = config.getProperty(type);
		Class<?> cls = Class.forName(ocrClassName);
		OCRConnector connector = (OCRConnector) cls.newInstance();
		return connector;
	}
}
