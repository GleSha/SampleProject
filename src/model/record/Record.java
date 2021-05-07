package model;

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
        return "model.Record{" +
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

        public Builder setId(Long id) {
            variablesData.setId(id);
            return this;
        }

        public Builder setName(String name) {
            variablesData.setName(name);
            return this;
        }

        public Builder setAmount(Double amount) {
            variablesData.setAmount(amount);
            return this;
        }

        public Builder setPercentRate(Double percentRate) {
            variablesData.setPercentRate(percentRate);
            return this;
        }

        public Builder setStart(Integer start) {
            monthsData.setStart(start);
            return this;
        }

        public Builder setEnd(Integer end) {
            monthsData.setEnd(end);
            return this;
        }

        public Builder setDuration(Integer duration) {
            monthsData.setDuration(duration);
            return this;
        }

        public Builder setDescription(String description) {
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
