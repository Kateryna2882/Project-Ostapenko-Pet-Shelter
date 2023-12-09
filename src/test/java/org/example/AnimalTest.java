package org.example;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AnimalTest {

    @Test
    void serializeAndDeserialize() throws Exception {
        Animal originalAnimal = new Animal("Funny", "Dog", 2);

        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(originalAnimal);

        Animal deserializedAnimal = objectMapper.readValue(json, Animal.class);

        assertEquals(originalAnimal, deserializedAnimal);
    }
}
