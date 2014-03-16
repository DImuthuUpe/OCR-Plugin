package org.apache.ocr;

import java.awt.image.BufferedImage;

import com.apache.pdfbox.ocr.tesseract.TessBaseAPI;

public class TesseractOCRConnector implements OCRConnector{

	private TessBaseAPI api;
	
	public TesseractOCRConnector(){
		api = new TessBaseAPI();
	}
	
	@Override
	public void setBufferedImage(BufferedImage image) {
		
		
	}

	@Override
	public boolean init() {
		return api.init(TessBaseAPI.DEFAULT_DATA_PATH,TessBaseAPI.DEFAULT_LANG);
	}

	@Override
	public String getUTF8Text() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LocationData getLocationData() {
		// TODO Auto-generated method stub
		return null;
	}

}
