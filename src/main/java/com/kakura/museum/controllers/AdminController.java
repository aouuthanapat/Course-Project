package com.kakura.museum.controllers;

import com.kakura.museum.models.Event;
import com.kakura.museum.models.User;
import com.kakura.museum.repo.EventRepository;
import com.kakura.museum.repo.UserRepository;
import com.kakura.museum.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class AdminController {

    private static final Logger logger = LogManager.getLogger();

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Autowired
    EventRepository eventRepository;

    @GetMapping("/admin")
    public String userList(Model model) {
        model.addAttribute("allUsers", userService.allUsers());
        return "admin";
    }

    @PostMapping("/admin/{id}/delete")
    public String  deleteUser(@PathVariable(value = "id") long id, Model model) {
            User user = userService.findUserById(id);
            Iterable<Event> events = eventRepository.findAll();
            for( Event event : events) {
                event.getUsers().remove(user);
                eventRepository.save(event);
            }
            userService.deleteUser(id);
        return "redirect:/admin";
    }

    @GetMapping("/admin/gt/{userId}")
    public String  gtUser(@PathVariable("userId") Long userId, Model model) {
        model.addAttribute("allUsers", userService.usergtList(userId));
        return "admin";
    }

    @GetMapping("/admin/{id}/edit")
    public String editUser(@PathVariable(value = "id") long id, Model model) {
        model.addAttribute("user", userService.findUserById(id));
        return "admin-edit";
    }

    @PostMapping("/admin/{id}/edit")
    public String editUserPost(@PathVariable(value = "id") long id, String username, String firstname, String lastname,
                               Long phonenumber, String city, String street, int housenumber, int category,
                               int experience, Model model) {
        User user = userService.findUserById(id);
        user.setUsername(username);
        user.setFirstName(firstname);
        user.setLastName(lastname);
        user.setPhoneNumber(phonenumber);
        user.getAddress().setCity(city);
        user.getAddress().setStreet(street);
        user.getAddress().setHouseNumber(housenumber);
        user.getStatus().setCategory(category);
        user.getStatus().setExperience(experience);
        userRepository.save(user);
        return "redirect:/admin";
    }
}