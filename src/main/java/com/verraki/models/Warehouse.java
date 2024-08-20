package com.verraki.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
@Data
@AllArgsConstructor
public class Warehouse {
    private final int id;
    private final String region;
    public final Map<Product, Integer> stockLevels = new ConcurrentHashMap<>();
    public final Map<Integer, Integer> products = new ConcurrentHashMap<>();

    public void addProduct(int productId, int stockLevel) {
        products.put(productId, stockLevel);
    }

    public void setStockLevel(int productId, int stockLevel) {
        products.put(productId, stockLevel);
    }

    public String getRegion() {
        return region;
    }

    public Map<Integer, Integer> getProducts() {
        return products;
    }

}
