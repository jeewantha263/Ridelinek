package com.ridelink.driver.service;

import com.ridelink.driver.model.Driver;
import com.ridelink.driver.repository.DriverRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DriverService {

    private final DriverRepository driverRepository;

    // Register a new driver profile
    public Driver createDriver(Driver driver) {
        return driverRepository.save(driver);
    }

    // Get all drivers
    public List<Driver> getAllDrivers() {
        return driverRepository.findAll();
    }

    // Get driver by ID
    public Optional<Driver> getDriverById(String id) {
        return driverRepository.findById(id);
    }

    // Get driver by accountId
    public Optional<Driver> getDriverByAccountId(String accountId) {
        return driverRepository.findByAccountId(accountId);
    }

    // Update full profile
    public Driver updateDriver(String id, Driver driverDetails) {
        return driverRepository.findById(id)
                .map(existingDriver -> {
                    existingDriver.setAccountId(driverDetails.getAccountId());
                    existingDriver.setVehicleType(driverDetails.getVehicleType());
                    existingDriver.setLicensePlate(driverDetails.getLicensePlate());
                    existingDriver.setStatus(driverDetails.getStatus());
                    existingDriver.setLatitude(driverDetails.getLatitude());
                    existingDriver.setLongitude(driverDetails.getLongitude());
                    return driverRepository.save(existingDriver);
                })
                .orElseThrow(() -> new RuntimeException("Driver not found with id: " + id));
    }

    // Update status and location
    public Driver updateStatusAndLocation(String id, String status, Double latitude, Double longitude) {
        return driverRepository.findById(id)
                .map(driver -> {
                    if (status != null) {
                        driver.setStatus(status);
                    }
                    if (latitude != null) {
                        driver.setLatitude(latitude);
                    }
                    if (longitude != null) {
                        driver.setLongitude(longitude);
                    }
                    return driverRepository.save(driver);
                })
                .orElseThrow(() -> new RuntimeException("Driver not found with id: " + id));
    }

    // Delete driver by ID
    public void deleteDriver(String id) {
        if (!driverRepository.existsById(id)) {
            throw new RuntimeException("Driver not found with id: " + id);
        }
        driverRepository.deleteById(id);
    }
}
