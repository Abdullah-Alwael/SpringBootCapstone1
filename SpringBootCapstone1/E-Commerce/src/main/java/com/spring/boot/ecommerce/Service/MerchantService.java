package com.spring.boot.ecommerce.Service;

import com.spring.boot.ecommerce.Model.Merchant;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class MerchantService {

    private ArrayList<Merchant> merchants = new ArrayList<>();

    public void addMerchant(Merchant merchant) {
        merchants.add(merchant);
    }

    public ArrayList<Merchant> getMerchants() {
        return merchants;
    }

    public boolean updateMerchant(String merchantID, Merchant merchant) {
        for (Merchant m : merchants) {
            if (m.getId().equals(merchantID)) {
                merchants.set(merchants.indexOf(m), merchant);
                return true;
            }
        }

        return false;
    }

    public boolean deleteMerchant(String merchantID) {
        for (Merchant m : merchants) {
            if (m.getId().equals(merchantID)) {
                merchants.remove(m);
                return true;
            }
        }

        return false;
    }
    
}
