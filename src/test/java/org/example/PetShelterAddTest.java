package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class PetShelterAddTest {
    private PetShelterAdd petShelter;
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @BeforeEach
    void setUpStreams() {
        System.setOut(new PrintStream(outContent));
    }

    @AfterEach
    void restoreStreams() {
        System.setOut(originalOut);
    }


    @BeforeEach
    public void setUp() {
        // Create a new PetShelterAdd instance before each test
        petShelter = new PetShelterAdd("test_pet_shelter_data.json");
    }

    @Test
    public void testAddPet() {
        // Create a sample animal
        Animal testAnimal = new Animal("TestName", "TestBreed", 3);

        // Add the animal to the shelter
        petShelter.addPet(testAnimal);

        // Check if the added pet is present in the list
        assertTrue(petShelter.getAnimals().contains(testAnimal));
    }

    @Test
    public void testRemovePet() {
        // Create a sample animal
        Animal testAnimal = new Animal("TestName", "TestBreed", 3);

        // Add the animal to the shelter
        petShelter.addPet(testAnimal);

        // Remove the animal from the shelter
        petShelter.removePet(testAnimal);

        // Check if the removed pet is not present in the list
        Assertions.assertFalse(petShelter.getAnimals().contains(testAnimal));
    }

    @Test
    public void testAddPetWithScannerInput() {
        // Mock user input for adding a pet
        ByteArrayInputStream in = new ByteArrayInputStream("TestName\nTestBreed\n3\n".getBytes());
        System.setIn(in);

        // Add pet using scanner input
        petShelter.addPet();

        // Reset System.in to the original input stream
        System.setIn(System.in);

        // Check if the added pet is present in the list
        List<Animal> animals = petShelter.getAnimals();
        Assertions.assertFalse(animals.isEmpty());
        assertEquals("TestName", animals.get(0).getName());
        assertEquals("TestBreed", animals.get(0).getBreed());
        assertEquals(3, animals.get(0).getAge());
    }

    @Test
    void testSaveAndLoadAnimalsToFile() throws IOException {
        // Створіть зразок тварини
        Animal testAnimal = new Animal("TestName", "TestBreed", 3);

        // Додайте тварину в притулок
        petShelter.addPet(testAnimal);

        // Збережіть тварин у файл
        petShelter.saveAnimalsToFileForTest();

        // Завантажте тварин з файлу в новий екземпляр PetShelterAdd
        PetShelterAdd newPetShelter = new PetShelterAdd("test_pet_shelter_data.json");

        // Перевірте, чи завантажена тварина присутня в новому екземплярі
        assertTrue(newPetShelter.getAnimals().stream()
                .anyMatch(animal ->
                        animal.getName().equals(testAnimal.getName()) &&
                                animal.getBreed().equals(testAnimal.getBreed()) &&
                                animal.getAge() == testAnimal.getAge()
                ));

        // Додаткові перевірки для файлових операцій
        File testFile = new File("test_pet_shelter_data.json");
        assertTrue(testFile.exists()); // Перевірте, чи файл існує

        // Прибирання: видаліть тестовий файл
        Files.deleteIfExists(Path.of("test_pet_shelter_data.json"));
    }


    @Test
    public void testSaveAnimalsToFile() throws IOException {
        // Створіть зразок тварини
        Animal testAnimal = new Animal("TestName", "TestBreed", 3);

        // Додайте тварину в притулок
        petShelter.addPet(testAnimal);

        // Збережіть тварин у файл за допомогою публічного методу
        petShelter.saveAnimalsToFileForTest();

        // Завантажте тварин з файлу в новий екземпляр PetShelterAdd
        PetShelterAdd newPetShelter = new PetShelterAdd("test_pet_shelter_data.json");

        // Перевірте, чи завантажена тварина присутня в новому екземплярі
        assertTrue(newPetShelter.getAnimals().contains(testAnimal));

        // Видаліть тестовий файл
        Files.deleteIfExists(Path.of("test_pet_shelter_data.json"));
    }

    @Test
    public void testSearchPetByBreed() {
        PetShelterAdd petShelterAdd = new PetShelterAdd();
        Animal dog = new Animal("Dog1", "Dog", 2);
        Animal cat1 = new Animal("Cat1", "Cat", 1);
        Animal cat2 = new Animal("Cat2", "Cat", 3);

        // Додайте тварин у притулок
        petShelterAdd.addPet(dog);
        petShelterAdd.addPet(cat1);
        petShelterAdd.addPet(cat2);

        // Перенаправте System.out для захоплення виводу
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        // Імітуйте введення користувача для пошуку тварин за породою
        ByteArrayInputStream in = new ByteArrayInputStream("Cat\n".getBytes());
        System.setIn(in);

        // Шукайте тварин за породою "Cat"
        petShelterAdd.searchPetByBreed();

        // Скиньте System.in на початковий вхідний потік
        System.setIn(System.in);

        // Скиньте System.out на початковий вихідний потік
        System.setOut(System.out);

        // Перевірте, чи вивід містить очікувану інформацію
//        petShelterAdd.searchPetByBreed();
    }

}
