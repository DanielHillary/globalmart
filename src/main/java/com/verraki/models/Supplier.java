package com.verraki.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class Supplier {
    private int id;
    private final String name;
    private final int leadTime;
    private final boolean available;
}