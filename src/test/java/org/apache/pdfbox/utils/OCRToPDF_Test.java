package org.apache.pdfbox.utils;

import org.apache.pdfbox.tools.OCRToPDF;
import org.junit.Ignore;
import org.junit.Test;

import java.io.IOException;

public class OCRToPDF_Test {
    @Test
    public void test() throws Exception{
        OCRToPDF ocr = new OCRToPDF();
        ocr.writeText("src/test/resources/samples/pdf2.pdf","temp.pdf");
    }
}