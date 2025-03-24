package datoPersona;

import java.util.Scanner;

public class Coach extends Person {
    private boolean isNationalTeamCoach;
    private int numberOfWon;

    public Coach(String name, String surname, String birthDate, double motivationLevel, double annualSalary, int numberOfWon, boolean isNationalTeamCoach) {
        super(name, surname, birthDate, motivationLevel, annualSalary);
        this.isNationalTeamCoach = isNationalTeamCoach;
        this.numberOfWon = numberOfWon;
    }

    public void salaryIncrease() {
        this.annualSalary += 0.05 * this.annualSalary;
        System.out.println(this.name + "has increased the annual salary to " + this.annualSalary);
    }

    public void training() {
        if (this.isNationalTeamCoach) {
            this.motivationLevel += 0.3;
        } else {
            this.motivationLevel += 0.15;
        }
        if (this.motivationLevel > 10) {
            this.motivationLevel = 10;
        }
        System.out.println(this.name + "'s motivation is " + this.motivationLevel);
        salaryIncrease();
    }

    public static Coach createCoach(Scanner sc) {
        boolean isNationalTeamCoach=false, exit=false;
        Person person = Person.createPerson(sc);
        do {
            System.out.println("The coach is naitonal team coach? (Y/N): ");
            String response = sc.nextLine();
            if ((response).equalsIgnoreCase("Y")) {
                isNationalTeamCoach = true;
                exit = true;
            } else if (response.equalsIgnoreCase("N")) {
                exit = true;
            } else {
                System.out.println("Invalid input! The response must be Y or N!");
            }
        } while (!exit);
        System.out.println("How many match has the coach won: ");
        int numberOfWon = sc.nextInt();
        return new Coach(person.name, person.surname, person.birthDate, person.motivationLevel, person.annualSalary,
                numberOfWon, isNationalTeamCoach);
    }

    @Override
    public String toString() {
        return  super.toString() +
                "\nIs National Team Coach: " + this.isNationalTeamCoach+
                "\nMatch had won: " + this.numberOfWon +
                "\n------------------------------------------------";
    }


    //Getter

    public boolean getIsNationalTeamCoach() {
        return isNationalTeamCoach;
    }

    public int getNumberOfWon() {
        return numberOfWon;
    }
}
