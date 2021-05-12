package parsing.xmlparser;

import parsing.xmlparser.utils.ThrowingConsumer;
import parsing.xmlparser.utils.VoidFunction;

import javax.xml.stream.XMLStreamException;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;

public class ReadEventContainer {

    private final Map<String, VoidFunction> openingTagsFunctions = new HashMap<>();
    private final Map<String, ThrowingConsumer<String>> closingTagsFunctions = new HashMap<>();


    public void onStartElement(String element) throws XMLStreamException, FileNotFoundException {
        VoidFunction voidFunction = openingTagsFunctions.get(element);
        if (voidFunction != null) {
            voidFunction.apply();
        }
    }

    public void onEndElement(String element, String stringBuffer) throws XMLStreamException, FileNotFoundException {
        ThrowingConsumer<String> stringConsumer = closingTagsFunctions.get(element);
        if (stringConsumer != null) {
            stringConsumer.accept(stringBuffer);
        }
    }

    public void addOpeningTagFunction(String tag, VoidFunction function) {
        openingTagsFunctions.put(tag, function);
    }

    public void addClosingTagFunction(String tag, ThrowingConsumer<String> function) {
        closingTagsFunctions.put(tag, function);
    }

    public void clearTagFunctions() {
        closingTagsFunctions.clear();
        openingTagsFunctions.clear();
    }
}
