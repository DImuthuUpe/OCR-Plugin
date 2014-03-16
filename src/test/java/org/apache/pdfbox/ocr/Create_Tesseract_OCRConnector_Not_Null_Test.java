package org.apache.pdfbox.ocr;
import junit.framework.TestCase;

public class Create_Tesseract_OCRConnector_Not_Null_Test extends TestCase{

	public void test() {
		OCRConnector conn=null;
		try {
			conn= OCRConnectorFactory.createOCRConnector("tesseract");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		assertEquals(conn!=null, true);
		if(conn!=null){
			assertEquals(conn.init(), true);
		}
	}

}
