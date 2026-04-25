package com.airtel.inventory.web;

import com.airtel.inventory.domain.Asset;
import com.airtel.inventory.logic.AssetService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/assets")
public class AssetWebController {

    private final AssetService assetService;

    public AssetWebController(AssetService assetService) {
        this.assetService = assetService;
    }

    @GetMapping
    public String listAssets(Model model) {
        model.addAttribute("assets", assetService.getAllAssets());
        return "assets/list";
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("asset", new Asset());
        return "assets/form";
    }

    @PostMapping("/save")
    public String saveAsset(@ModelAttribute Asset asset) {
        if (asset.getId() != null) {
            assetService.updateAsset(asset);
        } else {
            assetService.registerAsset(asset);
        }
        return "redirect:/assets";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        model.addAttribute("asset", assetService.getAssetById(id)
                .orElseThrow(() -> new RuntimeException("Asset not found")));
        return "assets/form";
    }

    @PostMapping("/delete/{id}")
    public String deleteAsset(@PathVariable Long id) {
        assetService.deleteAsset(id);
        return "redirect:/assets";
    }
}
