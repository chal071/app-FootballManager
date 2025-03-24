import datoPersona.Coach;
import datoPersona.Player;
import datoPersona.Person;
import java.util.ArrayList;
import java.util.Scanner;


public class Team {
    private String teamName;
    private int foundationYear;
    private String city;
    private String stadiumName;
    private Person president;
    private Coach coach;
    private ArrayList<Person> players;

    public Team (String teamName, int foundationYear, String city, String stadiumName, ArrayList<Person> players) {
        this.teamName = teamName;
        this.foundationYear = foundationYear;
        this.city = city;
        this.stadiumName = stadiumName;
        this.players = players;
    }

    public Team (String teamName, int foundationYear, String city, String stadiumName) {
        this.teamName = teamName;
        this.foundationYear = foundationYear;
        this.city = city;
        this.stadiumName = stadiumName;
    }

    public static Team createTeam(Scanner sc, ArrayList<Person> market) {
        System.out.println("Team's name: ");
        String teamName = sc.nextLine();
        System.out.println("Year of foundation: ");
        int foundationYear = Integer.parseInt(sc.nextLine());
        System.out.println("City: ");
        String city = sc.nextLine();
        System.out.println("Stadium: ");
        String stadiumName = sc.nextLine();
        ArrayList<Person> players = new ArrayList<>();
        Team team = new Team(teamName, foundationYear, city, stadiumName, players);
        team.changePresident(market, sc);
        team.changeCoach(market, sc);
        team.askForHowManyPlayers(sc, market, team);
        return team;
    }

    public void askForHowManyPlayers(Scanner sc, ArrayList<Person> market, Team team) {
        System.out.println("How many player do you want to add?");
        int numberOfPlayers = sc.nextInt();
        sc.nextLine();
        for (int i = 0; i < numberOfPlayers; i++) {
            System.out.println("Player " + (i + 1));
            team.addPlayerToTeam(sc, market);
        }
    }

    public void calcularTeamQuality() {
        double qualityTotal = 0;
        int count = 0;

        for (Person person : this.players) {
            if (person instanceof Player) {
                qualityTotal += ((Player) person).getQuality();
                count++;
            }
        }
        if (count > 0) {
            qualityTotal = qualityTotal / count;
        }
        System.out.println("quality: " + qualityTotal);
    }

    public void addPlayerToTeam(Scanner sc, ArrayList<Person> market) {
        System.out.println("Input the player's name: ");
        String playerName = sc.nextLine();
        System.out.println("Input the player's surname: ");
        String playerSurname = sc.nextLine();

        boolean found = MarketManager.searchPersonInMarket(market, playerName, playerSurname);

        if (found) {
            Player newPlayer = (Player) MarketManager.loadSinglePersonData(market, playerName, playerSurname);

            if (isPlayerInTeam(this, playerName, playerSurname, newPlayer.getPlayerNumber())) {
                System.out.println("This player is already in the team!");
                return;
            }

            if (isPlayerNumberTaken(this, newPlayer.getPlayerNumber())) {
                System.out.println("Dorsal number already taken. Please choose another:");
                int newNumber;
                do {
                    System.out.print("New dorsal: ");
                    newNumber = Integer.parseInt(sc.nextLine());
                } while (isPlayerNumberTaken(this, newNumber));
                newPlayer.setPlayerNumber(newNumber);
            }

            this.players.add(newPlayer);
            MarketManager.removePersonFromMarket(market, playerName, playerSurname);
            System.out.println(newPlayer.getName() + " added to " + this.teamName + "!");
        } else {
            System.out.println("Person not found in market!");
        }
    }


    public static boolean isPlayerInTeam(Team team, String name, String surname, int number) {
        for (Person p : team.getPlayers()) {
            if (p instanceof Player player &&
                    player.getName().equalsIgnoreCase(name) &&
                    player.getSurname().equalsIgnoreCase(surname) &&
                    player.getPlayerNumber() == number) {
                return true;
            }
        }
        return false;
    }

    public static boolean isPlayerNumberTaken(Team team, int number) {
        for (Person p : team.getPlayers()) {
            if (p instanceof Player player &&
                    player.getPlayerNumber() == number) {
                return true;
            }
        }
        return false;
    }

    public static Team searchTeamInTeamList(Scanner sc, ArrayList<Team> teams) {
        System.out.println("Input the team's name: ");
        String teamName = sc.nextLine().trim();
        for (Team team : teams) {
            if (team.getTeamName().equalsIgnoreCase(teamName)) {
                return team;
            }
        }

        System.out.println("Team not found in team list!");
        return null;
    }

