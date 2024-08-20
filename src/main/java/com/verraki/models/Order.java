package com.verraki.models;

public class Order implements Comparable<Order> {
    private final int orderId;
    private final int priority;
    private final double value;
    private final boolean expedited;
    private final Vendor vendor;

    public Order(int orderId, int priority, double value, boolean expedited, Vendor vendor) {
        this.orderId = orderId;
        this.priority = priority;
        this.value = value;
        this.expedited = expedited;
        this.vendor = vendor;
    }

    public int getOrderId() {
        return orderId;
    }

    public int getPriority() {
        return priority;
    }

    public double getValue() {
        return value;
    }

    public boolean isExpedited() {
        return expedited;
    }

    public Vendor getVendor() {
        return vendor;
    }

    @Override
    public int compareTo(Order other) {
        if (this.priority != other.priority) {
            return Integer.compare(other.priority, this.priority);
        }
        return Double.compare(other.value, this.value);
    }
}