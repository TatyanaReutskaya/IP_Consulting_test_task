package test_task;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Ticket {
    @JsonProperty("origin_name")
    private String cityFrom;
    @JsonProperty("destination_name")
    private String cityTo;
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonFormat(pattern = "dd.MM.yy HH:mm")
    @JsonProperty("departure_date")
    private LocalDateTime departureDateTime;
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonFormat(pattern = "dd.MM.yy HH:mm")
    @JsonProperty("arrival_date")
    private LocalDateTime arrivalDateTime;
    private String carrier;
    private int stop;
    private double price;

}
