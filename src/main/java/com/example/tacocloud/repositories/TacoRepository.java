package com.example.tacocloud.repositories;

import com.example.tacoclouddomain.entities.Taco;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface TacoRepository extends PagingAndSortingRepository<Taco, Long>, CrudRepository<Taco, Long> {
}
