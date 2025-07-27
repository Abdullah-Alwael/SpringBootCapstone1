package com.spring.boot.ecommerce.Service;

import com.spring.boot.ecommerce.Model.Category;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class CategoryService {

    private ArrayList<Category> categories = new ArrayList<>();

    public void addCategory(Category category) {
        categories.add(category);
    }

    public ArrayList<Category> getCategories() {
        return categories;
    }

    public boolean updateCategory(String categoryID, Category category) {
        for (Category c : categories) {
            if (c.getId().equals(categoryID)) {
                categories.set(categories.indexOf(c), category);
                return true;
            }
        }

        return false;
    }

    public boolean deleteCategory(String categoryID) {
        for (Category c : categories) {
            if (c.getId().equals(categoryID)) {
                categories.remove(c);
                return true;
            }
        }

        return false;
    }
    
}
