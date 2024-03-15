package com.ruangmain.warehouse.repository;

import com.ruangmain.warehouse.model.User;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends ListCrudRepository<User, Long> {

    Optional<User> findByEmail(String email);
}
