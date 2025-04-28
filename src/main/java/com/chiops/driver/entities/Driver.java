package com.chiops.driver.entities;


import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "drivers")
public class Driver {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private FullName fullName;

    @Column(nullable = false, unique = true,length = 18)
    private String curp;

    @ManyToOne(optional = false, cascade = CascadeType.ALL)
    private Address address;

    @Column(nullable = false)
    private BigDecimal monthlySalary;

    @OneToOne(optional = false, cascade = CascadeType.ALL)
    private License license;

    @Column(nullable = false)
    private LocalDateTime registrationDate;

    public Driver() {
    }

    public Driver(FullName fullName, String curp, Address address, BigDecimal monthlySalary, License license) {
        this.fullName = fullName;
        this.curp = curp;
        this.address = address;
        this.monthlySalary = monthlySalary;
        this.license = license;
        this.registrationDate = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(LocalDateTime registrationDate) {
        this.registrationDate = registrationDate;
    }

    public License getLicense() {
        return license;
    }

    public void setLicense(License license) {
        this.license = license;
    }

    public BigDecimal getMonthlySalary() {
        return monthlySalary;
    }

    public void setMonthlySalary(BigDecimal monthlySalary) {
        this.monthlySalary = monthlySalary;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public String getCurp() {
        return curp;
    }

    public void setCurp(String curp) {
        this.curp = curp;
    }

    public FullName getFullName() {
        return fullName;
    }

    public void setFullName(FullName fullName) {
        this.fullName = fullName;
    }
}
