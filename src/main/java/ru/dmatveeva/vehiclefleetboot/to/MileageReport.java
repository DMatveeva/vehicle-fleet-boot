package ru.dmatveeva.vehiclefleetboot.to;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Map;

@Getter
@Setter
public class MileageReport extends Report {
    @JsonProperty
    public int vehicleId;

    public MileageReport(@JsonProperty("periodToValue") final Map<String, Integer> periodToValue,
                         @JsonProperty("type") final String type,
                         @JsonProperty("period") final String period,
                         @JsonProperty("start") final LocalDate start,
                         @JsonProperty("end") final LocalDate end,
                         @JsonProperty("vehicleId") final int vehicleId) {
        super(periodToValue, type, period, start, end);
        this.vehicleId = vehicleId;
    }
}
