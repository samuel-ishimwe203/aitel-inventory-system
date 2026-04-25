package com.airtel.inventory.web;

import com.airtel.inventory.logic.AssetService;
import com.airtel.inventory.logic.AssignmentService;
import com.airtel.inventory.store.AuditLogRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DashboardController {

    private final AssetService assetService;
    private final AssignmentService assignmentService;
    private final com.airtel.inventory.logic.EmployeeService employeeService;
    private final AuditLogRepository auditLogRepository;

    public DashboardController(AssetService assetService, AssignmentService assignmentService, 
                               com.airtel.inventory.logic.EmployeeService employeeService, AuditLogRepository auditLogRepository) {
        this.assetService = assetService;
        this.assignmentService = assignmentService;
        this.employeeService = employeeService;
        this.auditLogRepository = auditLogRepository;
    }

    @GetMapping("/")
    public String landing() {
        return "landing";
    }

    @GetMapping("/dashboard")
    public String index(Model model) {
        long totalAssets = assetService.getAllAssets().size();
        long totalEmployees = employeeService.getAllEmployees().size();
        long activeAssignments = assignmentService.getAllAssignments().stream()
                .filter(a -> "Active".equals(a.getStatus())).count();
        
        model.addAttribute("totalAssets", totalAssets);
        model.addAttribute("totalEmployees", totalEmployees);
        model.addAttribute("activeAssignments", activeAssignments);
        model.addAttribute("recentLogs", auditLogRepository.findTop50ByOrderByTimestampDesc());
        return "dashboard";
    }
}
