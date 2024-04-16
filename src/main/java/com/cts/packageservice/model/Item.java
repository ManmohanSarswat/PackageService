package com.cts.packageservice.model;

import lombok.Data;

@Data
public class Item {
    private int id;
    private String name;
    private int quantity;
    private double price;
    private double total;
}
