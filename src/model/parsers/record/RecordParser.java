package mediators.record;

import model.Record;
import parsing.Parser;

import java.util.ArrayList;
import java.util.List;

public class RecordParser {

    private List<Record> parsedRecords = new ArrayList<>();
    private Record.Builder builder = Record.builder();
    private Double neededAmount;
    private long recordsCount = 0;

    public void setNeededAmount(Double amount) {
        neededAmount = amount;
    }

    public Double getNeededAmount() {
        return neededAmount;
    }

    public void recordsOpen() {
        parsedRecords.clear();
    }

    public void recordOpen() {
        builder = Record.builder();
    }

    public void idClose(String value) {
        builder.setId(Long.valueOf(value));
    }

    public void nameClose(String value) {
        builder.setName(value);
    }

    public void amountClose(String value) {
        builder.setAmount(Double.valueOf(value));
    }

    public void startClose(String value) {
        builder.setStart(Integer.valueOf(value));
    }

    public void endClose(String value) {
        builder.setEnd(Integer.valueOf(value));
    }

    private void durationClose(String value) {
        builder.setDuration(Integer.valueOf(value));
    }

    public void percentRateClose(String value) {
        builder.setPercentRate(Double.valueOf(value));
    }

    private void descriptionClose(String value) {
        builder.setDescription(value);
        parsedRecords.add(builder.build());
        /*recordsCount++;
        if (builder.getAmount() < neededAmount) {
            if (recordsCount > 20000L) {
                parsedRecords.add(builder.build());
                recordsCount = 0;
            }
        }*/
    }

    public List<Record> getParsedRecords(String fileName, Double neededAmount) {
        Parser parser = new Parser();
        this.neededAmount = neededAmount;
        parser.addOpeningTagFunction("records", this::recordsOpen);
        parser.addOpeningTagFunction("record", this::recordOpen);
        parser.addClosingTagFunction("id", this::idClose);
        parser.addClosingTagFunction("amount", this::amountClose);
        parser.addClosingTagFunction("name", this::nameClose);
        parser.addClosingTagFunction("percentRate", this::percentRateClose);
        parser.addClosingTagFunction("description", this::descriptionClose);
        parser.addClosingTagFunction("start", this::startClose);
        parser.addClosingTagFunction("end", this::endClose);
        parser.addClosingTagFunction("duration", this::durationClose);
        parser.parse(fileName);
        return parsedRecords;
    }
}
