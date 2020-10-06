package com.springboot.webApp.repository;

import com.springboot.webApp.model.Clan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClanRepository extends JpaRepository<Clan, Long> {
    boolean existsClanByName(String string);
}
