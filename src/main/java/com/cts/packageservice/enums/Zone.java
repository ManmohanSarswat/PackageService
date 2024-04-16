package com.cts.packageservice.enums;

import java.util.Arrays;
import java.util.List;

public enum Zone {
    NORTH(Arrays.asList("Himachal Pradesh", "Punjab", "Uttarakhand", "Uttar Pradesh", "Haryana", "Madhya Pradesh", "Chhattisgarh")),
    EAST(Arrays.asList("Bihar", "Orissa", "Jharkhand", "West Bengal", "Assam", "Sikkim", "Nagaland", "Meghalaya", "Manipur", "Mizoram", "Tripura", "Arunachal Pradesh")),
    WEST(Arrays.asList("Rajasthan", "Gujarat", "Goa", "Maharashtra")),
    SOUTH(Arrays.asList("Andhra Pradesh", "Karnataka", "Kerala", "Tamil Nadu"));

    private List<String> states;

    Zone(List<String> states) {
        this.states = states;
    }

    public static Zone getZone(String state) {
        for (Zone zone : Zone.values()) {
            if (zone.states.contains(state)) {
                return zone;
            }
        }
        return null;
    }
}