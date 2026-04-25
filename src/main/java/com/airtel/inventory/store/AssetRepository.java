package com.airtel.inventory.store;

import com.airtel.inventory.domain.Asset;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface AssetRepository extends JpaRepository<Asset, Long> {
    Optional<Asset> findBySerialNumber(String serialNumber);
    List<Asset> findByType(String type);
    List<Asset> findByAvailabilityStatus(String status);
}
