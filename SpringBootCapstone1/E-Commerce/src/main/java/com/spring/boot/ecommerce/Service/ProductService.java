package com.spring.boot.ecommerce.Service;

import com.spring.boot.ecommerce.Model.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class ProductService {

    private ArrayList<Product> products = new ArrayList<>();
    private final CategoryService categoryService;

    public boolean addProduct(Product product) {
        if (categoryService.checkAvailableCategory(product.getCategoryID())) {
            products.add(product);
            return true;
        }
        return false;
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

    public boolean checkAvailableProduct(String productID) {
        for (Product p : products) {
            if (p.getId().equals(productID)) {
                return true;
            }
        }

        return false;
    }


}
