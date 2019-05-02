package com.example.mark8.DTO;

import java.util.List;

public class SearchRequestDTO {

    private byte[][][]payload;

    public byte[][][] getPayload() {
        return payload;
    }

    public void setPayload(byte[][][] payload) {
        this.payload = payload;
    }
}
