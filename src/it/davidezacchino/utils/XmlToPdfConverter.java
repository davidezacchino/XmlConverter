package it.davidezacchino.utils;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * @author davidezacchino
 */
public class XmlToPdfConverter {

    private XmlToPdfConverter() {
    }

    /**
     * @param inputXmlPath  ex: "/Users/usertest/Desktop/test.xml"
     * @param outputPdfPath ex: "/Users/usertest/Desktop/test.pdf"
     * @throws IOException
     */
    public static void convertXmlToPdf(String inputXmlPath, String outputPdfPath) throws IOException {
        convertXmlToPdf(new FileInputStream(inputXmlPath), outputPdfPath);
    }

    /**
     * @param file
     * @param outputPdfPath ex: "/Users/usertest/Desktop/test.pdf"
     * @throws IOException
     */
    public static void convertXmlToPdf(File file, String outputPdfPath) throws IOException {
        convertXmlToPdf(new FileInputStream(file), outputPdfPath);
    }

    /**
     * @param xmlStringList
     * @param outputPdfPath ex: "/Users/usertest/Desktop/test.pdf"
     * @throws IOException
     */
    public static void convertXmlToPdf(List<String> xmlStringList, String outputPdfPath) throws IOException {
        convert(xmlStringList, outputPdfPath);
    }

    /**
     * @param inputStream
     * @param outputPdfPath ex: "/Users/usertest/Desktop/test.pdf"
     * @throws IOException
     */
    public static void convertXmlToPdf(InputStream inputStream, String outputPdfPath) throws IOException {
        convert(XmlConverterUtils.convertXmlToListString(inputStream), outputPdfPath);
    }

    private static void convert(List<String> xmlStringList, String outputPdfPath) throws IOException {
        PDPage page = new PDPage();
        PDDocument doc = new PDDocument();
        try (PDPageContentStream contentStream = new PDPageContentStream(doc, page)) {
            doc.addPage(page);

            //Begin the Content stream
            contentStream.beginText();

            //Setting the font to the Content stream
            contentStream.setFont(PDType1Font.TIMES_ROMAN, 10);

            //Setting the leading
            contentStream.setLeading(14.5f);

            //Setting the position for the line
            contentStream.newLineAtOffset(25, 725);

            //Adding text in the form of string
            for (String text : xmlStringList) {
                contentStream.showText(text);
                contentStream.newLine();
            }
            //Ending the content stream
            contentStream.endText();

        } catch (IOException e) {
            e.printStackTrace();
        }
        doc.save(outputPdfPath);
    }


}
