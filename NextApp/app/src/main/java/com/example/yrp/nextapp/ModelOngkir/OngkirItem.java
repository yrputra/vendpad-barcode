package com.example.yrp.nextapp.ModelOngkir;

/**
 * Created by YRP on 29/07/2015.
 */
public class OngkirItem {
    private String courier;
    private int originId, destinationId, weight;

    public OngkirItem() {
    }

    public void setOriginId(int originId) {
        this.originId = originId;
    }

    public void setDestinationId(int destinationId) {
        this.destinationId = destinationId;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public void setCourier(String courier) {
        this.courier = courier;
    }

    public int getWeight() {
        return weight;
    }

    public int getOriginId() {
        return originId;
    }

    public String getCourier() {
        return courier;
    }

    public int getDestinationId() {
        return destinationId;
    }
}
