package parsers.record;

import filewriting.record.RecordWriter;
import model.record.Record;
import parsing.xmlparser.utils.ThrowingConsumer;
import parsing.xmlparser.utils.VoidFunction;

import javax.xml.stream.XMLStreamException;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class RecordCollector {

    public final int MAX_BUFFER_SIZE = 6;

    private final List<Record> recordsBuffer = new ArrayList<>();
    private Predicate<Record> recordPredicate;
    private RecordWriter recordWriter;

    public RecordCollector(Predicate<Record> recordPredicate, RecordWriter recordWriter) {
        this.recordPredicate = recordPredicate;
        this.recordWriter = recordWriter;
    }

    public RecordCollector(Predicate<Record> recordPredicate) {
        this.recordPredicate = recordPredicate;
    }

    public final ThrowingConsumer<Record> recordConsumer = record -> {
        if (recordPredicate.test(record)) {
            recordsBuffer.add(record);
            if (recordsBuffer.size() >= MAX_BUFFER_SIZE) {
                writeToFile();
            }
        }
    };

    public final VoidFunction onRecordsClose = () -> {
        writePersist();
        writeEnd();
    };

    private void writeToFile() throws XMLStreamException, FileNotFoundException {
        if (recordWriter != null) {
            if (recordsBuffer.size() >= MAX_BUFFER_SIZE) {
                recordWriter.write(recordsBuffer);
            }
        }
        recordsBuffer.clear();
    }

    private void writePersist() throws XMLStreamException, FileNotFoundException {
        if (recordWriter != null) {
            recordWriter.write(recordsBuffer);
        }
        recordsBuffer.clear();
    }

    public void writeEnd() throws XMLStreamException {
        if (recordWriter != null) {
            recordWriter.end();
        }
    }
}
