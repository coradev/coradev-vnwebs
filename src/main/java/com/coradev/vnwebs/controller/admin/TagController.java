package com.coradev.vnwebs.controller.admin;

import com.coradev.vnwebs.model.Tag;
import com.coradev.vnwebs.service.TagService;
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
public class TagController {
    private final TagService tagService;

    @Autowired
    public TagController(TagService tagService) {
        this.tagService = tagService;
    }


    @GetMapping("/tags")
    public String list(@PageableDefault(size = 10, sort = {"id"}, direction = Sort.Direction.DESC)
                               Pageable pageable, Model model){
        model.addAttribute("page", tagService.listTag(pageable));
        return "admin/tags";
    }

    @GetMapping("/tags/input")
    public String input(Model model){
        model.addAttribute("tag", new Tag());
        return "admin/tag-input";
    }

    @GetMapping("/tags/{id}/input")
    public String editInput(@PathVariable Long id, Model model){
        model.addAttribute("tag", tagService.getTag(id));
        return "/admin/tag-input";
    }

    @PostMapping("/tags")
    public String postTag(@Valid Tag tag, BindingResult result, RedirectAttributes attributes){
        Tag tag1 = tagService.getTagByName(tag.getName());
        if(tag1 != null){
            result.rejectValue("name", "nameError", "the tag you add exists!");
        }

        if(result.hasErrors()){
            return "admin/tag-input";
        }

        Tag t = tagService.saveTag(tag);
        if(t == null){
            attributes.addFlashAttribute("message","add failed!");
        }else{
            attributes.addFlashAttribute("message", "add successful!");
        }
        return "redirect:/admin/tags";
    }

    @PostMapping("/tags/{id}")
    public String editTag(@Valid Tag tag, BindingResult result,
                          @PathVariable Long id, RedirectAttributes attributes){
        Tag tag2 = tagService.getTagByName(tag.getName());
        if(tag2 != null){
            result.rejectValue("name", "nameError", "The tag you add exists!");
        }

        if(result.hasErrors()){
            return "admin/tag-input";
        }

        Tag t = tagService.updateTag(id, tag);

        if(t == null){
            attributes.addFlashAttribute("message", "update failed!");
        }else {
            attributes.addFlashAttribute("message", "update successful!");
        }
        return "redirect:/admin/tags";
    }

    @GetMapping("/tags/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes attributes){
        attributes.addFlashAttribute("message","delete successful!");
        tagService.deleteTag(id);
        return "redirect:/admin/tags";
    }
}
