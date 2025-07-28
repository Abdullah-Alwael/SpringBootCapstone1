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
    private final UserService userService;
    private final MerchantService merchantService;
    private final MerchantStockService merchantStockService;

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

    public boolean buyProduct(String userID, String productID, String merchantID) {
        if (!userService.checkAvailableUser(userID)
                || !checkAvailableProduct(productID)
                || !merchantService.checkAvailableMerchant(merchantID)) {
            return false; // one or all IDs do not exist
        }

        Product p = getProduct(productID);

        if (userService.canPay(userID, p.getPrice())
                && merchantStockService.StockAvailable(productID, merchantID, 1)) {

            // TODO Extra step:
            p.setTimesPurchased(p.getTimesPurchased() + 1); // count how many times a product was purchased

            return merchantStockService.removeStockFromProduct(productID, merchantID, 1)
                    && userService.pay(userID, p.getPrice());
        } else {
            return false;
        }
    }

    // TODO Extra method:
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

}
