package com.example.mark8.DTO;

import java.util.List;

public class SearchResultDTO {
    private String itemName;
    private List<Product> products;

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }
}
