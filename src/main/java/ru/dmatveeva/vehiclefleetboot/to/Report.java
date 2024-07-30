package ru.dmatveeva.vehiclefleetboot.to;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Map;

@Getter
@Setter
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
}
