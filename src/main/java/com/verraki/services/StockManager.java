package com.verraki.services;

import com.verraki.models.*;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
public class StockManager {

    private static final Logger logger = Logger.getLogger(StockManager.class.getName());

    private List<Warehouse> warehouses;
    private List<Product> products;
    private List<Supplier> suppliers;

    public StockManager(List<Warehouse> warehouses, List<Product> products, List<Supplier> suppliers) {
        this.warehouses = warehouses;
        this.products = products;
        this.suppliers = suppliers;
    }

    private int calculateReorderPoint(Product product, Supplier supplier) {
        return (product.getAverageDailyDemand() * supplier.getLeadTime()) + product.getSafetyStock();
    }

    public Supplier selectSupplier() {
        return suppliers.stream()
                .filter(Supplier::isAvailable)
                .min(Comparator.comparingInt(Supplier::getLeadTime))
                .orElse(null);
    }

    // Monitor stock levels and reorder if necessary
    public void monitorAndReorder() {
        for (Warehouse warehouse : warehouses) {
            for (Map.Entry<Integer, Integer> entry : warehouse.getProducts().entrySet()) {
                int productId = entry.getKey();
                int stockLevel = entry.getValue();
                Product product = getProductById(productId);
                Supplier supplier = selectSupplier();

                if (supplier == null) {
                    logger.log(Level.WARNING, "No available supplier for product: {0}", product.getName());
                    notifyStockoutRisk(warehouse, product);
                    continue;
                }
                //Get inventory of the product from the warehouse
                Inventory inventory = new Inventory(70, 200);
                int reorderPoint = inventory.getReorderPoint();

                //If current stock-level is less than or equal to reorder point, trigger an order with supplier.
                if (stockLevel <= reorderPoint) {
                    placeOrder(supplier, product, inventory.getReorderQuantity());
                }
            }
        }
    }

    public void placeOrder(Supplier supplier, Product product, int quantity) {
        try {
            // Simulate placing an order with a supplier
            // Secure sensitive data handling
            logger.log(Level.INFO, "Ordering {0} of {1} from {2}", new Object[]{quantity, product.getName(), supplier.getName()});
            // Implement order placement logic here
        } catch (Exception e) {
            // Log and handle any errors during order placement
            logger.log(Level.SEVERE, "Error placing order for product: " + product.getName(), e);
        }
    }

    public Product getProductById(int productId) {
        return products.stream()
                .filter(product -> product.getId() == productId)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Product not found: " + productId));
    }

    void notifyStockoutRisk(Warehouse warehouse, Product product) {
        System.out.println("Warning: No suppliers available for product " + product.getName() + " at warehouse " + warehouse.getId());
        // Further actions could include sending notifications to procurement teams
    }
}
