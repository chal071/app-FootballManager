package datoPersona;

import java.util.ArrayList;
import java.util.Scanner;

public class Person {
    protected final String name;
    protected final String surname;
    protected final String birthDate;
    protected double motivationLevel;
    protected double annualSalary;

    public Person(String name,String surname, String birthDate, double motivationLevel, double annualSalary) {
        this.name = name;
        this.surname = surname;
        this.birthDate = birthDate;
        this.motivationLevel = setMotivationLevel(motivationLevel);
        this.annualSalary = annualSalary;
    }

    public Person(String name, String surname, String birthDate) {
        this.name = name;
        this.surname = surname;
        this.birthDate = birthDate;
    }

    public void training() {
        System.out.println(this.name + " is having training!");
        this.motivationLevel+=0.2;
        if(this.motivationLevel > 10) {
            this.motivationLevel=10;
        }
        System.out.printf("%s's motivation is %.1f\n", this.name, this.motivationLevel);
    }

    public double setMotivationLevel(double motivationLevel) {
        if(motivationLevel < 1 || motivationLevel > 10) {
            throw new IllegalArgumentException("Invalid motivation level!");
        } else {
            return this.motivationLevel = motivationLevel;
        }
    }


    public static Person createPerson(Scanner sc) {
        System.out.println("Input the person's name: ");
        String name = sc.nextLine();
        System.out.println("Input the person's surname: ");
        String surname = sc.nextLine();
        System.out.println("Input the person's birthday: ");
        String birthday = sc.nextLine();
        System.out.println("Input the person's motivation level(1-10): ");
        int motivation = sc.nextInt();
        System.out.println("Input the person's annual salary: ");
        double annualSalary = sc.nextDouble();
        sc.nextLine();
        return new Person(name, surname, birthday, motivation, annualSalary);
    }

    @Override
    public String toString() {
        return "\n------------------------------------------------\n"+
                this.name + " " + this.surname + ":"
                + " \nBirthday: " + this.birthDate
                + "\nMotivation Level: " + this.motivationLevel
                + "\nAnnual Salary: " + this.annualSalary;
    }


    //Getter

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public double getMotivationLevel() {
        return motivationLevel;
    }

    public double getAnnualSalary() {
        return annualSalary;
    }
}
