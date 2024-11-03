package com.example.tacocloud.repositories;

import com.example.tacocloud.entities.TacoOrder;
import org.springframework.data.repository.CrudRepository;

import java.util.Date;
import java.util.List;

public interface OrderRepository extends CrudRepository<TacoOrder, Long> {

    List<TacoOrder> findByDeliveryZip(String zip);
    List<TacoOrder> readTacoOrderByDeliveryZipAndPlacedAtBetween(String zip, Date startDate, Date endDate);

}
