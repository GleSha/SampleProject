package filewriting;

import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

public class XMLWriter {
    private boolean isOpen = false;
    private final String fileName;
    private XMLStreamWriter xMLStreamWriter;

    public XMLWriter(String fileName) {
        this.fileName = fileName;
    }

    public boolean isOpen()  {
        return isOpen;
    }

    public void open() throws FileNotFoundException, XMLStreamException {
        if (!isOpen) {
            xMLStreamWriter = XMLOutputFactory.newInstance()
                    .createXMLStreamWriter(new FileOutputStream(fileName));
            xMLStreamWriter.writeStartDocument();
            isOpen = true;
        }
    }

    public void close() throws XMLStreamException {
        if (isOpen) {
            xMLStreamWriter.writeCharacters("\r\n");
            xMLStreamWriter.writeEndDocument();
            xMLStreamWriter.flush();
            xMLStreamWriter.close();
            isOpen = false;
        }
    }

    public void writeStartElement(String elementName) throws XMLStreamException {
        if (isOpen) {
            xMLStreamWriter.writeCharacters("\r\n");
            xMLStreamWriter.writeStartElement(elementName);
        }
    }

    public void writeEndElement() throws XMLStreamException {
        if (isOpen) {
            xMLStreamWriter.writeCharacters("\r\n");
            xMLStreamWriter.writeEndElement();
        }
    }

    public void writeField(String fieldName, String value) throws XMLStreamException {
        if (isOpen) {
            xMLStreamWriter.writeCharacters("\r\n");
            xMLStreamWriter.writeStartElement(fieldName);
            xMLStreamWriter.writeCharacters(value);
            xMLStreamWriter.writeEndElement();
        }
    }


}
