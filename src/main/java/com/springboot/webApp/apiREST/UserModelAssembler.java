package com.springboot.webApp.apiREST;

import com.springboot.webApp.model.User;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
class UserModelAssembler implements RepresentationModelAssembler<User, EntityModel<User>> {

    @Override
    public EntityModel<User> toModel(User user){
        return EntityModel.of(user,
                linkTo(methodOn(UserControllerAPIREST.class).one(user.getIdUsers())).withSelfRel(),
                linkTo(methodOn(UserControllerAPIREST.class).all()).withRel("users"));
    }
}

