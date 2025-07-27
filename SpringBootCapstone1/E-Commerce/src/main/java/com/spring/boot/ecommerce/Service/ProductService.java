package com.spring.boot.ecommerce.Service;

import com.spring.boot.ecommerce.Model.Product;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class ProductService {

    private ArrayList<Product> products = new ArrayList<>();

    public void addProduct(Product product) {
        products.add(product);
    }

    public ArrayList<Product> getProducts() {
        return products;
    }

    public boolean updateProduct(String productID, Product product) {
        for (Product p : products) {
            if (p.getId().equals(productID)) {
                products.set(products.indexOf(p), product);
                return true;
            }
        }

        return false;
    }

    public boolean deleteProduct(String productID) {
        for (Product p : products) {
            if (p.getId().equals(productID)) {
                products.remove(p);
                return true;
            }
        }

        return false;
    }

}
