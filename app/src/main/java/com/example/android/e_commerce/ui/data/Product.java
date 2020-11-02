package com.example.android.e_commerce.ui.data;

public class Product {
    private String name, price,image,title,size;
    private double priceCard;

   public Product(){}

    public Product(String name, String price, String image, String title, String size, double priceCard) {
        this.name = name;
        this.price = price;
        this.image = image;
        this.title = title;
        this.size = size;
        this.priceCard = priceCard;
    }

    public String getName() {
        return name;
    }

    public String getPrice() {
        return price;
    }

    public String getImage() {
        return image;
    }

    public String getTitle() {
        return title;
    }

    public String getSize() {
        return size;
    }

    public double getPriceCard() {
        return priceCard;
    }
}
