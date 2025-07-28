package com.spring.boot.ecommerce.Service;

import com.spring.boot.ecommerce.Model.Product;
import com.spring.boot.ecommerce.Model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class ProductService {

    private ArrayList<Product> products = new ArrayList<>();

    private final CategoryService categoryService;
    private final UserService userService;

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
        if (!categoryService.checkAvailableCategory(product.getCategoryID())) {
            return false; // return false if the new category id does not exist
        }
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

    public Product getProduct(String productID) {
        for (Product p : products) {
            if (p.getId().equals(productID)) {
                return p;
            }
        }

        return null; // does not exist
    }

    // TODO Extra methods:
    public ArrayList<Product> listBestSellingProducts() {
        ArrayList<Product> bestSellers = new ArrayList<>(products);

        Product swap;

        // sort based on max time of purchase
        for (int i = 0; i < bestSellers.size(); i++) {
            for (int j = i+1; j < bestSellers.size()-1; j++) {
                if (bestSellers.get(j).getTimesPurchased()
                        > bestSellers.get(i).getTimesPurchased()){
                    swap = bestSellers.get(i);
                    bestSellers.set(i,bestSellers.get(j));
                    bestSellers.set(j,swap);
                }
            }
        }

        return bestSellers;
    }

    // update the score by admin, and display advertisements

    public boolean updateProductScore(String userID, String productID, double newScore){
        User user = userService.getUser(userID);

        if (user == null){
            return false; // user does not exist
        }

        if (!user.getRole().equals("Admin")){
            return false; // only admin can add scores for advertisement purposes
        }

        for(Product p : products){
            if (p.getId().equals(productID)){
                p.setScore(newScore);
                return true; // score updated
            }
        }

        return false; // product was not found
    }

}
