package com.airtel.inventory.logic;

import com.airtel.inventory.domain.Asset;
import com.airtel.inventory.domain.Assignment;
import com.airtel.inventory.domain.Employee;
import com.airtel.inventory.store.AssetRepository;
import com.airtel.inventory.store.AssignmentRepository;
import com.airtel.inventory.store.EmployeeRepository;
import com.airtel.inventory.domain.AuditLog;
import com.airtel.inventory.store.AuditLogRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AssignmentService {

    private final AssignmentRepository assignmentRepository;
    private final AssetRepository assetRepository;
    private final EmployeeRepository employeeRepository;
    private final AuditLogRepository auditLogRepository;

    public AssignmentService(AssignmentRepository assignmentRepository, AssetRepository assetRepository, 
                             EmployeeRepository employeeRepository, AuditLogRepository auditLogRepository) {
        this.assignmentRepository = assignmentRepository;
        this.assetRepository = assetRepository;
        this.employeeRepository = employeeRepository;
        this.auditLogRepository = auditLogRepository;
    }

    public List<Assignment> getAllAssignments() {
        return assignmentRepository.findAll();
    }

    @Transactional
    public Assignment assignAsset(Long assetId, String staffId, String notes) {
        Asset asset = assetRepository.findById(assetId)
                .orElseThrow(() -> new RuntimeException("Asset not found"));
        
        Employee employee = employeeRepository.findByStaffId(staffId)
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        if (!"Available".equals(asset.getAvailabilityStatus())) {
            throw new RuntimeException("Asset is not available for assignment");
        }

        Assignment assignment = new Assignment();
        assignment.setAsset(asset);
        assignment.setEmployee(employee);
        assignment.setNotes(notes);
        
        asset.setAvailabilityStatus("Assigned");
        assetRepository.save(asset);

        Assignment savedAssignment = assignmentRepository.save(assignment);
        
        String currentUser = SecurityContextHolder.getContext().getAuthentication().getName();
        auditLogRepository.save(new AuditLog("ASSET_ASSIGNED", 
            "Assigned " + asset.getName() + " to " + employee.getFullName(), currentUser));
            
        return savedAssignment;
    }

    @Transactional
    public void returnAsset(Long assignmentId) {
        Assignment assignment = assignmentRepository.findById(assignmentId)
                .orElseThrow(() -> new RuntimeException("Assignment not found"));

        Asset asset = assignment.getAsset();
        asset.setAvailabilityStatus("Available");
        assetRepository.save(asset);

        assignment.setReturnDate(LocalDateTime.now());
        assignment.setStatus("Returned");
        assignmentRepository.save(assignment);

        String currentUser = SecurityContextHolder.getContext().getAuthentication().getName();
        auditLogRepository.save(new AuditLog("ASSET_RETURNED", 
            "Returned " + asset.getName() + " from " + assignment.getEmployee().getFullName(), currentUser));
    }
}
