package com.springboot.webApp;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
class UserModelAssembler implements RepresentationModelAssembler<User, EntityModel<User>> {

    @Override
    public EntityModel<User> toModel(User user){
        return EntityModel.of(user,
                linkTo(methodOn(UserController.class).one(user.getIdUsers())).withSelfRel(),
                linkTo(methodOn(UserController.class).all()).withRel("users"));
    }
}

