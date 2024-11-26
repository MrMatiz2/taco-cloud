package com.example.tacocloud.services;

import com.example.tacoclouddomain.entities.Ingredient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@Slf4j
public class IngredientsService {

    private final RestTemplate restTemplate;

    public IngredientsService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public Ingredient getIngredient(String ingredientId) {
        /*
            Map<String, String> urlVariables = new HashMap<>();
            urlVariables.put("id", ingredientId);
            return restTemplate.getForObject("http://localhost:8080/data-api/ingredients/{id}", Ingredient.class,
                            urlVariables);
        */
        /*
            Map<String, String> urlVariables = new HashMap<>();
            urlVariables.put("id", ingredientId);
            URI url = UriComponentsBuilder
                    .fromHttpUrl("http://localhost:8080/data-api/ingredients/{id}")
                    .build(urlVariables);
                return restTemplate.getForObject(url, Ingredient.class);
        */
        /*
            ResponseEntity<Ingredient> response = restTemplate.getForEntity("http://localhost:8080/data-api/ingredients/{id}",
                    Ingredient.class, ingredientId);
            log.info("Fetched time: {}", response.getHeaders().getDate());
            return response.getBody();
        */
        return restTemplate.getForObject("http://localhost:8080/data-api/ingredients/{id}", Ingredient.class,
                ingredientId);
    }

    public void updateIngredient(Ingredient ingredient) {
        restTemplate.put("http://localhost:8080/data-api/ingredients{id}", ingredient, ingredient.getId());
    }

    public void deleteIngredient(Ingredient ingredient) {
        restTemplate.delete("http://localhost:8080/data-api/ingredients{id}", ingredient.getId());
    }


    public Ingredient createIngredient(Ingredient ingredient) {
        /*
            ResponseEntity<Ingredient> responseEntity = restTemplate.postForEntity("http://localhost:8080/data-api/ingredients",
                    ingredient, Ingredient.class);
            log.info("New resource created at {}", responseEntity.getHeaders().getLocation());
            return responseEntity.getBody();
        */
        return restTemplate.postForObject("http://localhost:8080/data-api/ingredients", ingredient,
                Ingredient.class);
    }

    public java.net.URI createIngredientURI(Ingredient ingredient) {
        return restTemplate.postForLocation("http://localhost:8080/data-api/ingredients", ingredient);
    }


}
