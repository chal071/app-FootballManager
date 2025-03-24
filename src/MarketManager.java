import datoPersona.Coach;
import datoPersona.Person;
import datoPersona.Player;

import java.util.ArrayList;
import java.util.Scanner;

public class MarketManager {
    public static void removePersonFromMarket(ArrayList<Person> market, String name, String surname) {
        int indexToRemove = -1;
        for (int i = 0; i < market.size(); i++) {
            Person p = market.get(i);
            if (p.getName().equalsIgnoreCase(name) && p.getSurname().equalsIgnoreCase(surname)) {
                indexToRemove = i;
                break;
            }
        }
        if (indexToRemove != -1) {
            market.remove(indexToRemove);
        }
    }

    public static void addPersonToMarket(ArrayList<Person> market, Person person) {
        market.add(person);
    }

    public static boolean searchPersonInMarket(ArrayList<Person> market, String personName, String personSurname) {
        for (Person Person : market) {
            if(Person.getName().equals(personName) && Person.getSurname().equals(personSurname)) {
                return true;
            }
        }
        return false;
    }

    public static Person loadSinglePersonData(ArrayList<Person> market, String personName, String personSurname) {
        for (Person person : market) {
            if (person.getName().equalsIgnoreCase(personName) && person.getSurname().equalsIgnoreCase(personSurname)) {
                return person;
            }
        }
        return null;
    }

    public static void transferPlayer(Scanner sc, ArrayList<Team> teams) {
        System.out.print("Player's current team? ");
        Team originTeam = Team.searchTeamInTeamList(sc, teams);
        if (originTeam == null) return;

        System.out.print("Player's new team? ");
        Team newTeam = Team.searchTeamInTeamList(sc, teams);
        if (newTeam == null) return;

        System.out.print("Player name: ");
        String playerName = sc.nextLine();
        System.out.print("Player surname: ");
        String playerSurname = sc.nextLine();

        Player target = null;
        for (Person p : originTeam.getPlayers()) {
            if (p instanceof Player player &&
                    player.getName().equalsIgnoreCase(playerName) &&
                    player.getSurname().equalsIgnoreCase(playerSurname)) {
                target = player;
                break;
            }
        }
        if (target == null) {
            System.out.println("❌ Player not found in " + originTeam.getTeamName());
            return;
        }
        if (Team.isPlayerNumberTaken(newTeam, target.getPlayerNumber())) {
            System.out.println("⚠️ Number " + target.getPlayerNumber() + " is already taken in " + newTeam.getTeamName());
            int newNumber;
            do {
                System.out.print("Enter a new available number: ");
                newNumber = Integer.parseInt(sc.nextLine());
            } while (Team.isPlayerNumberTaken(newTeam, newNumber));
            target.setPlayerNumber(newNumber);
        }

        originTeam.getPlayers().remove(target);
        newTeam.getPlayers().add(target);
        System.out.println("✅ " + playerName + " " + playerSurname + " has been transferred from " +
                originTeam.getTeamName() + " to " + newTeam.getTeamName() + "!");
    }


}
