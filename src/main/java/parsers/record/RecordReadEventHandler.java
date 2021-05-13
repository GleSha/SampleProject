package parsers.record;

import model.record.Record;
import parsing.xmlparser.ReadEventContainer;
import parsing.xmlparser.ReadEventHandler;
import parsing.xmlparser.utils.ThrowingConsumer;
import parsing.xmlparser.utils.VoidFunction;
import javax.xml.stream.XMLStreamException;
import java.io.FileNotFoundException;

public class RecordReadEventHandler implements ReadEventHandler {

    private Record.Builder builder = Record.builder();
    private final ThrowingConsumer<Record> recordConsumer;
    private final VoidFunction onRecordsClose;

    public RecordReadEventHandler(ThrowingConsumer<Record> recordConsumer, VoidFunction onRecordsClose) {
        this.recordConsumer = recordConsumer;
        this.onRecordsClose = onRecordsClose;
        builder = Record.builder();
    }

    private void onRecordOpen() {
        if (builder == null) {
            builder = Record.builder();
        }
    }

    private void onIdClose(String value) {
        builder.id(Long.valueOf(value));
    }

    private void onNameClose(String value) {
        builder.name(value);
    }

    private void onAmountClose(String value) {
        builder.amount(Double.valueOf(value));
    }

    private void onStartClose(String value) {
        builder.start(Integer.valueOf(value));
    }

    private void onEndClose(String value) {
        builder.end(Integer.valueOf(value));
    }

    private void onDurationClose(String value) {
        builder.duration(Integer.valueOf(value));
    }

    private void onPercentRateClose(String value) {
        builder.percentRate(Double.valueOf(value));
    }

    private void onDescriptionClose(String value) {
        builder.description(value);
    }

    private void onRecordClose(String value) throws XMLStreamException, FileNotFoundException {
        recordConsumer.accept(builder.build());
    }

    private void onRecordsClose(String value) throws XMLStreamException, FileNotFoundException {
        onRecordsClose.apply();
    }

    @Override
    public void setHandlersForEvents(ReadEventContainer eventContainer) {
        eventContainer.addOpeningTagFunction("record", this::onRecordOpen);
        eventContainer.addClosingTagFunction("record", this::onRecordClose);
        eventContainer.addClosingTagFunction("id", this::onIdClose);
        eventContainer.addClosingTagFunction("amount", this::onAmountClose);
        eventContainer.addClosingTagFunction("name", this::onNameClose);
        eventContainer.addClosingTagFunction("percentRate", this::onPercentRateClose);
        eventContainer.addClosingTagFunction("description", this::onDescriptionClose);
        eventContainer.addClosingTagFunction("start", this::onStartClose);
        eventContainer.addClosingTagFunction("end", this::onEndClose);
        eventContainer.addClosingTagFunction("duration", this::onDurationClose);
        eventContainer.addClosingTagFunction("records", this::onRecordsClose);
    }

}
