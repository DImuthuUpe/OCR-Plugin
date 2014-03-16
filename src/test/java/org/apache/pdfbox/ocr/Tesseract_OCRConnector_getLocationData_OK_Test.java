package org.apache.pdfbox.ocr;

import java.awt.image.BufferedImage;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;

import junit.framework.TestCase;

public class Tesseract_OCRConnector_getLocationData_OK_Test extends TestCase {
	public void test() {
		OCRConnector conn = null;
		LocationData[] locationData = null;
		try {
			conn = OCRConnectorFactory.createOCRConnector("tesseract");
			conn.init();
			PDDocument document = PDDocument
					.load("src/test/resources/samples/pdf1.pdf");
			PDFRenderer renderer = new PDFRenderer(document);
			BufferedImage image = renderer.renderImage(0, 3);
			conn.setBufferedImage(image);
			locationData = conn.getLocationData();

		} catch (Exception e) {
			e.printStackTrace();
		}
		for (int i = 0; i < locationData.length; i++) {
			System.out.print(locationData[i].getWord()+ " ");
			System.out.print(locationData[i].getBox_x1()+ ",");
			System.out.print(locationData[i].getBox_y1()+ ",");
			System.out.print(locationData[i].getBox_x2()+ ",");
			System.out.println(locationData[i].getBox_y2());
		}
		assertNotNull(locationData);
		assertTrue(locationData.length>0);
		
	}
}
