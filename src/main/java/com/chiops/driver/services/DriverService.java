package com.chiops.driver.services;


import com.chiops.driver.libs.dtos.DriverDTO;

import java.util.List;

public interface DriverService {

    List<DriverDTO> getAllDrivers();

    DriverDTO getDriverByCurp(String curp);

    DriverDTO createDriver(DriverDTO driverDTO);

    DriverDTO updateDriver(DriverDTO driverDTO);
}
