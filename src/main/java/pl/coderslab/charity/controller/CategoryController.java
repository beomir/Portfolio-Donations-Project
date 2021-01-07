package pl.coderslab.charity.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import pl.coderslab.charity.app.SecurityUtils;
import pl.coderslab.charity.entity.Category;
import pl.coderslab.charity.service.CategoryService;
import pl.coderslab.charity.service.UsersService;

import java.time.LocalDateTime;
import java.util.List;

@Controller
public class CategoryController {

    private final CategoryService categoryService;
    private final UsersService usersService;


    @Autowired
    public CategoryController(CategoryService categoryService, UsersService usersService) {

        this.categoryService = categoryService;
        this.usersService = usersService;
    }

    @GetMapping("/admin/formCategory")
    public String CategoryForm(Model model){
        model.addAttribute("category", new Category());

        usersService.loggedUserData(model);

        model.addAttribute("localDateTime", LocalDateTime.now());
        return "formCategory";
    }

    @PostMapping("/admin/formCategory")
    public String categoryAdd(Category category) {
        categoryService.add(category);
        return "redirect:/admin/categoryList";
    }


    @GetMapping("/admin/editCategory/{id}")
    public String institutionEdit(@PathVariable Long id, Model model) {

        usersService.loggedUserData(model);

        Category category = categoryService.category(id);
        model.addAttribute("category", category);

        List<Category> categories = categoryService.getCategory();
        model.addAttribute("categories", categories);


        model.addAttribute("localDateTime", LocalDateTime.now());
        return "editCategory";
    }

    @PostMapping("/admin/editCategory")
    public String categoryEditPost(Category category) {
        categoryService.add(category);
        return "redirect:/admin/categoryList";
    }

    @GetMapping("/admin/categoryList")
    public String categoryList(Model model) {
        usersService.loggedUserData(model);

        List<Category> categories = categoryService.getCategory();
        model.addAttribute("categories", categories);

        model.addAttribute("localDateTime", LocalDateTime.now());
        return "categoryList";
    }

//deactivate Category
    @GetMapping("/admin/categories/deactivate/{id}")
    public String deactivateCategory(@PathVariable Long id) {
        categoryService.deactivateCategory(id);
        return "redirect:/admin/categoryList";
    }

    //activate Category
    @GetMapping("/admin/categories/activate/{id}")
    public String activateCategory(@PathVariable Long id) {
        categoryService.activateCategory(id);
        return "redirect:/admin/categoryList";
    }

    //delete Category
    @GetMapping("/admin/categories/delete/{id}")
    public String removeCategory(@PathVariable Long id) {
        categoryService.deleteCategory(id);
        return "redirect:/admin/categoryList";
    }
}
