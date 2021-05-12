package parsing.xmlparser.utils;

import javax.xml.stream.XMLStreamException;
import java.io.FileNotFoundException;

public interface VoidFunction {
    void apply() throws XMLStreamException, FileNotFoundException;
}
