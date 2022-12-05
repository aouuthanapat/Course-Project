package com.kakura.museum.controllers;

import com.kakura.museum.models.Event;
import com.kakura.museum.models.Exhibit;
import com.kakura.museum.models.Theme;
import com.kakura.museum.models.User;
import com.kakura.museum.repo.EventRepository;
import com.kakura.museum.repo.ExhibitRepository;
import com.kakura.museum.repo.ThemeRepository;
import com.kakura.museum.repo.UserRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.*;

@Controller
public class EventController {

    private static final Logger logger = LogManager.getLogger();

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private ExhibitRepository exhibitRepository;

    @Autowired
    private ThemeRepository themeRepository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/events")
    public String eventsMain(Model model) {
        Iterable<Event> events = eventRepository.findAll();
        model.addAttribute("events", events);
        return "events-main";
    }

    @GetMapping("/events/add")
    public String eventsAdd(Model model) {
        return "events-add";
    }

    @PostMapping("/events/add")
    public String eventsPostAdd(@RequestParam String title, @RequestParam String anons, @RequestParam String full_text, Model model) {
        Event event = new Event(title, anons, full_text);
        eventRepository.save(event);
        return "redirect:/events";
    }

    @GetMapping("/events/{id}")
    public String eventsDetails(@PathVariable(value = "id") long id, Model model) {

        if (!eventRepository.existsById(id)) {
            return "redirect:/events";
        }

        Event event = eventRepository.findById(id).orElseThrow();
        model.addAttribute("event", event);

        List<Exhibit> exhibitList = new ArrayList<>();
        exhibitList.addAll(event.getExhibits());
        model.addAttribute("exhibits", exhibitList);

        Set<Theme> themeSet = new HashSet<>();
        themeSet.addAll(event.getThemes());
        model.addAttribute("themes", themeSet);

        Set<User> userSet = new HashSet<>();
        userSet.addAll(event.getUsers());
        model.addAttribute("users", userSet);
        return "events-details";
    }

    @GetMapping("/events/{id}/edit")
    public String eventsEdit(@PathVariable(value = "id") long id, Model model) {

        if (!eventRepository.existsById(id)) {
            return "redirect:/events";
        }

        Optional<Event> event = eventRepository.findById(id);
        ArrayList<Event> res = new ArrayList<>();
        event.ifPresent(res::add);
        model.addAttribute("event", res);

        return "events-edit";
    }

    @PostMapping("/events/{id}/edit")
    public String eventsPostUpdate(@PathVariable(value = "id") long id, @RequestParam String title, @RequestParam String anons, @RequestParam String full_text, Model model) {
        Event event = eventRepository.findById(id).orElseThrow();
        event.setTitle(title);
        event.setAnons(anons);
        event.setFull_text(full_text);
        eventRepository.save(event);
        return "redirect:/events";
    }

    @PostMapping("/events/{id}/remove")
    public String eventsPostDelete(@PathVariable(value = "id") long id, Model model) {
        Event event = eventRepository.findById(id).orElseThrow();
        eventRepository.delete(event);
        return "redirect:/events";
    }

    @GetMapping("/events/{id}/edit/addexhibit")
    public String addExhibit(@PathVariable(value = "id") long id, Model model) {
        Event event = eventRepository.findById(id).orElseThrow();
        String eventTitle = event.getTitle();
        Iterable<Exhibit> exhibits = exhibitRepository.findAll();
        model.addAttribute("exhibits", exhibits);
        model.addAttribute("eventId", id);
        model.addAttribute("eventTitle", eventTitle);
        return "addexhibit";
    }

    @PostMapping("/events/{id}/edit/addexhibit")
    public String addExhibitPost(@PathVariable(value = "id") long id, @RequestParam long exhibitId, Model model) {
        Exhibit exhibit = exhibitRepository.findById(exhibitId).orElseThrow();
        Set<Exhibit> exhibitSet = new HashSet<>();
        exhibitSet.add(exhibit);
        Event event = eventRepository.findById(id).orElseThrow();

        event.setExhibits(exhibitSet);
        exhibit.setEvent(event);
        eventRepository.save(event);
        exhibitRepository.save(exhibit);

        return "redirect:/events/{id}";
    }

    @GetMapping("/events/{id}/edit/addtheme")
    public String addTheme(@PathVariable(value = "id") long id, Model model) {
        Iterable<Theme> themes = themeRepository.findAll();
        model.addAttribute("themes", themes);
        model.addAttribute("eventId", id);
        return "addtheme";
    }

    @PostMapping("/events/{id}/edit/addtheme")
    public String addThemePost(@PathVariable(value = "id") long id, @RequestParam long themeId, Model model) {
        Theme theme = themeRepository.findById(themeId).orElseThrow();
        Event event = eventRepository.findById(id).orElseThrow();

        Set<Theme> themeSet = event.getThemes();
        themeSet.add(theme);

        Set<Event> eventSet = theme.getEvents();
        eventSet.add(event);


        event.setThemes(themeSet);
        theme.setEvents(eventSet);

        eventRepository.save(event);
        themeRepository.save(theme);

        return "redirect:/events/{id}";
    }

    @GetMapping("/events/{id}/edit/addguide")
    public String addGuide(@PathVariable(value = "id") long id, Model model) {
        Iterable<User> users = userRepository.findAll();
        model.addAttribute("users", users);
        model.addAttribute("eventId", id);
        return "addguide";
    }

    @PostMapping("/events/{id}/edit/addguide")
    public String addGuidePost(@PathVariable(value = "id") long id, @RequestParam long userId, Model model) {
        User user = userRepository.findById(userId).orElseThrow();
        Event event = eventRepository.findById(id).orElseThrow();

        Set<User> userSet = event.getUsers();
        userSet.add(user);

        Set<Event> eventSet = user.getEvents();
        eventSet.add(event);

        event.setUsers(userSet);
        user.setEvents(eventSet);

        eventRepository.save(event);
        userRepository.save(user);

        return "redirect:/events/{id}";
    }

}
