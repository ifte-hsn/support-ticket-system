package com.helloshishir.support.users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

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
        return "users/create";
    }

    // 3. save/update existing user
    @PostMapping("save")
    public String save(@ModelAttribute User user, ModelMap modelMap) {
        userService.save(user);
        return "redirect:/users/list";
    }

    // update user
    @GetMapping("edit/{id}")
    public String edit(@PathVariable("id") Integer id, ModelMap modelMap) {
        User user = userService.findById(id);
        modelMap.put("user", user);
        return "users/create";
    }
    // 4. delete existing user
    @GetMapping("delete/{id}")
    public String delete(@PathVariable("id") Integer id) {
        User user = userService.findById(id);
        userService.delete(user);
        return "redirect:/users/list";
    }
}
