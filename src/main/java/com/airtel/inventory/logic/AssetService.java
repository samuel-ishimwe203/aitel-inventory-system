package com.airtel.inventory.logic;

import com.airtel.inventory.domain.Asset;
import com.airtel.inventory.store.AssetRepository;
import com.airtel.inventory.domain.AuditLog;
import com.airtel.inventory.store.AuditLogRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class AssetService {

    private final AssetRepository assetRepository;
    private final AuditLogRepository auditLogRepository;

    public AssetService(AssetRepository assetRepository, AuditLogRepository auditLogRepository) {
        this.assetRepository = assetRepository;
        this.auditLogRepository = auditLogRepository;
    }

    public List<Asset> getAllAssets() {
        return assetRepository.findAll();
    }

    public Optional<Asset> getAssetById(Long id) {
        return assetRepository.findById(id);
    }

    @Transactional
    public Asset registerAsset(Asset asset) {
        Asset savedAsset = assetRepository.save(asset);
        String currentUser = SecurityContextHolder.getContext().getAuthentication().getName();
        auditLogRepository.save(new AuditLog("ASSET_REGISTERED", 
            "Registered new asset: " + asset.getName() + " (" + asset.getSerialNumber() + ")", currentUser));
        return savedAsset;
    }

    @Transactional
    public Asset updateAsset(Asset asset) {
        return assetRepository.save(asset);
    }

    public void deleteAsset(Long id) {
        assetRepository.deleteById(id);
    }
}
