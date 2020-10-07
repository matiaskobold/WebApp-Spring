package com.springboot.webApp.repository;

import com.springboot.webApp.model.Clan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClanRepository extends JpaRepository<Clan, Long> {
    boolean existsClanByName(String string);
    Clan findById(Clan clan);
    Clan findByName(String string);
    //Las implementaciones de todos estas funciones te las da spring siempre y cuando escribas el nombre
    //de una manera especifica. EJ: "find/exists"+"By"+"atributo"+"And"+"atributo2"+...
}
