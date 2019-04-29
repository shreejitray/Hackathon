package com.example.mark8.DTO;

public class Product {
    private String name;
    private String price;
    private String imageUrl;
    private int count;
    private int id;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    @Override
    public int hashCode(){
        return id;
    }

    @Override
    public boolean equals(Object obj){
        return this.id == ((Product)obj).getId();
    }

    public int compareTo(Product o) {
        return this.id == o.getId()?1:0;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
