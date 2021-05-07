package parsing;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class Parser {

    private final Map<String, VoidFunction> openingTagsFunctions = new HashMap<>();
    private final Map<String, Consumer<String>> closingTagsFunctions = new HashMap<>();

    public void parse(String path) {
        XMLInputFactory xmlInputFactory = XMLInputFactory.newInstance();
        try {
            XMLEventReader reader = xmlInputFactory.createXMLEventReader(new FileInputStream(path));
            if (reader.hasNext()) {
                XMLEvent nextEvent = reader.nextEvent();
                if (!nextEvent.isStartDocument()) {
                    throw new Exception("Error in startDocument event");
                }
            }
            String value = null;
            while (reader.hasNext()) {
                XMLEvent nextEvent = reader.nextEvent();
                if (nextEvent.isStartElement()) {
                    StartElement startElement = nextEvent.asStartElement();
                    VoidFunction voidFunction = openingTagsFunctions.get(startElement.getName().getLocalPart());
                    if (voidFunction != null) {
                        voidFunction.apply();
                    }
                } else if (nextEvent.isCharacters()) {
                    value = nextEvent.asCharacters().getData().trim();
                } else if (nextEvent.isEndElement()) {
                    EndElement endElement = nextEvent.asEndElement();
                    Consumer<String> stringConsumer = closingTagsFunctions.get(endElement.getName().getLocalPart());
                    if (stringConsumer != null) {
                        stringConsumer.accept(value);
                    }
                } else if (nextEvent.isEndDocument()) {
                    break;
                }
            }
            reader.close();
        } catch (XMLStreamException xse) {
            System.out.println("XMLStreamException");
            xse.printStackTrace();
        } catch (Exception fnfe) {
            System.out.println("FileNotFoundException");
            fnfe.printStackTrace();
        }
    }

    public void addOpeningTagFunction(String tag, VoidFunction function) {
        openingTagsFunctions.put(tag, function);
    }

    public void addClosingTagFunction(String tag, Consumer<String> function) {
        closingTagsFunctions.put(tag, function);
    }

    public void clearTagFunctions() {
        closingTagsFunctions.clear();
        openingTagsFunctions.clear();
    }

}
