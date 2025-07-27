package com.spring.boot.ecommerce.Service;

import com.spring.boot.ecommerce.Model.MerchantStock;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class MerchantStockService {

    private ArrayList<MerchantStock> merchantStocks = new ArrayList<>();

    public void addMerchantStock(MerchantStock merchantStock) {
        merchantStocks.add(merchantStock);
    }

    public ArrayList<MerchantStock> getMerchantStocks() {
        return merchantStocks;
    }

    public boolean updateMerchantStock(String merchantStockID, MerchantStock merchantStock) {
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

}
