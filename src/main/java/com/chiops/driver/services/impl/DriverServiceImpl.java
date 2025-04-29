package com.chiops.driver.services.impl;

import com.chiops.driver.entities.Address;
import com.chiops.driver.entities.Driver;
import com.chiops.driver.entities.FullName;
import com.chiops.driver.entities.License;
import com.chiops.driver.libs.dtos.DriverDTO;
import com.chiops.driver.libs.exceptions.exception.*;
import com.chiops.driver.repositories.DriverRepository;
import com.chiops.driver.services.DriverService;
import io.micronaut.transaction.annotation.Transactional;
import jakarta.inject.Singleton;
import jakarta.validation.Valid;

import java.util.List;

@Singleton
public class DriverServiceImpl implements DriverService {

    private final DriverRepository driverRepository;

    public DriverServiceImpl(DriverRepository driverRepository) {
        this.driverRepository = driverRepository;
    }

    @Override
    public List<DriverDTO> getAllDrivers() {
        return driverRepository.findAll()
                .stream()
                .map(this::toDriverDTO)
                .toList();
    }

    @Override
    public DriverDTO getDriverByCurp(String curp) {
        Driver driver = driverRepository.findByCurp(curp)
        .orElseThrow(() -> new NotFoundException("Driver not found with CURP: " + curp));
        return toDriverDTO(driver);
    }

    @Override
    @Transactional
    public DriverDTO createDriver(DriverDTO dto) {
        FullName fullName = new FullName(dto.getFirstName(), dto.getLastName());

        Address address = new Address(dto.getStreet(), dto.getCity(), dto.getState());
        License license = new License(dto.getLicenseNumber());

        if (driverRepository.findByCurp(dto.getCurp()).isPresent()) {
            throw new ConflictException("Driver with CURP " + dto.getCurp() + " already exists");
        }
        Driver driver = new Driver(
                fullName,
                dto.getCurp(),
                address,
                dto.getMonthlySalary(),
                license
        );

        return toDriverDTO(driverRepository.save(driver));
    }

    @Override
    public DriverDTO updateDriver(DriverDTO driverDTO) {
        Driver existingDriver = driverRepository.findByCurp(driverDTO.getCurp())
        .orElseThrow(() -> new NotFoundException("Driver not found with CURP: " + driverDTO.getCurp()));

        if (!existingDriver.getCurp().equals(driverDTO.getCurp())) {
            throw new BadRequestException("CURP cannot be changed");
        }


        existingDriver.getFullName().setFirstName(driverDTO.getFirstName());
        existingDriver.getFullName().setLastName(driverDTO.getLastName());
        existingDriver.setCurp(driverDTO.getCurp());
        existingDriver.getAddress().setStreet(driverDTO.getStreet());
        existingDriver.getAddress().setCity(driverDTO.getCity());
        existingDriver.getAddress().setState(driverDTO.getState());
        existingDriver.getLicense().setLicenseNumber(driverDTO.getLicenseNumber());
        existingDriver.setRegistrationDate(driverDTO.getRegistrationDate());

        driverRepository.update(existingDriver);

        return toDriverDTO(existingDriver);
    }

    @Override
    public void deleteDriver(String curp) {
        Driver driver = driverRepository.findByCurp(curp)
        .orElseThrow(() -> new NotFoundException("Driver not found with CURP: " + curp));
        driverRepository.delete(driver);
    }

    private DriverDTO toDriverDTO(Driver driver) {
        DriverDTO dto = new DriverDTO();
        dto.setFirstName(driver.getFullName().getFirstName());
        dto.setLastName(driver.getFullName().getLastName());
        dto.setCurp(driver.getCurp());
        dto.setStreet(driver.getAddress().getStreet());
        dto.setCity(driver.getAddress().getCity());
        dto.setState(driver.getAddress().getState());
        dto.setLicenseNumber(driver.getLicense().getLicenseNumber());
        dto.setRegistrationDate(driver.getRegistrationDate());
        return dto;
    }

    private Driver toDriverEntity(DriverDTO driverDTO) {
        Driver driver = new Driver();
        FullName fullName = new FullName();

        fullName.setFirstName(driverDTO.getFirstName());
        fullName.setLastName(driverDTO.getLastName());
        driver.setFullName(fullName);
        driver.setCurp(driver.getCurp());

        Address address = new Address();
        address.setStreet(driverDTO.getStreet());
        address.setCity(driverDTO.getCity());
        address.setState(driverDTO.getState());

        driver.setAddress(address);
        License license = new License();
        license.setLicenseNumber(driverDTO.getLicenseNumber());

        driver.setLicense(license);
        driver.setRegistrationDate(driver.getRegistrationDate());
        return driver;
    }

}
