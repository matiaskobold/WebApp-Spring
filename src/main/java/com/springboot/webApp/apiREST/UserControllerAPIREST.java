package com.springboot.webApp.apiREST;

import java.util.List;
import java.util.stream.Collectors;

import com.springboot.webApp.repository.UserRepository;
import com.springboot.webApp.model.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.hateoas.*;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
class UserControllerAPIREST {

    private final UserModelAssembler assembler;
    private final UserRepository repository;

    UserControllerAPIREST(UserRepository repository, UserModelAssembler assembler) {
        this.assembler=assembler;
        this.repository = repository;
    }

    // Aggregate root

    @GetMapping("/users")
    CollectionModel<EntityModel<User>> all(){
        List<EntityModel<User>> users = repository.findAll().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());
        return CollectionModel.of(users,
                linkTo(methodOn(UserControllerAPIREST.class).all()).withSelfRel());

    }

    @PostMapping("/users")
    ResponseEntity<?> newUser(@RequestBody User newUser){
        EntityModel<User> entityModel=assembler.toModel(repository.save(newUser));
        return ResponseEntity
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);
    }

    // Single item

    @GetMapping("/users/{id}")
    EntityModel<User> one(@PathVariable Long id) {

        User user = repository.findById(id) //
                .orElseThrow(() -> new UserNotFoundException(id));

       return assembler.toModel(user);
    }


    @PutMapping("/users/{id}")
    ResponseEntity<?> replaceUser(@RequestBody User newUser, @PathVariable Long id){
        User updatedUser=repository.findById(id)
                .map(user -> {
                    user.setMail_address(newUser.getMail_address());
                    user.setLast_name(newUser.getLast_name());
                    user.setFirst_name(newUser.getFirst_name());
                    user.setUsername(newUser.getUsername());
                    return repository.save(user);
                    })
                .orElseGet(()->{
                    newUser.setIdUsers(id);
                    return repository.save(newUser);
                });
        EntityModel<User> entityModel = assembler.toModel(updatedUser);
        return ResponseEntity
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);
    }

    //Deletes single user by ID, throws exception if user is not found.
    //DELETE: localhost:8080/users/{id number}
    @DeleteMapping("/users/{id}")
    CollectionModel<EntityModel<User>> deleteUser(@PathVariable Long id){
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return this.all();
        }
        else throw new UserNotFoundException(id);
    }



    //testing json ajax
    @GetMapping("/testGetUsers")
    List<User> test() {
        return repository.findAll();
    }

}