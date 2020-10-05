package com.springboot.webApp.repository;

import com.springboot.webApp.model.Clan;
import com.springboot.webApp.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findByClanId(Long clanId);
    Optional<User> findByIdAndClanId(Long id, Long clanId);
}
