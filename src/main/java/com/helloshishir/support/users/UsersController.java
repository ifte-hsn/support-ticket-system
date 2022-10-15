package com.helloshishir.support.users;

import com.helloshishir.support.util.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.ResourceUtils;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("users")
public class UsersController {

    @Autowired
    UserService userService;

    @Autowired
    StorageService storageService;

    // 1. get all users
    @GetMapping("index")
    public String getUsersList() {
        return "users/list";
    }

    @GetMapping("list")
    @ResponseBody
    public ResponseEntity<Page<User>> getUsers(@RequestParam(value = "offset", required = false, defaultValue = "0") int offset,
                                               @RequestParam(value = "limit", required = false, defaultValue = "10") int limit,
                                               @RequestParam(value = "sort", required = false, defaultValue = "id") String sortField,
                                               @RequestParam(value = "order", required = false, defaultValue = "ASC") String order){

        int pageNumber = offset;
        int perPage = limit;

        if (pageNumber != 0 && perPage != 0) {
            pageNumber = pageNumber/perPage;
        }


        return new ResponseEntity<>(userService.findAll(pageNumber, perPage, sortField, order), HttpStatus.OK);
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
    public String save(@Valid @ModelAttribute User user,
                       BindingResult bindingResult,
                       ModelMap modelMap,
                       RedirectAttributes redirectAttributes,
                       @RequestParam("image")MultipartFile multipartFile) throws IOException {

        if (bindingResult.hasErrors()) {
            return "users/form";
        }


        String fileName = "";

        if(!multipartFile.isEmpty()) {
            // get the file name
            fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
            user.setPhoto(fileName);
        }

        User savedUser = userService.save(user);

        if(!multipartFile.isEmpty()) {
            // get upload directory
            // in our case we will save it to the static resource directory
            // that exists in our class path
            String uploadDir = ResourceUtils.getURL("classpath:").getPath()+"/static/uploads/"+savedUser.getId();

            storageService.clearDir(uploadDir);
            storageService.save(multipartFile, uploadDir, fileName);
        }

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
