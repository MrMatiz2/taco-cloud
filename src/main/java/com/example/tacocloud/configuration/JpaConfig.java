package com.example.tacocloud.configuration;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@EntityScan(basePackages = "com.example.tacoclouddomain.entities")
public class JpaConfig {
}
