package com.ridelink.driver.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "drivers")
public class Driver {

    @Id
    private String id;

    private String accountId;

    private String vehicleType;

    private String licensePlate;

    private String status; // e.g., "AVAILABLE", "ON_TRIP", "OFFLINE"

    private Double latitude;

    private Double longitude;
}
