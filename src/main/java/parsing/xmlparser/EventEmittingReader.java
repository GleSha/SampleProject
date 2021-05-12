package parsing.xmlparser;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class EventEmittingReader {

    private final ReadEventContainer eventContainer;

    public EventEmittingReader(ReadEventContainer eventContainer) {
        this.eventContainer = eventContainer;
    }

    public void read(String path) throws XMLStreamException, FileNotFoundException {
        XMLInputFactory xmlInputFactory = XMLInputFactory.newInstance();
        XMLEventReader reader = xmlInputFactory.createXMLEventReader(new FileInputStream(path));
        if (reader.hasNext()) {
            reader.nextEvent();
        }
        String stringBuffer = null;
        while (reader.hasNext()) {
            XMLEvent nextEvent = reader.nextEvent();
            if (nextEvent.isStartElement()) {
                StartElement startElement = nextEvent.asStartElement();
                eventContainer.onStartElement(startElement.getName().getLocalPart());
            } else if (nextEvent.isCharacters()) {
                stringBuffer = nextEvent.asCharacters().getData().trim();
            } else if (nextEvent.isEndElement()) {
                EndElement endElement = nextEvent.asEndElement();
                eventContainer.onEndElement(endElement.getName().getLocalPart(), stringBuffer);
            } else if (nextEvent.isEndDocument()) {
                break;
            }
        }
        reader.close();
    }
}
