package com.airtel.inventory.domain;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "assets")
public class Asset {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String serialNumber;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String type; // Laptop, Desktop, Mobile

    @Column(columnDefinition = "TEXT")
    private String specifications;

    @Column(nullable = false)
    private String conditionStatus; // New, Good, Fair, Poor, Broken

    @Column(nullable = false)
    private String availabilityStatus; // Available, Assigned, Maintenance, Lost

    private LocalDateTime registeredAt;

    public Asset() {
        this.registeredAt = LocalDateTime.now();
    }

    public Asset(String serialNumber, String name, String type, String conditionStatus, String availabilityStatus) {
        this.serialNumber = serialNumber;
        this.name = name;
        this.type = type;
        this.conditionStatus = conditionStatus;
        this.availabilityStatus = availabilityStatus;
        this.registeredAt = LocalDateTime.now();
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getSerialNumber() { return serialNumber; }
    public void setSerialNumber(String serialNumber) { this.serialNumber = serialNumber; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public String getSpecifications() { return specifications; }
    public void setSpecifications(String specifications) { this.specifications = specifications; }

    public String getConditionStatus() { return conditionStatus; }
    public void setConditionStatus(String conditionStatus) { this.conditionStatus = conditionStatus; }

    public String getAvailabilityStatus() { return availabilityStatus; }
    public void setAvailabilityStatus(String availabilityStatus) { this.availabilityStatus = availabilityStatus; }

    public LocalDateTime getRegisteredAt() { return registeredAt; }
    public void setRegisteredAt(LocalDateTime registeredAt) { this.registeredAt = registeredAt; }
}
