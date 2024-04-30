package ru.dmatveeva.vehiclefleetboot.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
public class Report extends AbstractBaseEntity {

    public String type;

    String period;

    private LocalDate start;

    private LocalDate end;

    private Map<String, Integer> periodToValue;
}
