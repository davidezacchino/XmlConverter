package it.davidezacchino;

import it.davidezacchino.utils.XmlConverterUtils;
import it.davidezacchino.utils.XmlToPdfConverter;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        String input = "/Users/davidezacchino/Desktop/2021475EULOACKPCCSIS1.xml";
        String output = "/Users/davidezacchino/Desktop/2021475EULOACKPCCSIS1.pdf";

        XmlToPdfConverter.convertXmlToPdf(XmlConverterUtils.convertXmlToListString(input), output);
    }

}
