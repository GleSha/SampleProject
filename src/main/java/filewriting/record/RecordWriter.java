package filewriting.record;

import filewriting.XMLWriter;
import model.record.Record;
import javax.xml.stream.XMLStreamException;
import java.io.FileNotFoundException;
import java.util.Collection;

public class RecordWriter {

    private final XMLWriter xmlWriter;

    public RecordWriter(XMLWriter xmlWriter) {
        this.xmlWriter = xmlWriter;
    }

    public void write(Record record) throws XMLStreamException, FileNotFoundException {
        if (!xmlWriter.isOpen()) {
            xmlWriter.open();
            xmlWriter.writeStartElement("records");
        }
        writeInternal(record);
    }

    public void write(Collection<Record> records) throws XMLStreamException, FileNotFoundException {
        if (!xmlWriter.isOpen()) {
            xmlWriter.open();
            xmlWriter.writeStartElement("records");
        }
        for (Record record : records) {
            writeInternal(record);
        }
    }

    public void end() throws XMLStreamException {
        if (xmlWriter.isOpen()) {
            xmlWriter.close();
        }
    }

    private void writeInternal(Record record) throws XMLStreamException {
        xmlWriter.writeStartElement("record");
        xmlWriter.writeStartElement("variables");
        xmlWriter.writeField("id", String.valueOf(record.getVariables().getId()));
        xmlWriter.writeField("amount", String.valueOf(record.getVariables().getAmount()));
        xmlWriter.writeField("name", String.valueOf(record.getVariables().getName()));
        xmlWriter.writeStartElement("months");
        xmlWriter.writeField("start", String.valueOf(record.getVariables().getMonths().getStart()));
        xmlWriter.writeField("end", String.valueOf(record.getVariables().getMonths().getEnd()));
        xmlWriter.writeField("duration", String.valueOf(record.getVariables().getMonths().getDuration()));
        xmlWriter.writeEndElement();
        xmlWriter.writeField("percentRate", String.valueOf(record.getVariables().getPercentRate()));
        xmlWriter.writeEndElement();
        xmlWriter.writeField("description", record.getDescription());
        xmlWriter.writeEndElement();
    }
}
