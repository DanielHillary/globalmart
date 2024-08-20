package com.verraki;

import com.verraki.models.*;
import com.verraki.services.*;

import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {
    public static void main(String[] args) {

        // Sample data for testing
        Warehouse warehouse1 = new Warehouse(1, "North America");
        warehouse1.addProduct(1, 100);
        warehouse1.addProduct(2, 200);

        Warehouse warehouse2 = new Warehouse(2, "Europe");
        warehouse2.addProduct(1, 50);
        warehouse2.addProduct(2, 150);

        List<Warehouse> warehouses = Arrays.asList(warehouse1, warehouse2);

        Product product1 = new Product(1, "Product 1", 10, 20);
        Product product2 = new Product(2, "Product 2", 15, 30);

        List<Product> products = Arrays.asList(product1, product2);

        Supplier supplier1 = new Supplier(1, "Supplier A", 5, true);
        Supplier supplier2 = new Supplier(2, "Supplier B", 7, false);

        List<Supplier> suppliers = Arrays.asList(supplier1, supplier2);

        StockManager stockMonitor = new StockManager(warehouses, products, suppliers);

        // Start the monitoring process
        try {
            stockMonitor.monitorAndReorder();
        } catch (Exception e) {
            // Log and handle any unexpected errors in the main process
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, "Unexpected error occurred", e);
        }
    }
}