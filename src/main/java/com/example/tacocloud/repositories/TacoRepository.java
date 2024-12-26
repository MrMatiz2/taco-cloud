package com.example.tacocloud.repositories;

import com.example.tacoclouddomain.entities.Taco;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface TacoRepository extends PagingAndSortingRepository<Taco, Long>, ReactiveCrudRepository<Taco, Long> {
}
