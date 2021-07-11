package com.gordonramsay.userfollowsproduct.model;

import org.apache.kafka.common.protocol.types.Field;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.UUID;

@Entity
@Table(name = "products")
public class Product {
    @Id
    private UUID barcode;

    private String name;
    private Double mobileSalesPrice;
    private Double salesPrice;

    public UUID getBarcode() {
        return barcode;
    }

    public void setProductBarcode(UUID barcode) {
        this.barcode = barcode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getMobileSalesPrice() {
        return mobileSalesPrice;
    }

    public void setMobileSalesPrice(Double mobileSalesPrice) {
        this.mobileSalesPrice = mobileSalesPrice;
    }

    public Double getSalesPrice() {
        return salesPrice;
    }

    public void setSalesPrice(Double salesPrice) {
        this.salesPrice = salesPrice;
    }

    @Override
    public String toString() {
        return "Product{" +
                "productBarcode=" + barcode +
                ", name='" + name + '\'' +
                ", mobileSalesPrice=" + mobileSalesPrice +
                ", salesPrice=" + salesPrice +
                '}';
    }
}