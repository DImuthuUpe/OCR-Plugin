package org.apache.pdfbox.utils;

import junit.framework.TestCase;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.util.PDFTextStripper;

import java.io.IOException;

/**
 * Created by dimuthuupeksha on 3/26/14.
 */
public class PDFOCTRTextStripper_Test extends TestCase{
    public void test() throws IOException{
        PDFTextStripper ocrStripper = new PDFOCRTextStripper();
        //PDFTextStripper ocrStripper = new PDFTextStripper();
        PDDocument document = PDDocument.load("/Users/dimuthuupeksha/Documents/Academic/OCR-Plugin/Hello_World.pdf");
        String data = ocrStripper.getText(document);
        System.out.println("data "+data);
        assertNotNull(ocrStripper);
    }
}
