package com.airtel.inventory.store;

import com.airtel.inventory.domain.Assignment;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface AssignmentRepository extends JpaRepository<Assignment, Long> {
    List<Assignment> findByStatus(String status);
    List<Assignment> findByEmployeeId(Long employeeId);
    List<Assignment> findByAssetIdAndStatus(Long assetId, String status);
}
