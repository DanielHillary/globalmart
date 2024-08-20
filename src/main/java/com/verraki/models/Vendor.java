package com.verraki.models;

import java.util.concurrent.atomic.AtomicInteger;

public class Vendor {
    private final int vendorId;
    private final AtomicInteger activeOrders = new AtomicInteger(0);

    public Vendor(int vendorId) {
        this.vendorId = vendorId;
    }

    public int getVendorId() {
        return vendorId;
    }

    public void incrementOrders() {
        activeOrders.incrementAndGet();
    }

    public void decrementOrders() {
        activeOrders.decrementAndGet();
    }
}