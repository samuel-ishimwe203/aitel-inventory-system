package com.airtel.inventory.web;

import com.airtel.inventory.domain.Asset;
import com.airtel.inventory.domain.AuditLog;
import com.airtel.inventory.logic.AssetService;
import com.airtel.inventory.store.AuditLogRepository;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Controller
@RequestMapping("/reports")
public class ReportWebController {

    private final AssetService assetService;
    private final AuditLogRepository auditLogRepository;

    public ReportWebController(AssetService assetService, AuditLogRepository auditLogRepository) {
        this.assetService = assetService;
        this.auditLogRepository = auditLogRepository;
    }

    @GetMapping
    public String index(Model model) {
        model.addAttribute("assets", assetService.getAllAssets());
        model.addAttribute("logs", auditLogRepository.findAll());
        return "reports/index";
    }

    @GetMapping("/export/assets")
    public void exportAssets(HttpServletResponse response) throws IOException {
        response.setContentType("text/csv");
        response.setHeader("Content-Disposition", "attachment; filename=ims_assets.csv");

        List<Asset> assets = assetService.getAllAssets();
        StringBuilder sb = new StringBuilder();
        sb.append("Asset Name,Serial Number,Type,Condition,Status\n");

        for (Asset asset : assets) {
            sb.append(String.format("\"%s\",\"%s\",\"%s\",\"%s\",\"%s\"\n",
                asset.getName(), asset.getSerialNumber(), asset.getType(), asset.getConditionStatus(), asset.getAvailabilityStatus()));
        }

        response.getWriter().write(sb.toString());
    }

    @GetMapping("/export/logs")
    public void exportLogs(HttpServletResponse response) throws IOException {
        response.setContentType("text/csv");
        response.setHeader("Content-Disposition", "attachment; filename=ims_audit_logs.csv");

        List<AuditLog> logs = auditLogRepository.findAll();
        StringBuilder sb = new StringBuilder();
        sb.append("Time,Action,Details,Admin\n");

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        for (AuditLog log : logs) {
            sb.append(String.format("\"%s\",\"%s\",\"%s\",\"%s\"\n",
                log.getTimestamp().format(formatter), log.getAction(), log.getDetails(), log.getPerformedBy()));
        }

        response.getWriter().write(sb.toString());
    }
}
