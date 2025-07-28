package com.spring.boot.ecommerce.Service;

import com.spring.boot.ecommerce.Model.MerchantStock;
import com.spring.boot.ecommerce.Model.Product;
import com.spring.boot.ecommerce.Model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class MerchantStockService {

    private ArrayList<MerchantStock> merchantStocks = new ArrayList<>();

    private final ProductService productService;
    private final MerchantService merchantService;
    private final UserService userService;

    public boolean addMerchantStock(MerchantStock merchantStock) {
        if (productService.checkAvailableProduct(merchantStock.getProductID())
                && merchantService.checkAvailableMerchant(merchantStock.getMerchantID())) {

            merchantStocks.add(merchantStock);
            return true; // check if both product and merchant exist.
        }

        return false; // one or both of them do not exist
    }

    public ArrayList<MerchantStock> getMerchantStocks() {
        return merchantStocks;
    }

    public boolean updateMerchantStock(String merchantStockID, MerchantStock merchantStock) {
        if (!productService.checkAvailableProduct(merchantStock.getProductID())
                || !merchantService.checkAvailableMerchant(merchantStock.getMerchantID())) {
            // if any of the ID's do not exist, then return false.
            return false;
        }
        for (MerchantStock m : merchantStocks) {
            if (m.getId().equals(merchantStockID)) {
                merchantStocks.set(merchantStocks.indexOf(m), merchantStock);
                return true;
            }
        }

        return false;
    }

    public boolean deleteMerchantStock(String merchantStockID) {
        for (MerchantStock m : merchantStocks) {
            if (m.getId().equals(merchantStockID)) {
                merchantStocks.remove(m);
                return true;
            }
        }

        return false;
    }

    public boolean checkAvailableMerchantStock(String merchantStockID) {
        for (MerchantStock m : merchantStocks) {
            if (m.getId().equals(merchantStockID)) {
                return true;
            }
        }

        return false;
    }

    public boolean addMoreStockToProduct(String productID, String merchantID, int additionalStockAmount) {
        for (MerchantStock m : merchantStocks) {
            if (m.getProductID().equals(productID)
                    && m.getMerchantID().equals(merchantID)) {

                m.setStock(m.getStock() + additionalStockAmount);
                return true; //updated
            }
        }
        return false; // does not exist
    }

    public boolean StockAvailable(String productID, String merchantID, int stockAmountToCheck) {
        for (MerchantStock m : merchantStocks) {
            if (m.getProductID().equals(productID)
                    && m.getMerchantID().equals(merchantID)) {

                return m.getStock() >= stockAmountToCheck;
            }
        }
        return false; // the merchantStock does not exist
    }

    public boolean removeStockFromProduct(String productID, String merchantID, int stockAmountToRemove) {
        for (MerchantStock m : merchantStocks) {
            if (m.getProductID().equals(productID)
                    && m.getMerchantID().equals(merchantID)) {

                if (m.getStock() >= stockAmountToRemove) {
                    m.setStock(m.getStock() - stockAmountToRemove);
                    return true; //deducted from stock
                } else {
                    return false; // bad request, unavailable stock
                }
            }
        }
        return false; // the merchantStock does not exist
    }


    public boolean buyProduct(String userID, String productID, String merchantID) {
        if (!userService.checkAvailableUser(userID)
                || !productService.checkAvailableProduct(productID)
                || !merchantService.checkAvailableMerchant(merchantID)) {
            return false; // one or all IDs do not exist
        }

        Product p = productService.getProduct(productID);
        User u = userService.getUser(userID);

        if (userService.canPay(userID, p.getPrice())
                && StockAvailable(productID, merchantID, 1)) {

            // TODO Extra steps:
            // count how many times a product was purchased
            p.setTimesPurchased(p.getTimesPurchased() + 1);
            // store user purchase history in the format ("productID_Date,") i.e. ("100_2025-07-28,")
            u.setOrderHistory(u.getOrderHistory().concat(productID+"_"+ LocalDate.now()+","));

            return removeStockFromProduct(productID, merchantID, 1)
                    && userService.pay(userID, p.getPrice());
        } else {
            return false;
        }
    }

}
