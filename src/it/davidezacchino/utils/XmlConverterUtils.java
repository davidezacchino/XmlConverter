package it.davidezacchino.utils;

import it.davidezacchino.exeption.XmlFileExeption;
import org.w3c.dom.Document;
import org.w3c.dom.DocumentType;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author davidezacchino
 */
public class XmlConverterUtils {

    private XmlConverterUtils() {
    }

    /**
     * @param inputXmlPath ex: "/Users/usertest/Desktop/test.xml"
     * @throws IOException
     */
    public static List<String> convertXmlToListString(String inputXmlPath) throws IOException {

        checkExtensionValidity(inputXmlPath);

        return convertXmlToListString(new FileInputStream(inputXmlPath));
    }

    /**
     * @param file
     * @throws IOException
     */
    public static List<String> convertXmlToListString(File file) throws IOException {

        checkExtensionValidity(file.getPath());

        return convertXmlToListString(new FileInputStream(file));
    }

    public static List<String> convertXmlToListString(InputStream inputStream) {
        return writeXmlDocumentToXmlFile(convertXMLInputStreamToXMLDocument(inputStream));
    }

    /**
     * Convert inputstream
     *
     * @param inputStream
     * @return
     */
    private static Document convertXMLInputStreamToXMLDocument(InputStream inputStream) {
        //API to obtain DOM Document instance
        try {
            //Create DocumentBuilder with default configuration
            DocumentBuilder builder = getDocumentBuilderFactory().newDocumentBuilder();

            //Parse the content to Document object
            return builder.parse(inputStream);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static void checkExtensionValidity(String inputXmlPath) throws XmlFileExeption {
        String extension = "";

        int i = inputXmlPath.lastIndexOf('.');
        if (i > 0) {
            extension = inputXmlPath.substring(i + 1);
        }

        if (extension.isEmpty() || !"xml".equals(extension)) {
            throw new XmlFileExeption("File has an invalid extension");
        }
    }

    private static DocumentBuilderFactory getDocumentBuilderFactory() throws ParserConfigurationException {
        //Parser that produces DOM object trees from XML content
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setFeature("http://apache.org/xml/features/nonvalidating/load-dtd-grammar", false);
        factory.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);
        return factory;
    }

    private static List<String> writeXmlDocumentToXmlFile(Document xmlDocument) {
        if (xmlDocument == null) {
            return Collections.emptyList();
        }
        xmlDocument.setXmlStandalone(true); //before creating the DOMSource

        try {
            Transformer transformer = getTransformer(xmlDocument);
            StringWriter writer = transformDocumentToStringWriter(xmlDocument, transformer);

            return getLinesFromXmlStringWriter(writer);
        } catch (TransformerException e) {
            return new ArrayList<>();
        }
    }

    private static List<String> getLinesFromXmlStringWriter(StringWriter writer) {
        List<String> stringList = new ArrayList<>();

        for (String line : writer.getBuffer().toString().split("\n")) {
            Collections.addAll(stringList, line.split("\r"));
        }
        return stringList;
    }


    private static Transformer getTransformer(Document xmlDocument) throws TransformerConfigurationException {
        Transformer transformer = getTransformerFactory().newTransformer();
        setTransformerOutputProperties(xmlDocument, transformer);
        return transformer;
    }


    private static TransformerFactory getTransformerFactory() {
        TransformerFactory tf = TransformerFactory.newInstance();
        tf.setAttribute(XMLConstants.ACCESS_EXTERNAL_DTD, "");
        tf.setAttribute(XMLConstants.ACCESS_EXTERNAL_STYLESHEET, "");
        return tf;
    }

    private static void setTransformerOutputProperties(Document xmlDocument, Transformer transformer) {
        DocumentType doctype = xmlDocument.getDoctype();
        if (doctype != null) {
            if (doctype.getPublicId() != null) {
                transformer.setOutputProperty(OutputKeys.DOCTYPE_PUBLIC, doctype.getPublicId());
            }
            if (doctype.getSystemId() != null) {
                transformer.setOutputProperty(OutputKeys.DOCTYPE_SYSTEM, doctype.getSystemId());
            }
        }

        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
    }

    private static StringWriter transformDocumentToStringWriter(Document xmlDocument, Transformer transformer) throws TransformerException {
        StringWriter writer = new StringWriter();
        transformer.transform(new DOMSource(xmlDocument), new StreamResult(writer));
        return writer;
    }
}
