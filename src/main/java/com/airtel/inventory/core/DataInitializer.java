package com.airtel.inventory.core;

import com.airtel.inventory.domain.Asset;
import com.airtel.inventory.domain.AuditLog;
import com.airtel.inventory.domain.Employee;
import com.airtel.inventory.domain.User;
import com.airtel.inventory.store.AssetRepository;
import com.airtel.inventory.store.AuditLogRepository;
import com.airtel.inventory.store.EmployeeRepository;
import com.airtel.inventory.store.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    private final AssetRepository assetRepository;
    private final EmployeeRepository employeeRepository;
    private final UserRepository userRepository;
    private final AuditLogRepository auditLogRepository;
    private final PasswordEncoder passwordEncoder;

    public DataInitializer(AssetRepository assetRepository, 
                           EmployeeRepository employeeRepository, 
                           UserRepository userRepository,
                           AuditLogRepository auditLogRepository,
                           PasswordEncoder passwordEncoder) {
        this.assetRepository = assetRepository;
        this.employeeRepository = employeeRepository;
        this.userRepository = userRepository;
        this.auditLogRepository = auditLogRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {
        if (employeeRepository.count() == 0) {
            employeeRepository.save(new Employee("EMP001", "John Doe", "IT Support", "john.doe@airtel.com"));
            employeeRepository.save(new Employee("EMP002", "Jane Smith", "Human Resources", "jane.smith@airtel.com"));
            employeeRepository.save(new Employee("EMP003", "Alice Brown", "Finance", "alice.brown@airtel.com"));
        }

        if (assetRepository.count() == 0) {
            assetRepository.save(new Asset("SN-DL-001", "Dell Latitude 5420", "Laptop", "New", "Available"));
            assetRepository.save(new Asset("SN-HP-001", "HP EliteBook 840", "Laptop", "Good", "Available"));
            assetRepository.save(new Asset("SN-AP-001", "iPhone 13", "Mobile", "New", "Available"));
            assetRepository.save(new Asset("SN-DT-001", "OptiPlex 7090", "Desktop", "Fair", "Available"));
        }

        if (userRepository.count() == 0) {
            // Seed SysAdmin User: Username: 24RP00869, Password: 22RP03001
            userRepository.save(new User("24RP00869", passwordEncoder.encode("22RP03001"), "ADMIN"));
            // Add a normal user for testing
            userRepository.save(new User("user", passwordEncoder.encode("password"), "USER"));
        }

        if (auditLogRepository.count() == 0) {
            auditLogRepository.save(new AuditLog("ASSET_REGISTERED", "Registered new asset: Dell Latitude 5420 (SN-DL-001)", "System Admin"));
            auditLogRepository.save(new AuditLog("ASSET_ASSIGNED", "Assigned HP EliteBook 840 to John Doe", "System Admin"));
            auditLogRepository.save(new AuditLog("EMPLOYEE_ADDED", "Added new employee: Alice Brown (Finance)", "System Admin"));
        }
    }
}
