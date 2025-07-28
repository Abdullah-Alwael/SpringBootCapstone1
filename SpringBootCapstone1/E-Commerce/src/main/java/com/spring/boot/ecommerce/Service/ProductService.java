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

    public double getProductPrice(String productID) {
        for (Product p : products) {
            if (p.getId().equals(productID)) {
                return p.getPrice();
            }
        }

        return 0; // does not exist
    }

    public boolean buyProduct(String userID, String productID, String merchantID) {
        if (!userService.checkAvailableUser(userID)
                || !checkAvailableProduct(productID)
                || !merchantService.checkAvailableMerchant(merchantID)) {
            return false; // one or all IDs do not exist
        }

        if (userService.canPay(userID, getProductPrice(productID))
                && merchantStockService.StockAvailable(productID, merchantID, 1)) {

            return merchantStockService.removeStockFromProduct(productID, merchantID, 1)
                    && userService.pay(userID, getProductPrice(productID));
        } else {
            return false;
        }
    }

}
