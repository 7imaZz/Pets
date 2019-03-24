package com.example.pets;

public class Pets {

    private String name, breed;
    private int gender, weight, id;


    public Pets(String name, String breed, int gender, int weight) {
        this.name = name;
        this.breed = breed;
        this.gender = gender;
        this.weight = weight;
    }

    public Pets(String name, String breed, int gender, int weight, int id) {
        this.name = name;
        this.breed = breed;
        this.gender = gender;
        this.weight = weight;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public String getBreed() {
        return breed;
    }

    public int getGender() {
        return gender;
    }

    public int getWeight() {
        return weight;
    }

    public int getId() {
        return id;
    }
}
