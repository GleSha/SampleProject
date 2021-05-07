import model.parsers.record.RecordParser;

public class Main {

    public static void main(String[] args) {
        RecordParser recordParser = new RecordParser();
        recordParser.setSearchFilter(b -> b.getAmount() < -1.0);
        recordParser.getParsedRecords("schema.xml").forEach(System.out::println);
    }

}
