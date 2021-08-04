package com.la_haus.infrastructure.mysql.repository;

import com.la_haus.domain.entity.Favorites;
import com.la_haus.domain.entity.Property;
import com.la_haus.domain.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Integer> {
    Optional<User> findByEmail(String email);
    Page<Favorites> findById(int id,Pageable pageable);
}
