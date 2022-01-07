package it.davidezacchino.utils;

import it.davidezacchino.exeption.XmlFileExeption;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.List;

class XmlConverterUtilsTest {

    @Test
    void testConvertXmlToListStringByPath() throws IOException {
        String inputPath = this.getClass().getClassLoader().getResource("test.xml").getPath();
        List<String> converted = XmlConverterUtils.convertXmlToListString(inputPath);
        Assertions.assertFalse(converted.isEmpty());
    }

    @Test
    void testConvertXmlToListStringByFile() throws IOException {
        File fileInput = new File(this.getClass().getClassLoader().getResource("test.xml").getPath());
        List<String> converted = XmlConverterUtils.convertXmlToListString(fileInput);
        Assertions.assertFalse(converted.isEmpty());
    }

    @Test
    void testXmlFileExeptionByInvalidPath() {
        String inputPath = this.getClass().getClassLoader().getResource("test.ml").getPath();
        Assertions.assertThrows(XmlFileExeption.class, () -> XmlConverterUtils.convertXmlToListString(inputPath));
    }

    @Test
    void testXmlFileExeptionByInvalidFile() {
        File fileInput = new File(this.getClass().getClassLoader().getResource("test.ml").getPath());
        Assertions.assertThrows(XmlFileExeption.class, () -> XmlConverterUtils.convertXmlToListString(fileInput));
    }

}