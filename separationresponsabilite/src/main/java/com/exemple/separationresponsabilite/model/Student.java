package com.exemple.separationresponsabilite.model;

public class Student {

    private String reference;
    private String firstName;
    private String lastName;
    private int age;

    public String getReference() { return reference; }
    public void setReference(String reference) { this.reference = reference; }

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public int getAge() { return age; }
    public void setAge(int age) { this.age = age; }
}
