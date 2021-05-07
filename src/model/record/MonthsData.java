package model;

public class MonthsData {
    private Integer start;
    private Integer end;
    private Integer duration;

    public Integer getStart() {
        return start;
    }

    public void setStart(Integer start) {
        this.start = start;
    }

    public Integer getEnd() {
        return end;
    }

    public void setEnd(Integer end) {
        this.end = end;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    @Override
    public String toString() {
        return "model.MonthsData{" +
                "start=" + start +
                ", end=" + end +
                ", duration=" + duration +
                '}';
    }
}
