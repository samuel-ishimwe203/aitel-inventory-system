package com.airtel.inventory.web;

import com.airtel.inventory.logic.AssetService;
import com.airtel.inventory.logic.AssignmentService;
import com.airtel.inventory.logic.EmployeeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/assignments")
public class AssignmentWebController {

    private final AssignmentService assignmentService;
    private final AssetService assetService;
    private final EmployeeService employeeService;

    public AssignmentWebController(AssignmentService assignmentService, AssetService assetService, 
                                   EmployeeService employeeService) {
        this.assignmentService = assignmentService;
        this.assetService = assetService;
        this.employeeService = employeeService;
    }

    @GetMapping
    public String listAssignments(Model model) {
        model.addAttribute("assignments", assignmentService.getAllAssignments());
        return "assignments/list";
    }

    @GetMapping("/new")
    public String showAssignForm(Model model) {
        model.addAttribute("assets", assetService.getAllAssets());
        model.addAttribute("employees", employeeService.getAllEmployees());
        return "assignments/form";
    }

    @PostMapping("/save")
    public String saveAssignment(@RequestParam Long assetId, @RequestParam String staffId, @RequestParam String notes) {
        assignmentService.assignAsset(assetId, staffId, notes);
        return "redirect:/assignments";
    }

    @PostMapping("/return/{id}")
    public String returnAsset(@PathVariable Long id) {
        assignmentService.returnAsset(id);
        return "redirect:/assignments";
    }
}
