package ru.dmatveeva.vehiclefleetboot.to;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;
import java.util.Map;

public class Report {

    @JsonProperty
    String type;

    //values : y, m, d
    @JsonProperty
    String period;

    @JsonProperty
    @JsonFormat(pattern="yyyy-MM-dd")
    private LocalDate start;

    @JsonProperty
    @JsonFormat(pattern="yyyy-MM-dd")
    private LocalDate end;

    public void setPeriodToValueKm(Map<String, Integer> periodToValue) {
        this.periodToValue = periodToValue;
    }

    @JsonProperty
    private Map<String, Integer> periodToValue;

    public Report() {
    }

    // The constructor works for deserialization and has nothing to do with serialization
    public Report(@JsonProperty("periodToValue") final Map<String, Integer> periodToValue,
                  @JsonProperty("type") final String type,
                  @JsonProperty("period") final String period,
                  @JsonProperty("start") final LocalDate start,
                  @JsonProperty("end") final LocalDate end) {
        this.periodToValue = periodToValue;
        this.type = type;
        this.period = period;
        this.start = start;
        this.end = end;
    }

    // Getters are typically automatically serialized
    public Map<String, Integer> getPeriodToValue() {
        return periodToValue;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public LocalDate getStart() {
        return start;
    }

    public void setStart(LocalDate start) {
        this.start = start;
    }

    public LocalDate getEnd() {
        return end;
    }

    public void setEnd(LocalDate end) {
        this.end = end;
    }
}
