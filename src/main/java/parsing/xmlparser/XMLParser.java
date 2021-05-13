package parsing.xmlparser;

import javax.xml.stream.XMLStreamException;
import java.io.FileNotFoundException;
import java.util.function.Supplier;

public class XMLParser {

    private final ReadEventContainer readEventContainer;

    public XMLParser(Supplier<ReadEventHandlersInitializer> readEventHandlerSupplier) {
        readEventContainer = new ReadEventContainer();
        readEventHandlerSupplier.get().setHandlersForEvents(readEventContainer);
    }

    public void read(String fileName) throws XMLStreamException, FileNotFoundException {
        EventEmittingReader eventEmittingReader = new EventEmittingReader(readEventContainer);
        eventEmittingReader.read(fileName);
    }
}
