package ru.dmatveeva.vehiclefleetboot.to;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class VehicleTo {

    @JsonProperty
    private Integer id;

    @JsonProperty
    private Integer modelId;

    @JsonProperty
    private String vin;

    @JsonProperty
    private BigDecimal costUsd;

    @JsonProperty
    private String color;

    @JsonProperty
    private int mileage;

    @JsonProperty
    private int productionYear;

    @JsonProperty
    private Integer enterpriseId;

    @JsonProperty
    @JsonFormat(pattern="dd-MM-yyyy HH:mm")
    private LocalDateTime purchaseDate;
}
