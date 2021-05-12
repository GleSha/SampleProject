import filewriting.XMLWriter;
import filewriting.record.RecordWriter;
import parsers.record.RecordCollector;
import parsing.xmlparser.XMLParser;
import parsers.record.RecordReadEventHandler;
import javax.xml.stream.XMLStreamException;
import java.io.FileNotFoundException;
import org.openjdk.jol.vm.VM;

public class Main {
    public static void main(String[] args) {
        try {
            RecordWriter recordWriter = new RecordWriter(new XMLWriter("outTry.xml"));
            RecordCollector recordCollector = new RecordCollector(r -> r.getVariables().getAmount() > 0.0, recordWriter);
            XMLParser recordParser = new XMLParser(() -> new RecordReadEventHandler(recordCollector.recordConsumer,
                    recordCollector.onRecordsClose));
            recordParser.read("src\\main\\resources\\try.xml");
        } catch (XMLStreamException | FileNotFoundException e) {
            System.out.println(e);
        }
    }


    public static void gettingAddressesOfTheObjects() {
        String s1 = "one";
        String s2 = new String("one");
        System.out.println(VM.current().addressOf(s1));
        System.out.println(VM.current().addressOf(s2));
        s2 = s2.intern();
        System.out.println(VM.current().addressOf(s2));
    }
}
