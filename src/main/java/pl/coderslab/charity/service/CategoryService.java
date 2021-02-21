package pl.coderslab.charity.service;

import pl.coderslab.charity.entity.Category;

import java.util.List;

public interface CategoryService {

    void add(Category category);

    List<Category> getCategory();

    Category category(Long id);

    void deactivateCategory(Long id);

    void activateCategory(Long id);

    void deleteCategory(Long id);

    List<Category> getActiveCategory();

}
