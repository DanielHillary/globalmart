package com.verraki.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class Product {
    private final int id;
    private final String name;
    private int averageDailyDemand;
    private int safetyStock;
}