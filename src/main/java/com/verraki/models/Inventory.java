package com.verraki.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class Inventory {
    private int id;
    private String region;
    private int productId;
    private int reorderQuantity;
    private int reorderPoint;
    private int stockLevel;

    public Inventory(int reorderPoint, int reorderQuantity) {
        this.reorderPoint = reorderPoint;
        this.reorderQuantity = reorderQuantity;
    }
}
