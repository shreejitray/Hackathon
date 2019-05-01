package com.example.mark8.DTO;

import java.util.List;

public class GenerateQRCodeDTO {
    private List<Product> payload;

    public List<Product> getPayload() {
        return payload;
    }

    public void setPayload(List<Product> payload) {
        this.payload = payload;
    }
}
