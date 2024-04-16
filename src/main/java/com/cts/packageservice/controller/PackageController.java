package com.cts.packageservice.controller;

import com.cts.packageservice.enums.Zone;
import com.cts.packageservice.model.ItemAddress;
import com.cts.packageservice.service.PackageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/packages")
public class PackageController {

    @Autowired
    private PackageService packageService;

    @PostMapping("/sort")
    public Map<Integer, List<ItemAddress>> sortPackage(@RequestBody List<ItemAddress> itemAddressList) {
        return packageService.sortPackage(itemAddressList);
    }
}