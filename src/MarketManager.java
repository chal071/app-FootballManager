import datoPersona.Coach;
import datoPersona.Person;
import datoPersona.Player;

import java.util.ArrayList;

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

}
