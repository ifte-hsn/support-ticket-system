package com.helloshishir.support.users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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
    // 2. add new user
    // 3. update existing user
    // 4. delete existing user
}
