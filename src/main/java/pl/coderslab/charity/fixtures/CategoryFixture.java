package pl.coderslab.charity.fixtures;

import org.springframework.stereotype.Component;
import pl.coderslab.charity.entity.Category;
import pl.coderslab.charity.service.CategoryService;

import java.util.Arrays;
import java.util.List;

@Component
public class CategoryFixture {
    private CategoryService categoryService;

            private List<Category> categoryList = Arrays.asList(
                    new Category(null,"ubrania"),
                    new Category(null,"zabawki")
            );


    public CategoryFixture(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    public void loadIntoDB() {
        for (Category category : categoryList){
            categoryService.add(category);
        }
    }
}
