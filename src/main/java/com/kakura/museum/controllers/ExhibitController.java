package com.kakura.museum.controllers;

import com.kakura.museum.models.Author;
import com.kakura.museum.models.Exhibit;
import com.kakura.museum.repo.AuthorRepository;
import com.kakura.museum.repo.ExhibitRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.Date;
import java.util.*;

@Controller
public class ExhibitController {

    private static final Logger logger = LogManager.getLogger();

    @Autowired
    private ExhibitRepository exhibitRepository;

    @Autowired
    private AuthorRepository authorRepository;

    @GetMapping("/exhibits")
    public String exhibitsMain(Model model) {
        Iterable<Exhibit> exhibits = exhibitRepository.findAll();
        model.addAttribute("exhibits", exhibits);
        return "exhibits-main";
    }

    @GetMapping("/exhibits/add")
    public String exhibitsAdd(Model model) {
        return "exhibits-add";
    }

    @PostMapping("/exhibits/add")
    public String exhibitsPostAdd(@RequestParam String name, @RequestParam Date date, Model model) {
        Exhibit exhibit = new Exhibit(name, date);
        exhibitRepository.save(exhibit);
        return "redirect:/exhibits";
    }

    @GetMapping("/exhibits/{id}/edit")
    public String exhibitsEdit(@PathVariable(value = "id") long id, Model model) {

        if (!exhibitRepository.existsById(id)) {
            return "redirect:/exhibits";
        }

        Optional<Exhibit> exhibit = exhibitRepository.findById(id);
        ArrayList<Exhibit> res = new ArrayList<>();
        exhibit.ifPresent(res::add);
        model.addAttribute("exhibit", res);

        return "exhibits-edit";
    }

    @PostMapping("/exhibits/{id}/edit")
    public String exhibitsPostEdit(@PathVariable(value = "id") long id, @RequestParam String name, @RequestParam Date date, Model model) {
        Exhibit exhibit = exhibitRepository.findById(id).orElseThrow();
        exhibit.setName(name);
        exhibit.setReceiptDate(date);
        exhibitRepository.save(exhibit);
        return "redirect:/exhibits";
    }

    @PostMapping("/exhibits/{id}/edit/remove") //fixme
    public String exhibitsPostDelete(@PathVariable(value = "id") long id, Model model) {
        Exhibit exhibit = exhibitRepository.findById(id).orElseThrow();
        exhibitRepository.delete(exhibit);
        return "redirect:/exhibits";
    }

    @GetMapping("/exhibits/{id}/edit/addauthor")
    public String addAuthor(@PathVariable(value = "id") long id, Model model) {
        Exhibit exhibit = exhibitRepository.findById(id).orElseThrow();
        String exhibitName = exhibit.getName();
        Iterable<Author> authors = authorRepository.findAll();
        model.addAttribute("exhibitName", exhibitName);
        model.addAttribute("exhibitId", id);
        model.addAttribute("authors", authors);
        return "add-author-to-exhibit";
    }

    @PostMapping("/exhibits/{id}/edit/addauthor")
    public String addAuthorPost(@PathVariable long id, @RequestParam long authorId, Model model) {
        Author author = authorRepository.findById(authorId).orElseThrow();
        Exhibit exhibit = exhibitRepository.findById(id).orElseThrow();

        Set<Author> authorSet = exhibit.getAuthors();
        authorSet.add(author);

        Set<Exhibit> exhibitSet = author.getExhibits();
        exhibitSet.add(exhibit);

        exhibit.setAuthors(authorSet);
        author.setExhibits(exhibitSet);

        exhibitRepository.save(exhibit);
        authorRepository.save(author);

        return "redirect:/exhibits";
    }

    @PostMapping("/exhibits/findafterdate")
    public String findAfterDate(@RequestParam Date dateIn, Model model) {
        List<Exhibit> exhibitList = exhibitRepository.findExhibitAfterDate(dateIn);

        model.addAttribute("exhibits", exhibitList);
        return "exhibits-main";
    }

}
