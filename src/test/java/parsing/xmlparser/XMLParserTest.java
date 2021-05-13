package parsing.xmlparser;

import filewriting.XMLWriter;
import filewriting.record.RecordWriter;
import org.junit.Assert;
import org.junit.Test;
import parsers.record.RecordCollector;
import parsers.record.RecordReadEventHandlerInitializer;
import javax.xml.stream.XMLStreamException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class XMLParserTest {

    private static final String pathPrefix = "src\\test\\resources\\parsing\\record\\";
    private static final String emptyFile = "emptyFile.xml";
    private static final String nonexistentFile = "nonexistentFile";
    private static final String maxBufferCountRecords = "maxBufferCountRecords.xml";
    private static final String oneMoreThanMaxBufferCountRecords = "oneMoreThanMaxBufferCountRecords.xml";
    private static final String oneRecord = "oneRecord.xml";
    private static final String oneRecordOtherFieldsOrder = "oneRecordOtherFieldsOrder.xml";
    private static final String oneRecordOtherFieldsOrderResult = "oneRecordOtherFieldsOrderResult.xml";
    private static final String recordsForFiltering = "recordsForFiltering.xml";
    private static final String filteringResult = "filteringResult.xml";
    private static final String incompleteFile = "incompleteFile.xml";

    @Test(expected = XMLStreamException.class)
    public void read_emptyFile_getError() throws XMLStreamException, FileNotFoundException {
        readIgnoreOutput(emptyFile);
    }

    @Test(expected = FileNotFoundException.class)
    public void read_nonexistentFile_getError() throws XMLStreamException, FileNotFoundException {
        readIgnoreOutput(nonexistentFile);
    }

    @Test(expected = XMLStreamException.class)
    public void read_incompleteFile_getError() throws XMLStreamException, FileNotFoundException {
        readIgnoreOutput(incompleteFile);
    }

    @Test
    public void read_oneRecord() throws XMLStreamException, IOException {
        readWithoutFiltering(oneRecord);
    }

    @Test
    public void read_maxBufferRecords() throws XMLStreamException, IOException {
        readWithoutFiltering(maxBufferCountRecords);
    }

    @Test
    public void read_oneMoreThanMaxBufferRecords() throws XMLStreamException, IOException {
        readWithoutFiltering(oneMoreThanMaxBufferCountRecords);
    }

    @Test
    public void read_oneRecordOtherFieldsOrder() throws XMLStreamException, IOException {
        RecordWriter recordWriter = new RecordWriter(new XMLWriter(pathPrefix + "out" + oneRecordOtherFieldsOrder));
        RecordCollector recordCollector = new RecordCollector(r -> true, recordWriter);
        XMLParser recordParser = new XMLParser(() -> new RecordReadEventHandlerInitializer(recordCollector.recordConsumer,
                recordCollector.onRecordsClose));
        recordParser.read(pathPrefix + oneRecordOtherFieldsOrder);
        Assert.assertEquals(Files.readString(Paths.get(pathPrefix + oneRecordOtherFieldsOrderResult)),
                Files.readString(Paths.get(pathPrefix + "out" + oneRecordOtherFieldsOrder)));
    }

    @Test
    public void read_recordsFiltering() throws XMLStreamException, IOException {
        RecordWriter recordWriter = new RecordWriter(new XMLWriter(pathPrefix + "out" + recordsForFiltering));
        RecordCollector recordCollector = new RecordCollector(r -> r.getVariables().getAmount() < 1.0, recordWriter);
        XMLParser recordParser = new XMLParser(() -> new RecordReadEventHandlerInitializer(recordCollector.recordConsumer,
                recordCollector.onRecordsClose));
        recordParser.read(pathPrefix + recordsForFiltering);
        Assert.assertEquals(Files.readString(Paths.get(pathPrefix + filteringResult)),
                Files.readString(Paths.get(pathPrefix + "out" + recordsForFiltering)));
    }

    private void readIgnoreOutput(String fileName) throws XMLStreamException, FileNotFoundException {
        RecordWriter recordWriter = new RecordWriter(new XMLWriter(pathPrefix + "out" + fileName));
        RecordCollector recordCollector = new RecordCollector(r -> r.getVariables().getAmount() > 1.0, recordWriter);
        XMLParser recordParser = new XMLParser(() -> new RecordReadEventHandlerInitializer(recordCollector.recordConsumer,
                recordCollector.onRecordsClose));
        recordParser.read(pathPrefix + fileName);
    }

    private void readWithoutFiltering(String fileName) throws XMLStreamException, IOException {
        RecordWriter recordWriter = new RecordWriter(new XMLWriter(pathPrefix + "out" + fileName));
        RecordCollector recordCollector = new RecordCollector(r -> true, recordWriter);
        XMLParser recordParser = new XMLParser(() -> new RecordReadEventHandlerInitializer(recordCollector.recordConsumer,
                recordCollector.onRecordsClose));
        recordParser.read(pathPrefix + fileName);
        Assert.assertEquals(Files.readString(Paths.get(pathPrefix + fileName)),
                Files.readString(Paths.get(pathPrefix + "out" + fileName)));
    }
}