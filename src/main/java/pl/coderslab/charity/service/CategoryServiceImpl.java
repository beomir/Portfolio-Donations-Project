package pl.coderslab.charity.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.coderslab.charity.app.SecurityUtils;
import pl.coderslab.charity.entity.Category;
import pl.coderslab.charity.repository.CategoryRepository;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService{

    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }


    @Override
    public void add(Category category) {
        categoryRepository.save(category);
    }

    @Override
    public List<Category> getCategory() {
        return categoryRepository.getCategory();
    }

    @Override
    public Category category(Long id) {
        return categoryRepository.category(id);
    }

    @Override
    public void deactivateCategory(Long id) {
        Category category = categoryRepository.getOne(id);
        category.setActive(false);
        category.setLast_update(LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME));
        category.setChangeBy(SecurityUtils.usernameForActivations());
        categoryRepository.save(category);
    }

    @Override
    public void activateCategory(Long id) {
        Category category = categoryRepository.getOne(id);
        category.setActive(true);
        category.setLast_update(LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME));
        category.setChangeBy(SecurityUtils.usernameForActivations());
        categoryRepository.save(category);
    }


    @Override
    public void deleteCategory(Long id) {
        categoryRepository.deleteById(id);
    }

    @Override
    public List<Category> getActiveCategory() {
        return categoryRepository.getActiveCategory();
    }

}
