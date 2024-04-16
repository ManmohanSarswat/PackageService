package com.cts.packageservice.service;

import com.cts.packageservice.enums.Zone;
import com.cts.packageservice.model.ItemAddress;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class PackageService {

    @Autowired
    private WebClient webClient;

    public Map<Integer, List<ItemAddress>> sortPackage(List<ItemAddress> itemAddressList) {
        log.info("Sorting packages based on the shipping address state");
        Map<Zone, List<ItemAddress>> sortedPackages = new HashMap<>();
        Map<Integer, List<ItemAddress>> finalPackages = new HashMap<>();
        try {
            for (ItemAddress pkg : itemAddressList) {
                Zone zone = Zone.getZone(pkg.getAddress().getState());
                if (zone != null) {
                    sortedPackages.computeIfAbsent(zone, k -> new ArrayList<>()).add(pkg);
                }
            }
            sortedPackages.forEach((k, v) -> finalPackages.put(getAssignedPackageId(k), v));
        } catch (Exception e) {
            log.error("An error occurred while sorting the packages: ", e);
        }
        log.info(finalPackages.toString());
        for (Map.Entry<Integer, List<ItemAddress>> entry : finalPackages.entrySet()) {
            sendPackagesToCourier(entry.getValue()).subscribe(response -> log.info("Package ID: " + entry.getKey() + " - " + response),
                    error -> log.error("An error occurred while sending the packages to the courier service: ", error));
        }
        return finalPackages;
    }

    public Mono<String> sendPackagesToCourier(List<ItemAddress> packages) {
        return webClient.post()
                .uri("http://localhost:8081/packages/courier") // replace with your actual API endpoint
                .bodyValue(packages)
                .retrieve()
                .bodyToMono(String.class)
                .doOnSuccess(response -> log.info("Packages sent to courier service successfully: " + packages))
                .doOnError(e -> log.error("An error occurred while sending the packages to the courier service: ", e));
    }

    private Integer getAssignedPackageId(Zone zone) {
        String zoneName = zone.name();
        switch (zoneName) {
            case "NORTH":
                return 1;
            case "EAST":
                return 2;
            case "WEST":
                return 3;
            case "SOUTH":
                return 4;
            default:
                return null;
        }
    }
}