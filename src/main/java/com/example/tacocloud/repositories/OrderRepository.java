package com.example.tacocloud.repositories;

import com.example.tacocloud.entities.TacoOrder;
import com.example.tacocloud.entities.Users;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface OrderRepository extends CrudRepository<TacoOrder, Long> {

    List<TacoOrder> findByUserOrderByPlacedAtDesc(Users user, Pageable pageable);

}
