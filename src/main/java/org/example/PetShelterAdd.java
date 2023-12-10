package org.example;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class PetShelterAdd {

    public static void main(String[] args) {
        PetShelterAdd petShelterApp = new PetShelterAdd();
        petShelterApp.run();
    }

    private static final String DEFAULT_FILE_NAME = "pet_shelter_data.json";
    @Getter
    private final List<Animal> animals;
    private final ObjectMapper objectMapper;

    @Getter
    private final String fileName;

    public PetShelterAdd() {
        this(DEFAULT_FILE_NAME);
    }

    public PetShelterAdd(String fileName) {
        this.animals = new ArrayList<>();
        this.objectMapper = new ObjectMapper();
        this.fileName = fileName;

        loadAnimalsFromFile();
    }

    public void addPet(Animal animal) {
        animals.add(animal);
        System.out.println("Pet added: " + animal);
    }

    public void removePet(Animal animal) {
        if (animals.remove(animal)) {
            System.out.println("Pet removed: " + animal);
        } else {
            System.out.println("Pet not found: " + animal);
        }
    }

    private void loadAnimalsFromFile() {
        try {
            File file = new File(fileName);
            if (file.exists()) {
                animals.addAll(objectMapper.readValue(file, new TypeReference<List<Animal>>() {
                }));
                System.out.println("Loaded animals from file.");
            }
        } catch (IOException e) {
            System.err.println("Error loading animals from file: " + e.getMessage());
        }
    }

    private void saveAnimalsToFile() {
        try {
            objectMapper.writeValue(new File(fileName), animals);
            System.out.println("Saved animals to file.");
        } catch (IOException e) {
            System.err.println("Error saving animals to file: " + e.getMessage());
        }
    }

    public void addPet() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter the name of the pet:");
        String name = scanner.nextLine();

        System.out.println("Enter the breed of the pet:");
        String breed = scanner.nextLine();

        System.out.println("Enter the age of the pet:");
        int age = scanner.nextInt();

        Animal animal = new Animal(name, breed, age);
        animals.add(animal);

        System.out.println("Pet added: " + animal);
    }

    private void showAllPets() {
        if (animals.isEmpty()) {
            System.out.println("There are no pets in the shelter.");
        } else {
            System.out.println("Pets in the shelter:");
            for (Animal pet : animals) {
                System.out.println(pet);
            }
        }
    }

    private void removePetFromShelter() {
        Scanner scanner = new Scanner(System.in);

        if (animals.isEmpty()) {
            System.out.println("The shelter has no pets to remove.");
            return;
        }

        System.out.println("Select a pet to delete:");

        for (int i = 0; i < animals.size(); i++) {
            System.out.println((i + 1) + ". " + animals.get(i));
        }

        System.out.print("Enter the pet number to delete:");
        int choice = scanner.nextInt();

        if (choice > 0 && choice <= animals.size()) {
            Animal removedPet = animals.remove(choice - 1);
            System.out.println("Pet removed: " + removedPet);
        } else {
            System.out.println("Incorrect selection of pet to delete.");
        }
    }

    public void run() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Hello! Welcome to the Pet Shelter. What is your name?");
        String name = scanner.nextLine();
        System.out.println("Your name: " + name);
        System.out.println("Nice to meet you, " + name + "! On our website, in the PHOTO section, you can see all our FLUFFIES " +
                "and choose who you are looking for.");

        boolean running = true;
        while (running) {
            System.out.println("Menu:");
            System.out.println("1. Add a pet");
            System.out.println("2. View all pets");
            System.out.println("3. Remove a pet");
            System.out.println("4. Exit");

            System.out.print("Select an option:");
            if (!scanner.hasNextInt()) {
                System.out.println("Incorrect choice. Please enter a number.");
                scanner.nextLine(); // discard the incorrect input
                continue;
            }

            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    addPet();
                    break;
                case 2:
                    showAllPets();
                    break;
                case 3:
                    removePetFromShelter();
                    break;
                case 4:
                    saveAnimalsToFile();
                    System.out.println("Thank you for using. Goodbye, " + name + "!");
                    running = false;
                    break;
                default:
                    System.out.println("Incorrect choice. Try again.");
            }
        }
    }

    public void saveAnimalsToFileForTest() {
        saveAnimalsToFile();
    }

    public void searchPetByBreed() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter the breed of the pet:");
        String breed = scanner.nextLine();

        List<Animal> matchingAnimals = animals.stream()
                .filter(animal -> animal.getBreed().equalsIgnoreCase(breed))
                .collect(Collectors.toList());

        if (matchingAnimals.isEmpty()) {
            System.out.println("No pets of the breed " + breed + " found.");
        } else {
            System.out.println("Pets of the breed " + breed + ":");
            for (Animal pet : matchingAnimals) {
                System.out.println(pet.getName());
            }
        }
    }
}
