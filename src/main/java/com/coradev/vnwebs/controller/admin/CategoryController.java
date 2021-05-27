package com.coradev.vnwebs.controller.admin;

import com.coradev.vnwebs.model.Category;
import com.coradev.vnwebs.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping("/admin")
public class CategoryController {


    private final CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/categories")
    public String list(@PageableDefault(size = 10, sort = {"id"}, direction = Sort.Direction.DESC)
                               Pageable pageable, Model model){
        model.addAttribute("page", categoryService.listCategory(pageable));
        return "admin/categories";
    }

    @GetMapping("/categories/input")
    public String input(Model model){
        model.addAttribute("category", new Category());
        return "admin/category-input";
    }

    @GetMapping("/categories/{id}/input")
    public String editInput(@PathVariable Long id, Model model){
        model.addAttribute("category", categoryService.getCategory(id));
        return "admin/category-input";
    }

    @PostMapping("/categories")
    public String post(@Valid Category category, BindingResult result, RedirectAttributes attributes){
        Category _category = categoryService.getCategoryByName(category.getName());
        if(_category != null){
            result.rejectValue("name", "nameError", "The category you add exist!");
        }
        if(result.hasErrors()){
            return "admin/category-input";
        }
        Category c = categoryService.saveCategory(category);
        if(c == null){
            attributes.addFlashAttribute("message", "add failed!");
        }else{
            attributes.addFlashAttribute("message", "add successful!");
        }
        return "redirect:/admin/categories";
    }

    @PostMapping("/categories/{id}")
    public String editPost(@Valid Category category, BindingResult result,
                           @PathVariable Long id, RedirectAttributes attributes){
        Category _category = categoryService.getCategoryByName(category.getName());
        if(_category != null){
            result.rejectValue("name", "nameError", "The category you add exist!");
        }
        if(result.hasErrors()){
            return "admin/category-input";
        }
        Category c = categoryService.updateCategory(id, category);
        if(c == null){
            attributes.addFlashAttribute("message", "update failed!");
        }else{
            attributes.addFlashAttribute("message", "update successful!");
        }
        return "redirect:/admin/categories";
    }

    @GetMapping("categories/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes attributes){
        attributes.addFlashAttribute("message", "delete successful!");
        categoryService.deleteCategory(id);
        return "redirect:/admin/categories";
    }



}
