package com.scrumoftheearth.springbootapi.controller;

import com.scrumoftheearth.springbootapi.error.NotUniqueException;
import com.scrumoftheearth.springbootapi.model.User;
import com.scrumoftheearth.springbootapi.security.SecurityUser;
import com.scrumoftheearth.springbootapi.service.UserService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/user")
@ApiOperation(value = "/api/user", tags = "User Model Controller")
public class UserController {

    private final UserService userService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
        this.bCryptPasswordEncoder = new BCryptPasswordEncoder();
    }

    @GetMapping("/{username}")
    @ApiOperation(value = "Get a user by Username", response = User.class, httpMethod = "GET")
    public ResponseEntity<?> READUser(@PathVariable("username") String username, Principal principal) throws Throwable {
        User user = userService.getByUsername(username);
        if (user.getUserName().equals(principal.getName())) {
            return new ResponseEntity<>(user, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Client not Authorized to access this resource!", HttpStatus.UNAUTHORIZED);
        }
    }

    @PostMapping("")
    @ApiOperation(value = "Create a new User", response = User.class, httpMethod = "POST")
    public ResponseEntity<?> CREATEUser(@Valid @RequestBody User user, BindingResult bindingResult) {
        UserErrors userErrors = new UserErrors().nonUniqueUserName(userService.checkUserNameNotUnique(user.getUserName()));
        checkValidPasswords(user, userErrors);
        Optional<ResponseEntity<Map<String, Map<String, String>>>> fieldErrors = getFieldErrors(bindingResult, userErrors);
        if (fieldErrors.isPresent()) {
            return fieldErrors.get();
        }
        try {
            String encryptedPass = bCryptPasswordEncoder.encode(user.getPassword());
            user.setSaltedHashedPassword(encryptedPass);
            user = userService.saveUser(user);
            // Do not return the password to the client!!
            // That could leak the password to a malicious user!
            user.setPassword(null);
            user.setPasswordConfirmation(null);
        } catch (NotUniqueException ex) {
            throw new RuntimeException("Failed at saving User due to uniqueness! This should not occur!", ex);
        }
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "Update a user by ID", response = User.class, httpMethod = "PUT", notes = "Will require authorzation of \"ROLE_USER\"")
    public ResponseEntity<?> UPDATEUser(@PathVariable("id") Long id, @Valid @RequestBody User updatedUser, BindingResult bindingResult) throws Throwable {
        User user = userService.getById(id);
        UserErrors userErrors = new UserErrors();
        if (!user.getUserName().equals(updatedUser.getUserName())) {
            userErrors.nonUniqueUserName(userService.checkUserNameNotUnique(updatedUser.getUserName()));
        }
        // TODO: Implement edit without needing
        checkValidPasswords(updatedUser, userErrors);
        Optional<ResponseEntity<Map<String, Map<String, String>>>> fieldErrors = getFieldErrors(bindingResult, userErrors);
        if (fieldErrors.isPresent()) {
            return fieldErrors.get();
        }
        try {
            if (user.getPassword() != null) {
                String encryptedPass = bCryptPasswordEncoder.encode(user.getPassword());
                user.setSaltedHashedPassword(encryptedPass);
            }
            user = userService.updateUser(user, updatedUser);
        } catch (NotUniqueException ex) {
            throw new RuntimeException("Failed at saving User due to uniqueness! This should not occur!", ex);
        }
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    private Optional<ResponseEntity<Map<String, Map<String, String>>>> getFieldErrors(BindingResult bindingResult, UserErrors userErrors) {
        /* Error Handling Based on article by Baeldung.
            Baeldung 2020, Validation in Spring Boot, Baeldung, Viewed on 15/08/2000
            <https://www.baeldung.com/spring-boot-bean-validation>
         */
        Map<String, String> fieldErrors = new HashMap<>();
        if (bindingResult.hasErrors() || userErrors.hasErrors()) {
            bindingResult.getAllErrors().forEach(error -> {
                fieldErrors.put(((FieldError) error).getField(), error.getDefaultMessage());
            });
            if (userErrors.nonUniqueUserName) {
                fieldErrors.put("userName", "User Name must be unique!");
            }
            if (userErrors.badPassword) {
                fieldErrors.put("password", "Password must be valid!");
            }
            if (userErrors.badPasswordConfirmation) {
                fieldErrors.put("passwordConfirmation", "Password Confirmation must be valid!");
            }
            if (userErrors.passwordsMismatch) {
                fieldErrors.put("passwordConfirmation", "Both Passwords entered must match!");
            }
        }
        if (!fieldErrors.isEmpty()) {
            Map<String, Map<String, String>> jsonResponse = new HashMap<>();
            jsonResponse.put("errors", fieldErrors);
            return Optional.of(new ResponseEntity<>(jsonResponse, HttpStatus.BAD_REQUEST));
        }
        return Optional.empty();
    }

    private void checkValidPasswords(User user, UserErrors userErrors) {
        userErrors.badPassword = user.getPassword() == null;
        userErrors.badPasswordConfirmation = user.getPassword() == null;
        if (userErrors.badPassword || userErrors.badPasswordConfirmation) {
            return;
        }
        String tempPassword = user.getPassword().trim();
        String tempPasswordConfirmation = user.getPasswordConfirmation().trim();
        userErrors.badPassword = tempPassword.isEmpty();
        userErrors.badPasswordConfirmation = tempPasswordConfirmation.isEmpty();
        userErrors.passwordsMismatch = !tempPassword.equals(tempPasswordConfirmation);
    }

    // Basic nested class for printing the correct error messages
    protected static class UserErrors {

        public boolean nonUniqueUserName;
        public boolean badPassword;
        public boolean badPasswordConfirmation;
        public boolean passwordsMismatch;

        public boolean hasErrors() {
            return nonUniqueUserName || badPassword || badPasswordConfirmation || passwordsMismatch;
        }

        public UserErrors nonUniqueUserName(boolean bool) {
            this.nonUniqueUserName = bool;
            return this;
        }
    }
}