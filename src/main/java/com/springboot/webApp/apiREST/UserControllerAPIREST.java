package com.springboot.webApp.apiREST;

import java.util.List;
import java.util.stream.Collectors;

import com.springboot.webApp.model.Clan;
import com.springboot.webApp.repository.ClanRepository;
import com.springboot.webApp.repository.UserRepository;
import com.springboot.webApp.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.hateoas.*;

import javax.validation.Valid;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
class UserControllerAPIREST {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ClanRepository clanRepository;

    @GetMapping("/clans/{clanId}/users")
    public List<User> getAllUsersByClanId(@PathVariable(value = "clanId") Long clanId) {
        return userRepository.findByClanId(clanId);

    }
//POST http://localhost:8080/clans/1/users body {

    @PostMapping("/clans/{clanId}/users")
    public User createUser(@PathVariable(value = "clanId") long clanId, @Valid @RequestBody User user){
        return clanRepository.findById(clanId).map(clan->{
            user.setClan(clan);
            return userRepository.save(user);
        }).orElseThrow(()-> new ResourceNotFoundException("Clan id"+clanId+" not found"));
    }

    @PutMapping("/clans/{clanId}/users/{userId}")
    public User updateUser(@PathVariable(value = "clanId") Long clanId,
                           @PathVariable(value = "userId") Long userId,
                           @Valid @RequestBody User userRequest){
        if(!clanRepository.existsById(clanId)){
            throw new ResourceNotFoundException("ClanId"+clanId+" notFound");
        }
        return userRepository.findById(userId).map(user -> {
            user.setFirst_name(userRequest.getFirst_name());
            user.setLast_name(userRequest.getLast_name());
            user.setMail_address(userRequest.getMail_address());
            user.setUsername(userRequest.getUsername());
            return userRepository.save(user);
        }).orElseThrow(()-> new ResourceNotFoundException("UserId"+userId+"not Found"));
    }

    @DeleteMapping("/clans/{clanId}/users/{userId}")
    public  ResponseEntity<?> deleteUser(@PathVariable (value="clanId") Long clanId,
                                         @PathVariable (value="userId") Long userId){
        return userRepository.findByIdAndClanId(userId, clanId).map(user -> {
            userRepository.delete(user);
            return ResponseEntity.ok().build();
        }).orElseThrow(()->new ResourceNotFoundException("User not found by ID "+userId+" and Clan ID"+clanId));
    }







    /*
    private final UserModelAssembler assembler;
    private final UserRepository userRepository;



    UserControllerAPIREST(UserRepository userRepository, UserModelAssembler assembler) {
        this.assembler=assembler;
        this.userRepository = userRepository;

    }


*/



    // Aggregate root

    /*
    @GetMapping("/users")
    CollectionModel<EntityModel<User>> all(){
        List<EntityModel<User>> users = userRepository.findAll().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());
        return CollectionModel.of(users,
                linkTo(methodOn(UserControllerAPIREST.class).all()).withSelfRel());

    }

    @PostMapping("/users")
    ResponseEntity<?> newUser(@RequestBody User newUser){
        EntityModel<User> entityModel=assembler.toModel(userRepository.save(newUser));
        return ResponseEntity
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);
    }

    // Single item

    @GetMapping("/users/{id}")
    EntityModel<User> one(@PathVariable Long id) {

        User user = userRepository.findById(id) //
                .orElseThrow(() -> new UserNotFoundException(id));

       return assembler.toModel(user);
    }


    @PutMapping("/users/{id}")
    ResponseEntity<?> replaceUser(@RequestBody User newUser, @PathVariable Long id){
        User updatedUser= userRepository.findById(id)
                .map(user -> {
                    user.setMail_address(newUser.getMail_address());
                    user.setLast_name(newUser.getLast_name());
                    user.setFirst_name(newUser.getFirst_name());
                    user.setUsername(newUser.getUsername());
                    return userRepository.save(user);
                    })
                .orElseGet(()->{
                    newUser.setId(id);
                    return userRepository.save(newUser);
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
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
            return this.all();
        }
        else throw new UserNotFoundException(id);
    }


*/
    //for JSON AJAX DATATABLES
    @GetMapping("/testGetUsers")
    List<User> test() {
        return userRepository.findAll();
    }

}