    public void changeCoach(ArrayList<Person> market, Scanner sc) {
        boolean exit = false;
        do {
            System.out.println("Input the coach's name: ");
            String coachName = sc.nextLine().trim();
            System.out.println("Input the coach's surname: ");
            String coachSurname = sc.nextLine().trim();
            System.out.println("Searching in the market....");
            boolean found = MarketManager.searchPersonInMarket(market, coachName, coachSurname);
            if (found) {
                Coach newCoach = (Coach) MarketManager.loadSinglePersonData(market, coachName, coachSurname);
                System.out.println("Found " + newCoach.getName() + ". Adding to team");
                if (this.coach != null) {
                    System.out.println("Moving current coach " + this.coach.getName() + " to the market.");
                    MarketManager.addPersonToMarket(market, this.coach);
                }
                this.coach = newCoach;
                exit = true;
            } else {
                System.out.println("Coach not found in market! Do you want to create a new coach? (Y/N)");
                String response = sc.nextLine().trim();
                if (response.equalsIgnoreCase("y")) {
                    if (this.coach != null) {
                        System.out.println("Moving current coach " + this.coach.getName() + " to the market.");
                        MarketManager.addPersonToMarket(market, this.coach);
                    }
                    this.coach = Coach.createCoach(sc);
                    System.out.println("New coach added to the team: " + this.coach.getName());
                    exit = true;
                } else if (response.equalsIgnoreCase("n")) {
                    System.out.println("Okay, let's try again.");
                } else {
                    System.out.println("Invalid input! Please enter Y or N.");
                }
            }
        } while (!exit);
    }

    public void changePresident(ArrayList<Person> market, Scanner sc) {
        boolean exit = false;
        do {
            System.out.println("Input the president's name: ");
            String presidentName = sc.nextLine().trim();
            System.out.println("Input the president's surname: ");
            String presidentSurname = sc.nextLine().trim();
            System.out.println("Searching in the market....");
            boolean found = MarketManager.searchPersonInMarket(market, presidentName, presidentSurname);
            if (found) {
                Person newPresident = MarketManager.loadSinglePersonData(market, presidentName, presidentSurname);
                System.out.println("Found " + newPresident.getName() + ". Adding as president to team");
                if (this.president != null) {
                    System.out.println("Moving current president " + this.president.getName() + " to the market.");
                    MarketManager.addPersonToMarket(market, this.president);
                }
                this.president = newPresident;
                exit = true;
            } else {
                System.out.println("President not found in market! Do you want to create a new one? (Y/N)");
                String response = sc.nextLine().trim();
                if (response.equalsIgnoreCase("y")) {
                    if (this.president != null) {
                        System.out.println("↩Moving current president " + this.president.getName() + " to the market.");
                        MarketManager.addPersonToMarket(market, this.president);
                    }
                    this.president = Person.createPerson(sc);
                    System.out.println("New president added to the team: " + this.president.getName());
                    exit = true;
                } else if (response.equalsIgnoreCase("n")) {
                    System.out.println("Okay, try again.");
                } else {
                    System.out.println("Invalid input! Please enter Y or N.");
                }
            }
        } while (!exit);
    }

    //Setter
    public void setCoach(Coach coach) {
        this.coach = coach;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public void setFoundationYear(int foundationYear) {
        this.foundationYear = foundationYear;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setStadiumName(String stadiumName) {
        this.stadiumName = stadiumName;
    }

    public void setPresident(Person president) {
        this.president = president;
    }

    public void setPlayers(ArrayList<Person> players) {
        this.players = players;
    }

    //Getter
    public Coach getCoach() {
        return coach;
    }

    public String getTeamName() {
        return teamName;
    }

    public int getFoundationYear() {
        return foundationYear;
    }

    public String getCity() {
        return city;
    }

    public String getStadiumName() {
        return stadiumName;
    }

    public Person getPresident() {
        return president;
    }

    public ArrayList<Person> getPlayers() {
        return players;
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Team: ").append(teamName).append("\n");
        sb.append("Founded: ").append(foundationYear).append("\n");
        sb.append("City: ").append(city).append("\n");
        sb.append("Stadium: ").append(stadiumName).append("\n");
        if (president != null) {
            sb.append("President: ").append(president.getName()).append(" ").append(president.getSurname()).append("\n");
        } else {
            sb.append("President: (none)\n");
        }
        if (coach != null) {
            sb.append("Coach: ").append(coach.getName()).append(" ").append(coach.getSurname()).append("\n");
        } else {
            sb.append("Coach: (none)\n");
        }
        sb.append("Players:\n");
        if (players != null && !players.isEmpty()) {
            for (Person p : players) {
                if (p instanceof Player player) {
                    sb.append("   - ").append(player.getName()).append(" ").append(player.getSurname())
                            .append(" | #").append(player.getPlayerNumber())
                            .append(" | Pos: ").append(player.getPosition())
                            .append(" | Quality: ").append(player.getQuality()).append("\n");
                }
            }
        } else {
            sb.append("(no players)\n");
        }
        return sb.toString();
    }
}
