package com.example.mark8.DTO;

import java.util.ArrayList;
import java.util.List;

public class MainContext {
    private List<Product> searchList;
    private List<Product> savedList;
    private List<Product> cartList;

    public MainContext(){
        searchList = new ArrayList<>();
        savedList = new ArrayList<>();
        cartList = new ArrayList<>();
    }

    public void setSearchList(List searchList){
        if(searchList != null){
            this.searchList = searchList;
        }
    }

    public void addtoSavedList(Product product){
        if(!getSavedList().contains(product)){
            getSavedList().add(product);
        }
    }

    public void addToCart(Product product){
        if(getCartList().contains(product)){
            Product producte = getCartList().get(getCartList().indexOf(product));
            producte.setCount(producte.getCount()+1);
        }else{
            product.setCount(1);
            getCartList().add(product);
        }
    }

    public void setSavedList(List<Product> savedList){
        this.savedList = savedList;
    }

    public List<Product> getSavedList() {
        return savedList;
    }

    public List<Product> getSearchList() {
        return searchList;
    }

    public List<Product> getCartList() {
        return cartList;
    }
}
