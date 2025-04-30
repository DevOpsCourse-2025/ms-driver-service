package com.chiops.driver.services;


import com.chiops.driver.libs.dtos.DriverDTO;

import jakarta.validation.Valid;

import java.util.List;

public interface DriverService {

    List<DriverDTO> getAllDrivers();

    DriverDTO getDriverByCurp(String curp);

    DriverDTO createDriver(@Valid DriverDTO driverDTO);

    DriverDTO updateDriver(DriverDTO driverDTO);

    DriverDTO  deleteDriver(String curp);
}
