/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.pdfbox.ocr;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

import com.apache.pdfbox.ocr.tesseract.TessBaseAPI;

public class TesseractOCRConnector implements OCRConnector{

	private TessBaseAPI api;
	
	public TesseractOCRConnector(){
		api = new TessBaseAPI();
	}

	public void setBufferedImage(BufferedImage image) {
		api.setBufferedImage(image);	
	}


	public boolean init() {
		return api.init(TessBaseAPI.DEFAULT_DATA_PATH,TessBaseAPI.DEFAULT_LANG);
	}


	public String getUTF8Text() {
		return api.getUTF8Text();
	}


	public LocationData[] getLocationData() {
		api.getResultIterator();
		ArrayList<LocationData> dataList = new ArrayList<LocationData>();
		if (api.isResultIteratorAvailable()) {
			do {
				String word = api.getWord().trim();
				String locationString = api.getBoundingBox();
				String locationArr[] = locationString.split(",");
				if(word!=null&&locationArr!=null&&locationArr.length==4){
					float box_x1, box_y1, box_x2, box_y2;
					box_x1 = Float.parseFloat(locationArr[0]);
					box_y1 = Float.parseFloat(locationArr[1]);
					box_x2 = Float.parseFloat(locationArr[2]);
					box_y2 = Float.parseFloat(locationArr[3]);
					LocationData locData = new LocationData(word, box_x1, box_y1, box_x2, box_y2);
					dataList.add(locData);
				}
			} while (api.resultIteratorNext());
		}
		
		LocationData[] locationDataArray = new LocationData[0];
		locationDataArray=dataList.toArray(locationDataArray);
		return locationDataArray;
	}


	public void end() {
		// TODO Auto-generated method stub
		
	}
	
	

}
