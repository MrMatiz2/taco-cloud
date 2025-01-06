package com.example.tacocloud.actuator;

import com.example.tacocloud.repositories.TacoRepository;
import org.springframework.boot.actuate.info.Info;
import org.springframework.boot.actuate.info.InfoContributor;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class TacoCountInfoContributor implements InfoContributor {

    private final TacoRepository tacoRepository;

    public TacoCountInfoContributor(TacoRepository tacoRepository) {
        this.tacoRepository = tacoRepository;
    }

    @Override
    public void contribute(Info.Builder builder) {
        long tacoCount = tacoRepository.count();
        Map<String, Object> info = new HashMap<>();
        info.put("tacoCount", tacoCount);
        builder.withDetail("taco-stats", tacoCount);
    }
}
