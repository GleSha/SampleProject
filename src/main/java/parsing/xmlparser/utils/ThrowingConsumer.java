package parsing.xmlparser.utils;

import javax.xml.stream.XMLStreamException;
import java.io.FileNotFoundException;

public interface ThrowingConsumer<T> {

    void accept(T t) throws XMLStreamException, FileNotFoundException;

}
