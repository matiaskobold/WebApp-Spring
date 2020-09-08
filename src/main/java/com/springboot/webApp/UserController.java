package com.springboot.webApp;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.hateoas.*;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
class UserController {


    private final UserRepository repository;

    UserController(UserRepository repository) {
        this.repository = repository;
    }

    // Aggregate root

    @GetMapping("/users")
    CollectionModel<EntityModel<User>> all(){
        List<EntityModel<User>> users = repository.findAll().stream()
                .map(user -> EntityModel.of(user,
                        linkTo(methodOn(UserController.class).one(user.getIdUsers())).withSelfRel(),
                        linkTo(methodOn(UserController.class).all()).withRel("users")))
                .collect(Collectors.toList());
        return CollectionModel.of(users,
                linkTo(methodOn(UserController.class).all()).withSelfRel());
    }

    @PostMapping("/users")
    User newUser(@RequestBody User newUser) {
        return repository.save(newUser);
    }

    // Single item

    @GetMapping("/users/{id}")
    EntityModel<User> one(@PathVariable Long id) {

        User user = repository.findById(id) //
                .orElseThrow(() -> new UserNotFoundException(id));

        return EntityModel.of(user, //
                linkTo(methodOn(UserController.class).one(id)).withSelfRel(),
                linkTo(methodOn(UserController.class).all()).withRel("users"));
    }


    @PutMapping("/users/{id}")
    User replaceUser(@RequestBody User newUser, @PathVariable Long id) {

        return repository.findById(id)
                .map(user -> {
                    user.setUsername(newUser.getUsername());
                    user.setFirst_name(newUser.getFirst_name());
                    user.setLast_name(newUser.getLast_name());
                    user.setMail_address(newUser.getMail_address());
                    return repository.save(user);
                })
                .orElseGet(() -> {
                    newUser.setIdUsers(id);
                    return repository.save(newUser);
                });
    }

    @DeleteMapping("/users/{id}")
    void deleteUser(@PathVariable Long id) {
        repository.deleteById(id);
    }
}