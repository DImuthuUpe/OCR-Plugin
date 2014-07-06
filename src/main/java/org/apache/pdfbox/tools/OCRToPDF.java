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

package org.apache.pdfbox.tools;

import org.apache.pdfbox.ocr.LocationData;
import org.apache.pdfbox.ocr.OCRConnector;
import org.apache.pdfbox.ocr.OCRConnectorFactory;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.edit.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.rendering.PDFRenderer;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class OCRToPDF {

    private static final String START_PAGE = "-startPage";
    private static final String END_PAGE = "-endPage";

    private int startPage = 0;
    private int endPage = Integer.MAX_VALUE;

    private LocationData[] normalizeLocationData(LocationData[] data, int height, int zoomFactor) {
        for (int i = 0; i < data.length; i++) {
            data[i].setBox_x1(data[i].getBox_x1() / zoomFactor);
            data[i].setBox_x2(data[i].getBox_x2() / zoomFactor);
            data[i].setBox_y1(height - data[i].getBox_y1() / zoomFactor);
            data[i].setBox_y2(height - data[i].getBox_y2() / zoomFactor);
        }
        return data;
    }

    private LocationData[] getPageLocationData(int pageNo, PDDocument document) throws Exception {
        int zoomFactor = 3;
        LocationData[] locationData = null;
        OCRConnector conn = OCRConnectorFactory.createOCRConnector("tesseract");
        conn.init();
        PDFRenderer renderer = new PDFRenderer(document);
        BufferedImage image = renderer.renderImage(pageNo, zoomFactor);
        int width = image.getWidth() / zoomFactor;
        int height = image.getHeight() / zoomFactor;
        conn.setBufferedImage(image);
        locationData = conn.getLocationData();
        locationData = normalizeLocationData(locationData, height, zoomFactor);
        return locationData;
    }

    public void writeText(String source, String target) throws Exception {
        File file = new File(source);
        PDDocument document = PDDocument.load(file);
        if (document.getNumberOfPages() < endPage) {
            endPage = document.getNumberOfPages() - 1;
        }

        for (int pgNo = startPage; pgNo <= endPage; pgNo++) {
            LocationData[] locationData = getPageLocationData(pgNo, document);

            PDPage page = document.getPage(pgNo);
            PDFont font = PDType1Font.HELVETICA_BOLD;

            PDPageContentStream contentStream = new PDPageContentStream(document, page, true, false);

            contentStream.beginText();
            for (int i = 0; i < locationData.length; i++) {
                System.out.print(locationData[i].getWord() + " ");
                System.out.print(locationData[i].getBox_x1() + " ");
                System.out.println(locationData[i].getBox_y2() + " ");
                contentStream.setFont(font, 12);
                if (i != 0) {
                    contentStream.moveTextPositionByAmount(locationData[i].getBox_x1() - locationData[i - 1].getBox_x1(), locationData[i].getBox_y2() - locationData[i - 1].getBox_y2());
                } else {
                    contentStream.moveTextPositionByAmount(locationData[i].getBox_x1(), locationData[i].getBox_y2());
                }
                contentStream.drawString(locationData[i].getWord());
            }
            contentStream.endText();

            contentStream.close();
        }

        document.save(new File(target));
        document.close();
    }

    public void startExtraction(String[] args) throws Exception {
        String sourcePDFFile = null;
        String targetPDFFile = null;

        for (int i = 0; i < args.length; i++) {
            if (args[i].equals(START_PAGE)) {
                i++;
                if (i >= args.length) {
                    usage();
                }
                startPage = Integer.parseInt(args[i]);
            } else if (args[i].equals(END_PAGE)) {
                i++;
                if (i >= args.length) {
                    usage();
                }
                endPage = Integer.parseInt(args[i]);
            } else {
                if (sourcePDFFile == null) {
                    sourcePDFFile = args[i];
                } else {
                    targetPDFFile = args[i];
                }
            }
        }

        if (sourcePDFFile == null) {
            usage();
        } else {
            if (targetPDFFile == null) {
                targetPDFFile = sourcePDFFile;
            }
            writeText(sourcePDFFile, targetPDFFile);
        }


    }

    public static void main(String args[]) throws Exception {
        OCRToPDF ocr = new OCRToPDF();
        ocr.startExtraction(args);
    }

    private static void usage() {
        System.err
                .println("Usage: java -jar pdfbox-app-x.y.z.jar OCRToPDF [OPTIONS] <Source PDF file> <Target PDF file>\n"
                        + "  -startPage <number>          The first page to start extraction(1 based)\n"
                        + "  -endPage <number>            The last page to extract(inclusive)\n"
                        + "  <Source PDF file>            The PDF document to use\n"
                        + "  <Target PDF file>            The target PDF document\n");
        System.exit(1);
    }
}
