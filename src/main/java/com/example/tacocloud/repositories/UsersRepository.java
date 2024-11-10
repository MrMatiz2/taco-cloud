package com.example.tacocloud.repositories;

import com.example.tacocloud.entities.Users;
import org.springframework.data.repository.CrudRepository;

public interface UsersRepository extends CrudRepository<Users, Long> {

    Users findByUsername(String username);

}
