package com.kakura.museum.controllers;

import com.kakura.museum.models.*;
import com.kakura.museum.repo.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.*;

@Controller
public class AuthorController {

    private String lastName = "";
    private String firstName = "";

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private ExhibitRepository exhibitRepository;

    @GetMapping("/authors")
    public String authorsMain(Model model) {
        Iterable<Author> authors = authorRepository.findAll();
        model.addAttribute("authors", authors);
        model.addAttribute("firstName", firstName);
        model.addAttribute("lastName", lastName);
        return "authors-main";
    }

    @GetMapping("/authors/add")
    public String authorsAdd(Model model) {
        return "authors-add";
    }

    @PostMapping("/authors/add")
    public String authorsPostAdd(@RequestParam String firstname, @RequestParam String lastname, Model model) {
        Author author = new Author(firstname, lastname);
        author.setAuthorInfo(new AuthorInfo());
        authorRepository.save(author);
        return "redirect:/authors";
    }

    @PostMapping("/authors/{id}/delete")
    public String authorsDelete(@PathVariable long id, Model model) {
        Author author = authorRepository.findById(id).orElseThrow();
        Set<Exhibit> exhibitSet = author.getExhibits();

        for(Exhibit exhibit : exhibitSet) {
            Set<Author> authorSet = exhibit.getAuthors();
            authorSet.remove(author);
            exhibit.setAuthors(authorSet);
            exhibitRepository.save(exhibit);
        }

        authorRepository.delete(author);
        return "redirect:/authors";
    }

    @PostMapping("/authors/findbyfirstname")
    public String findByFirstName(@RequestParam String firstName, Model model) {
        List<Author> authorList = authorRepository.findAuthorsByFirstName(firstName);
        model.addAttribute("authors", authorList);
        model.addAttribute("firstName", firstName);
        return "authors-main";
    }

    @PostMapping("/authors/findbylastname")
    public String findByLastName(@RequestParam String lastName, Model model) {
        List<Author> authorList = authorRepository.findAuthorsByLastName(lastName);
        model.addAttribute("authors", authorList);
        model.addAttribute("lastName", lastName);
        return "authors-main";
    }

    @GetMapping("/authors/{id}/edit")
    public String authorsEdit(@PathVariable long id, Model model) {

        if (!authorRepository.existsById(id)) {
            return "redirect:/authors";
        }

        Optional<Author> author = authorRepository.findById(id);
        ArrayList<Author> res = new ArrayList<>();
        author.ifPresent(res::add);
        model.addAttribute("author", res);
        return "authors-edit";


        /*if (!exhibitRepository.existsById(id)) {
            return "redirect:/exhibits";
        }

        Optional<Exhibit> exhibit = exhibitRepository.findById(id);
        ArrayList<Exhibit> res = new ArrayList<>();
        exhibit.ifPresent(res::add);
        model.addAttribute("exhibit", res);

        return "exhibits-edit";*/
    }

    @PostMapping("/authors/{id}/edit")
    public String authorsEditPost(@PathVariable long id, @RequestParam String firstname, @RequestParam String lastname,
                                  @RequestParam String profession, @RequestParam int exhibitamount, Model model) {
        Author author = authorRepository.findById(id).orElseThrow();
        author.setFirstName(firstname);
        author.setLastName(lastname);
        author.getAuthorInfo().setProfession(profession);
        author.getAuthorInfo().setExhibitAmount(exhibitamount);
        authorRepository.save(author);

        return "redirect:/authors";
    }

}
