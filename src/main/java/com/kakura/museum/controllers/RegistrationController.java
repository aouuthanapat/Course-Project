package com.kakura.museum.controllers;

import com.kakura.museum.models.Address;
import com.kakura.museum.models.Status;
import com.kakura.museum.models.User;
import com.kakura.museum.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class RegistrationController {

    @Autowired
    private UserService userService;

    @GetMapping("/registration")
    public String registration(Model model) {
        model.addAttribute("userForm", new User());

        return "registration";
    }

    @PostMapping("/registration") //fixme @Valid
    public String addUser(@RequestParam String username, @RequestParam String firstname,
                          @RequestParam String lastname, @RequestParam long phonenumber,
                          @RequestParam String password, Model model) {
        User user = new User();
        user.setPassword(password);
        user.setUsername(username);
        user.setFirstName(firstname);
        user.setLastName(lastname);
        user.setPhoneNumber(phonenumber);
        user.setAddress(new Address());
        user.setStatus(new Status());
        userService.saveUser(user);



       /* if (bindingResult.hasErrors()) {
            return "registration";
        }
        if (!userForm.getPassword().equals(userForm.getPasswordConfirm())){
            model.addAttribute("passwordError", "Пароли не совпадают");
            return "registration";
        }
        if (!userService.saveUser(userForm)){
            model.addAttribute("usernameError", "Пользователь с таким именем уже существует");
            return "registration";
        }*/

        return "redirect:/";
    }
}
