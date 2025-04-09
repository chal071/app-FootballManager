package datoPersona;

import java.util.Comparator;
import java.util.Objects;
import java.util.Random;
import java.util.Scanner;

public class Player extends Person {
    private int playerNumber;
    private String position;
    private double quality;

    public Player(String name, String surname, String birthDate, double motivationLevel, double annualSalary, int playerNumber, String position, double quality) {
        super(name,surname, birthDate, motivationLevel, annualSalary);
        this.playerNumber = playerNumber;
        this.position = setPosition(position);
        this.quality = setQuality(quality);
    }

    public Player(String name, String surname, String birthDate, int playerNumber){
        super(name, surname, birthDate);
        this.playerNumber = playerNumber;
    }

    public String setPosition(String position) {
        if (!position.matches("POR|DEF|MIG|DAV")) {
            throw new IllegalArgumentException("Invalid position!");
        } else {
            return this.position = position;
        }
    }

    public double setQuality(double quality) {
        if(quality < 30 || quality > 100) {
            throw new IllegalArgumentException("Invalid quality!");
        } else {
            return this.quality = quality;
        }
    }

    public String positionChange(String position) {
        Random rand = new Random();
        int even = rand.nextInt(3);
        if (Objects.equals(position, "POR")) {
            switch (even) {
                case 0:
                    this.position = "DEF";
                    break;
                case 1:
                    this.position = "DAV";
                    break;
                case 2:
                    this.position = "MIG";
                    break;
            }
        } else if (Objects.equals(position, "DEF")) {
            switch (even) {
                case 0:
                    this.position = "MIG";
                    break;
                case 1:
                    this.position = "DAV";
                    break;
                case 2:
                    this.position = "POR";
                    break;
                default:
            }
        } else if (Objects.equals(position, "DAV")) {
            switch (even) {
                case 0:
                    this.position = "MIG";
                    break;
                case 1:
                    this.position = "DEF";
                    break;
                case 2:
                    this.position = "POR";
                    break;
                default:
            }
        } else {
            switch (even) {
                case 0:
                    this.position = "DAV";
                    break;
                case 1:
                    this.position = "DEF";
                    break;
                case 2:
                    this.position = "POR";
                    break;
                default:
            }
        }
        return this.position;
    }

    @Override
    public void training() {
        super.training();
        Random rand = new Random();
        int chance = rand.nextInt(100);

        if (chance < 5) {
            this.position = positionChange(position);
            this.quality += 1;
            System.out.println(this.name + " changed position to " + this.position + "!");
            System.out.println(this.name + "'s quality increased by 1 → New quality: " + this.quality);
            System.out.printf("%s's quality increased 1 → New quality: %.1f\n", this.name, this.quality);
        } else {
            double increase;
            if (chance < 15) {
                increase = 0.3;
            } else if (chance < 35) {
                increase = 0.2;
            } else {
                increase = 0.1;
            }
            this.quality += increase;
            if (this.quality > 100) {
                this.quality = 100;
            }
            System.out.printf("%s's quality increased %.1f → New quality: %.1f\n", this.name, increase, this.quality);
        }
    }


    public double getQuality() {
        return quality;
    }

    public static Player createPlayer(Scanner sc) {
        Person person = Person.createPerson(sc);
        System.out.println("Input the person's player number: ");
        int playerNumber = sc.nextInt();
        sc.nextLine();
        System.out.println("Input the person's position(POR|DEF|MIG|DAV): ");
        String position = sc.nextLine().toUpperCase();
        System.out.println("Input the person's quality(30 - 100): ");
        int quality = sc.nextInt();
        return new Player(person.name, person.surname, person.birthDate, person.motivationLevel, person.annualSalary,
                playerNumber, position, quality){};
    }


    @Override
    public String toString() {
        return  super.toString() +
                "\nPlayer Number: " + this.playerNumber +
                "\nPosition: " + this.position +
                "\nQuality: " + this.quality +
                "\n------------------------------------------------";
    }

    public String getMarketSummary() {
        return String.format(
                "#%d | %s %s | %s | Q: %.1f | Mot: %.1f",
                this.playerNumber, this.getName(), this.getSurname(), this.position, this.quality, this.getMotivationLevel()
        );
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        Player player = (Player) obj;
        return playerNumber == player.playerNumber &&
                name.equalsIgnoreCase(player.name) &&
                surname.equalsIgnoreCase(player.surname);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name.toLowerCase(), surname.toLowerCase(), playerNumber);
    }

    public static Comparator<Player> byQualityThenMotivationThenSurname = (p1, p2) -> {
        if (p2.getQuality() != p1.getQuality()) {
            return Double.compare(p2.getQuality(), p1.getQuality());
        } else if (p2.getMotivationLevel() != p1.getMotivationLevel()) {
            return Double.compare(p2.getMotivationLevel(), p1.getMotivationLevel());
        } else {
            return p1.getSurname().compareToIgnoreCase(p2.getSurname());
        }
    };

    public static Comparator<Player> byPositionThenQuality = (p1, p2) -> {
        int comparator = p1.getPosition().compareToIgnoreCase(p2.getPosition());
        if (comparator == 0) {
            return Double.compare(p2.getQuality(), p1.getQuality());
        }
        return comparator;
    };


    //Getter
    public int getPlayerNumber() {
        return playerNumber;
    }

    public String getPosition() {
        return position;
    }

    public void setPlayerNumber(int playerNumber) {
        this.playerNumber = playerNumber;
    }
}