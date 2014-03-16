package org.apache.pdfbox.ocr;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

import com.apache.pdfbox.ocr.tesseract.TessBaseAPI;

public class TesseractOCRConnector implements OCRConnector{

	private TessBaseAPI api;
	
	public TesseractOCRConnector(){
		api = new TessBaseAPI();
	}
	
	@Override
	public void setBufferedImage(BufferedImage image) {
		api.setBufferedImage(image);	
	}

	@Override
	public boolean init() {
		return api.init(TessBaseAPI.DEFAULT_DATA_PATH,TessBaseAPI.DEFAULT_LANG);
	}

	@Override
	public String getUTF8Text() {
		return api.getUTF8Text();
	}

	@Override
	public LocationData[] getLocationData() {
		api.getResultIterator();
		ArrayList<LocationData> dataList = new ArrayList<LocationData>();
		if (api.isResultIteratorAvailable()) {
			do {
				String word = api.getWord().trim();
				String locationString = api.getBoundingBox();
				String locationArr[] = locationString.split(",");
				if(word!=null&&locationArr!=null&&locationArr.length==4){
					int box_x1, box_y1, box_x2, box_y2;
					box_x1 = Integer.parseInt(locationArr[0]);
					box_y1 = Integer.parseInt(locationArr[1]);
					box_x2 = Integer.parseInt(locationArr[2]);
					box_y2 = Integer.parseInt(locationArr[3]);
					LocationData locData = new LocationData(word, box_x1, box_y1, box_x2, box_y2);
					dataList.add(locData);
				}
			} while (api.resultIteratorNext());
		}
		
		LocationData[] locationDataArray = new LocationData[0];
		locationDataArray=dataList.toArray(locationDataArray);
		return locationDataArray;
	}

	@Override
	public void end() {
		// TODO Auto-generated method stub
		
	}

}
