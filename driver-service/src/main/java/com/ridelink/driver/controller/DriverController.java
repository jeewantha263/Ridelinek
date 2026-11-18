package com.ridelink.driver.controller;

import com.ridelink.driver.model.Driver;
import com.ridelink.driver.service.DriverService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/drivers")
@RequiredArgsConstructor
@Tag(name = "Driver Management", description = "Endpoints for managing drivers, status, and locations")
public class DriverController {

    private final DriverService driverService;

    @PostMapping
    @Operation(summary = "Register a new driver profile")
    public ResponseEntity<Driver> createDriver(@RequestBody Driver driver) {
        Driver createdDriver = driverService.createDriver(driver);
        return new ResponseEntity<>(createdDriver, HttpStatus.CREATED);
    }

    @GetMapping
    @Operation(summary = "Get all drivers")
    public ResponseEntity<List<Driver>> getAllDrivers() {
        return ResponseEntity.ok(driverService.getAllDrivers());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get driver by ID")
    public ResponseEntity<Driver> getDriverById(@PathVariable String id) {
        return driverService.getDriverById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/account/{accountId}")
    @Operation(summary = "Get driver by Account ID")
    public ResponseEntity<Driver> getDriverByAccountId(@PathVariable String accountId) {
        return driverService.getDriverByAccountId(accountId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update full driver profile")
    public ResponseEntity<Driver> updateDriver(@PathVariable String id, @RequestBody Driver driver) {
        try {
            return ResponseEntity.ok(driverService.updateDriver(id, driver));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping("/{id}/location")
    @Operation(summary = "Update driver status and location")
    public ResponseEntity<Driver> updateLocation(
            @PathVariable String id,
            @RequestBody LocationUpdateRequest request) {
        try {
            Driver updated = driverService.updateStatusAndLocation(
                    id,
                    request.getStatus(),
                    request.getLatitude(),
                    request.getLongitude()
            );
            return ResponseEntity.ok(updated);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete driver by ID")
    public ResponseEntity<Void> deleteDriver(@PathVariable String id) {
        try {
            driverService.deleteDriver(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // DTO for location & status update payload
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class LocationUpdateRequest {
        private String status;
        private Double latitude;
        private Double longitude;
    }
}
