package pl.coderslab.charity.fixtures;

import org.springframework.stereotype.Component;
import pl.coderslab.charity.app.TimeUtils;
import pl.coderslab.charity.entity.Category;
import pl.coderslab.charity.service.CategoryService;

import java.util.Arrays;
import java.util.List;

@Component
public class CategoryFixture {
    private CategoryService categoryService;

            private List<Category> categoryList = Arrays.asList(
                    new Category(null,"ubrania, które nadają się do ponownego użycia", TimeUtils.timeNowLong(),TimeUtils.timeNowLong(),"system",true),
                    new Category(null,"ubrania, do wyrzucenia",TimeUtils.timeNowLong(),TimeUtils.timeNowLong(),"system",true),
                    new Category(null,"książki",TimeUtils.timeNowLong(),TimeUtils.timeNowLong(),"system",true),
                    new Category(null,"zabawki",TimeUtils.timeNowLong(),TimeUtils.timeNowLong(),"system",true),
                    new Category(null,"inne",TimeUtils.timeNowLong(),TimeUtils.timeNowLong(),"system",true)
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
