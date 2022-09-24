package com.helloshishir.support.users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("users")
public class UsersController {

    @Autowired
    UserService userService;

    // 1. get all users
    @GetMapping("list")
    public String getUsersList(ModelMap modelMap) {
        List<User> userList = userService.findAll();
        modelMap.put("userList", userList);
        return "users/list";
    }
    // 2. add new user form
    @GetMapping("create")
    public String create(ModelMap modelMap) {
        User user = new User();
        modelMap.put("user", user);
        return "users/form";
    }

    // 3. save/update existing user
    @PostMapping("save")
    public String save(@Valid @ModelAttribute User user, BindingResult bindingResult, ModelMap modelMap, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "users/form";
        }
        userService.save(user);
        redirectAttributes.addFlashAttribute("SUCCESS_MESSAGE", "User saved successfully!");
        return "redirect:/users/list";
    }

    // update user
    @GetMapping("edit/{id}")
    public String edit(@PathVariable("id") Integer id, ModelMap modelMap) {
        User user = userService.findById(id);
        modelMap.put("user", user);
        return "users/form";
    }
    // 4. delete existing user
    @GetMapping("delete/{id}")
    public String delete(@PathVariable("id") Integer id, RedirectAttributes redirectAttributes) {
        User user = userService.findById(id);
        userService.delete(user);
        redirectAttributes.addFlashAttribute("SUCCESS_MESSAGE", "User deleted successfully!");
        return "redirect:/users/list";
    }


    @ResponseBody
    @PostMapping("is_unique_email")
    public ResponseEntity<Boolean> isUniqueEmail(@Param("id") Integer id, @Param("email") String email) {
        return new ResponseEntity<>(userService.isEmailUnique(id, email), HttpStatus.OK);
    }



    @ResponseBody
    @PostMapping("is_unique_username")
    public ResponseEntity<Boolean> isUniqueUsername(@Param("id") Integer id, @Param("username") String username) {
        return new ResponseEntity<>(userService.isUsernameUnique(id, username), HttpStatus.OK);
    }
}
