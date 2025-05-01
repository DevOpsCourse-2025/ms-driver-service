package com.chiops.driver.controllers;

import com.chiops.driver.libs.dtos.DriverDTO;
import com.chiops.driver.libs.exceptions.exception.BadRequestException;
import com.chiops.driver.libs.exceptions.exception.InternalServerException;

import com.chiops.driver.services.DriverService;

import io.micronaut.http.annotation.*;

import java.util.List;

@Controller("/driver")
public class DriverController {

    private final DriverService driverService;

    public DriverController(DriverService driverService) {
        this.driverService = driverService;
    }

    @Post("/create")
    public DriverDTO createDriver(@Body DriverDTO driverDTO) {
        try {
            return driverService.createDriver(driverDTO);
        } catch (BadRequestException e) {
            throw new BadRequestException("Bad request while trying to create the driver: " + e.getMessage());
        } catch (InternalServerException e) {
            throw new InternalServerException("Internal server error while trying to create the driver: " + e.getMessage());
        }
    }

    @Put("/update")
    public DriverDTO updateDriver(@Body DriverDTO driverDTO) {
        try {
            return driverService.updateDriver(driverDTO);
        } catch (BadRequestException e) {
            throw new BadRequestException("Bad request while trying to update the driver: " + e.getMessage());
        } catch (InternalServerException e) {
            throw new InternalServerException("Internal server error while trying to update the driver: " + e.getMessage());
        }
    }

    @Delete("/delete/{curp}")
    public void deleteDriver(@PathVariable String curp) {
        try {
            driverService.deleteDriver(curp);
        } catch (BadRequestException e) {
            throw new BadRequestException("Bad request while trying to delete the driver with CURP " + curp + ": " + e.getMessage());
        } catch (InternalServerException e) {
            throw new InternalServerException("Internal server error while trying to delete the driver with CURP " + curp + ": " + e.getMessage());
        }
    }

    @Get("/get/{curp}")
    public DriverDTO getDriverByCurp(@PathVariable String curp) {
        try {
            return driverService.getDriverByCurp(curp);
        } catch (BadRequestException e) {
            throw new BadRequestException("Bad request while trying to get the driver with CURP " + curp + ": " + e.getMessage());
        } catch (InternalServerException e) {
            throw new InternalServerException("Internal server error while trying to get the driver with CURP " + curp + ": " + e.getMessage());
        }
    }

    @Get("/getall")
    public List<DriverDTO> getAllDrivers() {
        try {
            return driverService.getAllDrivers();
        } catch (BadRequestException e) {
            throw new BadRequestException("Bad request while trying to get all drivers: " + e.getMessage());
        } catch (InternalServerException e) {
            throw new InternalServerException("Internal server error while trying to get all drivers: " + e.getMessage());
        }
    }
}
