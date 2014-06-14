package org.apache.pdfbox.utils;


import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.util.PDFTextStripper;
import org.junit.Ignore;
import org.junit.Test;

import java.io.IOException;
import static org.junit.Assert.assertNotNull;
/**
 * Created by dimuthuupeksha on 3/26/14.
 */
public class PDFOCTRTextStripper_Test{
	@Test
	@Ignore
    public void testPDF1() throws IOException{
        PDFTextStripper ocrStripper = new PDFOCRTextStripper();
        //PDFTextStripper ocrStripper = new PDFTextStripper();
        PDDocument document = PDDocument.load("src/test/resources/samples/pdf2.pdf");
        String data = ocrStripper.getText(document);
        document.close();
        System.out.println("data "+data);
        assertNotNull(ocrStripper);
    }
	@Test
	@Ignore
    public void testPDF2() throws IOException{
        PDFTextStripper ocrStripper = new PDFOCRTextStripper();
        //PDFTextStripper ocrStripper = new PDFTextStripper();
        PDDocument document = PDDocument.load("src/test/resources/samples/pdf2.pdf");
        String data = ocrStripper.getText(document);
        document.close();
        System.out.println("data "+data);
        assertNotNull(ocrStripper);
    }
}
