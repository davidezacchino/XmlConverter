package it.davidezacchino.exeption;

import java.io.IOException;

public class XmlFileExeption extends IOException {

    public XmlFileExeption() {
        super();
    }

    public XmlFileExeption(String errorMessage) {
        super(errorMessage);
    }
}
