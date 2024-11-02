package com.example.tacocloud.repositories;

import com.example.tacocloud.entities.TacoOrder;
import org.springframework.data.repository.CrudRepository;

public interface OrderRepository extends CrudRepository<TacoOrder, Long> {

}
