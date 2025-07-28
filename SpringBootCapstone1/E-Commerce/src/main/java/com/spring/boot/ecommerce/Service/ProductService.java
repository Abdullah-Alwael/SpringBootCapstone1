package com.spring.boot.ecommerce.Service;

import com.spring.boot.ecommerce.Model.Category;
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
            for (int j = i + 1; j < bestSellers.size() - 1; j++) {
                if (bestSellers.get(j).getTimesPurchased() > bestSellers.get(i).getTimesPurchased()) {

                    swap = bestSellers.get(i);
                    bestSellers.set(i, bestSellers.get(j));
                    bestSellers.set(j, swap);

                }
            }
        }

        return bestSellers;
    }

    // update the score by admin
    public boolean updateProductScore(String userID, String productID, double newScore) {
        User user = userService.getUser(userID);

        if (user == null) {
            return false; // user does not exist
        }

        if (!user.getRole().equals("Admin")) {
            return false; // only admin can add scores for advertisement purposes
        }

        for (Product p : products) {
            if (p.getId().equals(productID)) {

                p.setScore(newScore);
                return true; // score updated

            }
        }

        return false; // product was not found
    }

    // helper sort method:
    public void sortByScore(ArrayList<Product> products) {
        Product swap;

        // sort based on score
        for (int i = 0; i < products.size(); i++) {
            for (int j = i + 1; j < products.size() - 1; j++) {
                if (products.get(j).getScore() > products.get(i).getScore()) {

                    swap = products.get(i);
                    products.set(i, products.get(j));
                    products.set(j, swap);

                }
            }
        }
    }

    // display advertisements based on highest score set by admin
    public ArrayList<Product> displayAdvertisement() {
        ArrayList<Product> bestScore = new ArrayList<>(products);

        // sort all products based on advertisement score
        sortByScore(bestScore);

        return bestScore;
    }

    // Display Similar products suggestions based on category and sort by score
    public ArrayList<Product> displaySimilarProducts(String productID) {
        ArrayList<Product> similarProducts = new ArrayList<>();

        Product similarTo = getProduct(productID); // the product we are comparing to, can be null

        for (Product p : products) { // add all products with the same category
            if (p.getCategoryID().equals(similarTo.getCategoryID())) {
                similarProducts.add(p);
            }
        }

        // sort similar products by score:
        sortByScore(similarProducts);

        return similarProducts;
    }

    // display user order history list
    public ArrayList<Product> displayUserOrderHistory(String userID) {

        ArrayList<Product> orderHistoryList = new ArrayList<>(); // [] empty list

        String orderHistory = userService.getUser(userID).getOrderHistory();

        // i.e. 100_2025-07-28, 200_2025-05-22 , . . .
        String[] orderIDsWithDates = orderHistory.split(","); // [0] = 100_2025-07-28

        for (String order : orderIDsWithDates) { // 100_2025-07-28
            String productID = order.split("_")[0]; // 100 which is the productID

            if (checkAvailableProduct(productID)) { // to prevent null pointer exception
                orderHistoryList.add(getProduct(productID)); // getProduct can return null
            }
        }

        return orderHistoryList; // or else return an empty list if user/product do not exist
    }

    // figure out user's favorite category
    public String favoriteUserCategory(String userID) {
        String favoriteCategory = ""; // initially nothing

        ArrayList<Product> userOrderHistory = displayUserOrderHistory(userID); // get user orders
        ArrayList<String> userCategories = new ArrayList<>(); // generate user categories

        for (Product p : userOrderHistory) {
            if (categoryService.checkAvailableCategory(p.getCategoryID())) { // if category exists, add it
                userCategories.add(categoryService.getCategory(p.getCategoryID()).getName());
            }
        }

        // check the most repeated category
        int currentCount;
        int maxCount = 0;

        for (String currentCategory : userCategories) {
            currentCount = 0;
            for (String counterCategory : userCategories) { // count how many times this category occurs
                if (currentCategory.equals(counterCategory)) {
                    currentCount++;
                }
            }

            if (currentCount > maxCount) {
                maxCount = currentCount;
                favoriteCategory = currentCategory;
            }
        }

        return favoriteCategory;

    }
}
