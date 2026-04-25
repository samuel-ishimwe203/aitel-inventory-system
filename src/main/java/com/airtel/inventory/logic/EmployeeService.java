package com.airtel.inventory.logic;

import com.airtel.inventory.domain.AuditLog;
import com.airtel.inventory.domain.Employee;
import com.airtel.inventory.store.AuditLogRepository;
import com.airtel.inventory.store.EmployeeRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final AuditLogRepository auditLogRepository;

    public EmployeeService(EmployeeRepository employeeRepository, AuditLogRepository auditLogRepository) {
        this.employeeRepository = employeeRepository;
        this.auditLogRepository = auditLogRepository;
    }

    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    @Transactional
    public Employee saveEmployee(Employee employee) {
        Employee saved = employeeRepository.save(employee);
        String currentUser = SecurityContextHolder.getContext().getAuthentication().getName();
        auditLogRepository.save(new AuditLog("EMPLOYEE_SAVED", 
            "Saved employee: " + employee.getFullName() + " (" + employee.getStaffId() + ")", currentUser));
        return saved;
    }

    public Optional<Employee> getEmployeeById(Long id) {
        return employeeRepository.findById(id);
    }

    @Transactional
    public void deleteEmployee(Long id) {
        employeeRepository.findById(id).ifPresent(emp -> {
            String currentUser = SecurityContextHolder.getContext().getAuthentication().getName();
            auditLogRepository.save(new AuditLog("EMPLOYEE_DELETED", 
                "Deleted employee: " + emp.getFullName() + " (" + emp.getStaffId() + ")", currentUser));
        });
        employeeRepository.deleteById(id);
    }
}
