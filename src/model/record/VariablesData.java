package model.record;

public class VariablesData {
    private Long id;
    private String name;
    private Double amount;
    private Double percentRate;
    private MonthsData months;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Double getPercentRate() {
        return percentRate;
    }

    public void setPercentRate(Double percentRate) {
        this.percentRate = percentRate;
    }

    public MonthsData getMonths() {
        return months;
    }

    public void setMonths(MonthsData months) {
        this.months = months;
    }

    @Override
    public String toString() {
        return "model.record.VariablesData{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", amount=" + amount +
                ", percentRate=" + percentRate +
                ", months=" + months +
                '}';
    }
}
