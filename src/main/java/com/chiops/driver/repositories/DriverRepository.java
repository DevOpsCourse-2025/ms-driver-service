package com.chiops.driver.repositories;

import java.util.Optional;

import com.chiops.driver.entities.Driver;
import io.micronaut.data.annotation.Repository;
import io.micronaut.data.repository.CrudRepository;

@Repository
public interface DriverRepository extends CrudRepository<Driver, Long> {

    Optional<Driver> findByCurp(String curp);

}
