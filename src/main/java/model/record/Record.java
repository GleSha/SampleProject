package model.record;

public class Record {

    private VariablesData variables;
    private String description;

    public VariablesData getVariables() {
        return variables;
    }

    public void setVariables(VariablesData variables) {
        this.variables = variables;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "model.record.Record{" +
                "variables=" + variables +
                ", description='" + description + '\'' +
                '}';
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private static VariablesData variablesData = new VariablesData();
        private static MonthsData monthsData = new MonthsData();
        private static String desc;

        private Builder() {
        }

        public void clear() {
            desc = null;
            variablesData.setAmount(null);
            variablesData.setId(null);
            variablesData.setName(null);
            variablesData.setPercentRate(null);
            monthsData.setEnd(null);
            monthsData.setStart(null);
            monthsData.setDuration(null);
        }

        public Builder id(Long id) {
            variablesData.setId(id);
            return this;
        }

        public Builder name(String name) {
            variablesData.setName(name);
            return this;
        }

        public Builder amount(Double amount) {
            variablesData.setAmount(amount);
            return this;
        }

        public Builder percentRate(Double percentRate) {
            variablesData.setPercentRate(percentRate);
            return this;
        }

        public Builder start(Integer start) {
            monthsData.setStart(start);
            return this;
        }

        public Builder end(Integer end) {
            monthsData.setEnd(end);
            return this;
        }

        public Builder duration(Integer duration) {
            monthsData.setDuration(duration);
            return this;
        }

        public Builder description(String description) {
            desc = description;
            return this;
        }

        public Record build() {
            Record record = new Record();
            record.setDescription(desc);
            variablesData.setMonths(monthsData);
            record.setVariables(variablesData);
            monthsData = new MonthsData();
            variablesData = new VariablesData();
            desc = null;
            return record;
        }

        public Double getAmount() {
            return variablesData.getAmount();
        }
    }

}
