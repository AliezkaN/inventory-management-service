package com.nahorniak.inventorymanagementservice.utils;


import java.util.Collection;

public interface StatsCollector <R, T> {

    R collect(Collection<T> entities);
}
