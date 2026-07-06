package com.chargepoint.model;

/**
 * Product data model for extracted product information.
 */
public class Product {
    private String name;
    private String originalPrice;
    private String currentPrice;
    private String discount;

    public Product() {
    }

    public Product(String name, String originalPrice, String currentPrice, String discount) {
        this.name = name;
        this.originalPrice = originalPrice;
        this.currentPrice = currentPrice;
        this.discount = discount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOriginalPrice() {
        return originalPrice;
    }

    public void setOriginalPrice(String originalPrice) {
        this.originalPrice = originalPrice;
    }

    public String getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(String currentPrice) {
        this.currentPrice = currentPrice;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    @Override
    public String toString() {
        return "Product{" +
                "name='" + name + '\'' +
                ", originalPrice='" + originalPrice + '\'' +
                ", currentPrice='" + currentPrice + '\'' +
                ", discount='" + discount + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Product product = (Product) o;

        if (name != null ? !name.equals(product.name) : product.name != null) return false;
        if (originalPrice != null ? !originalPrice.equals(product.originalPrice) : product.originalPrice != null)
            return false;
        if (currentPrice != null ? !currentPrice.equals(product.currentPrice) : product.currentPrice != null)
            return false;
        return discount != null ? discount.equals(product.discount) : product.discount == null;
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (originalPrice != null ? originalPrice.hashCode() : 0);
        result = 31 * result + (currentPrice != null ? currentPrice.hashCode() : 0);
        result = 31 * result + (discount != null ? discount.hashCode() : 0);
        return result;
    }
}