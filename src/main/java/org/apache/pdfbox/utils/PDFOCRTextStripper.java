package org.apache.pdfbox.utils;

import org.apache.pdfbox.cos.COSStream;
import org.apache.pdfbox.ocr.LocationData;
import org.apache.pdfbox.ocr.OCRConnector;
import org.apache.pdfbox.ocr.OCRConnectorFactory;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDResources;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;import org.apache.pdfbox.rendering.PDFRenderer;
import org.apache.pdfbox.text.TextPosition;
import org.apache.pdfbox.util.Matrix;
import org.apache.pdfbox.util.PDFTextStripper;

import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * Created by dimuthuupeksha on 3/26/14.
 */
public class PDFOCRTextStripper extends PDFTextStripper {
	private int currentPageNo=0;
	private int zoomFactor =3;
	public PDFOCRTextStripper() throws IOException {
		super();
		setSortByPosition(true);
	}
	
	private LocationData[] normalizeLocationData(LocationData[] data,int height){
		for (int i = 0; i < data.length; i++) {
			data[i].setBox_x1(data[i].getBox_x1()/zoomFactor);
			data[i].setBox_x2(data[i].getBox_x2()/zoomFactor);
			data[i].setBox_y1(height - data[i].getBox_y1()/zoomFactor);
			data[i].setBox_y2(height - data[i].getBox_y2()/zoomFactor);
		}
		return data;
	}
	
	@Override
    public void processStream(PDResources resources, COSStream cosStream,
    		PDRectangle drawingSize, int rotation) throws IOException {
		
		
		OCRConnector conn = null;
		LocationData[] locationData = null;
		try {
			conn = OCRConnectorFactory.createOCRConnector("tesseract");
			conn.init();
			PDFRenderer renderer = new PDFRenderer(document);
			BufferedImage image = renderer.renderImage(currentPageNo, zoomFactor);
			int width= image.getWidth()/zoomFactor;
			int height = image.getHeight()/zoomFactor;
			
			//System.out.println("Width "+width + " Height "+height );
			conn.setBufferedImage(image);
			locationData = conn.getLocationData();
			locationData = normalizeLocationData(locationData,height);
			for (int i = 0; i < locationData.length; i++) {
				/*System.out.print(locationData[i].getWord()+ " ");
				System.out.print(locationData[i].getBox_x1()+ ",");
				System.out.print(locationData[i].getBox_y1()+ ",");
				System.out.print(locationData[i].getBox_x2()+ ",");
				System.out.println(locationData[i].getBox_y2()+",");*/
				TextPosition textPosition = generateTextPosition(locationData[i], width, height, rotation);
				processTextPosition(textPosition);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		currentPageNo++;
    }
	
	private TextPosition generateTextPosition(LocationData locationData,float pageWidth,float pageHeight,int rotation){
		
    	Matrix textMatrixStart = new Matrix();
    	textMatrixStart.setValue(0, 0, 1);
    	textMatrixStart.setValue(0, 1, 0);
    	textMatrixStart.setValue(0, 2, 0);
    	
    	textMatrixStart.setValue(1, 0, 0);
    	textMatrixStart.setValue(1, 1, 1);
    	textMatrixStart.setValue(1, 2, 0);
    	
    	textMatrixStart.setValue(2, 0, locationData.getBox_x1());
    	textMatrixStart.setValue(2, 1, locationData.getBox_y2());
    	textMatrixStart.setValue(2, 2, 1);
    	
    	float endXPosition = locationData.getBox_x2();
    	float endYPosition =locationData.getBox_y2();
    	float totalVerticalDisplacementDisp =locationData.getBox_y1()-locationData.getBox_y2(); //Maximum error is height of the word
    	float widthText = locationData.getBox_x2()-locationData.getBox_x1();
    	
    	String c = locationData.getWord();
    	int []codePoints = new int[c.length()];
    	
    	for(int i=0;i<c.length();i++){
    		codePoints[i] = c.charAt(i);
    	}
    	
    	PDFont font = new PDType1Font();
    	float fontSizeText = widthText/c.length();
    	float spaceWidthDisp = fontSizeText/2; // This is an approximation for space width

    	TextPosition textPosition = new TextPosition(rotation, pageWidth, pageHeight, textMatrixStart, endXPosition,
        endYPosition, totalVerticalDisplacementDisp, widthText, spaceWidthDisp, c, codePoints, font,
        fontSizeText,12);
    	
    	return textPosition;
	}
}
