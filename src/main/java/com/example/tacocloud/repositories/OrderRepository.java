package com.example.tacocloud.repositories;

import com.example.tacocloud.entities.TacoOrder;

public interface OrderRepository {

    TacoOrder save(TacoOrder order);

}
