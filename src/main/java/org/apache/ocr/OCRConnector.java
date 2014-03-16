package org.apache.ocr;

import java.awt.image.BufferedImage;

public interface OCRConnector {
	public void setBufferedImage(BufferedImage image);
	public boolean init();
	public String getUTF8Text();
	public LocationData getLocationData();
}
