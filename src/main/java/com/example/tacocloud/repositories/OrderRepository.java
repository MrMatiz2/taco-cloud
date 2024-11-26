package com.example.tacocloud.repositories;

import com.example.tacoclouddomain.entities.TacoOrder;
import com.example.tacoclouddomain.entities.Users;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface OrderRepository extends CrudRepository<TacoOrder, Long> {

    List<TacoOrder> findByUserOrderByPlacedAtDesc(Users user, Pageable pageable);

}
