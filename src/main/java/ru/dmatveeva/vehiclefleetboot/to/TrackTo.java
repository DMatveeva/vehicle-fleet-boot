package ru.dmatveeva.vehiclefleetboot.to;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.ZonedDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TrackTo {

    @JsonProperty
    private Integer id;

    @JsonProperty
    private Integer vehicleId;

    @JsonProperty
    @JsonFormat(pattern="yyyy-MM-dd HH:mm")
    private ZonedDateTime started;

    @JsonProperty
    @JsonFormat(pattern="yyyy-MM-dd HH:mm")
    private ZonedDateTime finished;
}
