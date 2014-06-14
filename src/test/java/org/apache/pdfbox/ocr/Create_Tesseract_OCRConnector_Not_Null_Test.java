package org.apache.pdfbox.ocr;
import org.junit.Ignore;
import org.junit.Test;
import static org.junit.Assert.assertEquals;


public class Create_Tesseract_OCRConnector_Not_Null_Test{
	@Test
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
