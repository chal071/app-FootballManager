import datoPersona.Coach;
import datoPersona.Person;
import datoPersona.Player;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        String filePath = "src/resource/mercat_fitxatges.txt";
        Scanner sc = new Scanner(System.in);
        ArrayList<Person> market = new ArrayList<>();
        ArrayList<Team> teams = new ArrayList<>();
        loadPersonData(filePath, market);
        loadTeamData(filePath, teams);
        inputMenuOptions(filePath, sc, market, teams);
    }

    public static void showMenu() {
        System.out.println("Welcome to HerGame Manager: \n" +
                "1 - View current league standings \n" +
                "2 - Manage team \n" +
                "3 - Register team \n" +
                "4 - Register new person \n" +
                "5 - Check team data \n" +
                "6 - Check team player data \n" +
                "7 - Play new league \n" +
                "8 - Perform training session \n" +
                "9 - Transfer player \n" +
                "10 - Save data \n" +
                "0 - Exit \n" +
                "You option: ");
    }

    public static void showTeamMenu() {
        System.out.println("Team Manager:\n" +
                "1- Deregister team\n" +
                "2- Modify president\n" +
                "3- Dismiss coach\n" +
                "4- Register player or coach\n" +
                " 0- Exit");
    }

    public static void inputTeamMenu(Scanner sc, ArrayList<Person> market, ArrayList<Team> teams) {
        int option = 1;
        do {
            showTeamMenu();
            try {
                option = sc.nextInt();
                if (option < 0 || option > 4) {
                    System.out.println("Invalid option. Try again.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid option!");
                sc.nextLine();
            }
            teamMenuOptionSwitch(option, sc, market, teams);
        } while (option != 0);
    }

    public static void teamMenuOptionSwitch(int option, Scanner sc, ArrayList<Person> market, ArrayList<Team> teams) {
        switch (option) {
            case 1:
                deregisterTeam(sc, teams);
                break;
            case 2:
                changePresident(sc, market, teams);
                break;
            case 3:
                dismissCoach(sc, market, teams);
                break;
            case 4:
                addPlayerOrCoachToTeam(sc,market,teams);
                break;
        }
    }

    public static void inputMenuOptions(String filePath,Scanner sc, ArrayList<Person> market, ArrayList<Team> teams) {
        int option = 1;
        do {
            showMenu();
            try {
                option = sc.nextInt();
                if (option < 0 || option > 10) {
                    System.out.println("Invalid option. Try again.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid option!");
                sc.nextLine();
            }
            menuOptionsSwitch(filePath ,option, sc, market, teams);
        } while (option != 0);
    }

    public static void menuOptionsSwitch(String filePath,int option, Scanner sc, ArrayList<Person> market, ArrayList<Team> teams) {
        switch (option) {
            case 1:
                break;
            case 2:
                inputTeamMenu(sc, market, teams);
                break;
            case 3:
                registerTeam(sc, market);
                break;
            case 4:
                addPersonToMarket(sc, market);
                break;
            case 5:
                checkTeamData(sc, teams);
                break;
            case 6:
                checkTeamPlayerData(sc, teams);
                break;
            case 7:
                break;
            case 8:
                performTrainingSession(sc, market);
                break;
            case 9:
                break;
            case 10:
                askForWhatTypeOfDataWantToSave(sc, filePath, market, teams);
                break;
        }
    }

    public static void loadPersonData(String filePath, ArrayList<Person> market) {
        FileManager.loadMarket(filePath, market);
    }

    public static void loadTeamData(String filePath, ArrayList<Team> teams) {
        teams = FileManager.loadTeamFromFile(filePath);
    }

    public static void askForWhatTypeOfDataWantToSave(Scanner sc, String filePath, ArrayList<Person> market, ArrayList<Team> teams) {
        int option;
        boolean exit = false;
        System.out.println("Save Data: \n" +
                "1. Team data \n" +
                "2. Market data\n" +
                "3. Exit\n" +
                "Enter option: ");
        do {
            option = sc.nextInt();
            if (option < 1 || option > 4) {
                System.out.println("Invalid option. Try again.");
            } else if (option == 1) {
                saveTeamData(filePath, teams);
                exit = true;
            } else if (option == 2) {
                savePersonData(filePath, market);
                exit = true;
            } else {
                exit = true;
            }
        }while (!exit);

    }

    public static void savePersonData(String filePath, ArrayList<Person> market) {
        FileManager.savePersonOfMarketToFile(market, filePath);
    }

    public static void saveTeamData(String filePath, ArrayList<Team> teams) {
        FileManager.saveTeamToFile(filePath, teams);
    }

    public static void addPersonToMarket(Scanner sc, ArrayList<Person> market) {
        boolean exit = false;
        System.out.println("Register player or coach: \n" +
                "1. Player\n" +
                "2. Coach\n" +
                "3. President\n" +
                "4. Exit");
        do {
            int option = sc.nextInt();
            if (option == 1) {
                Person.addPersonToMarket(market, Player.createPlayer(sc));
                exit = true;
            }
            if (option == 2) {
                Person.addPersonToMarket(market, Coach.createCoach(sc));
                exit = true;
            } else if (option == 3) {
                Person.addPersonToMarket(market, Person.createPerson(sc));
                exit = true;
            } else if (option == 4) {
                exit = true;
            } else {
                System.out.println("Invalid option. Try again.");
            }
        } while (!exit);
    }

    public static void addPlayerOrCoachToTeam (Scanner sc, ArrayList<Person> market, ArrayList<Team> teams) {
        boolean exit = false;
        Team team = Team.searchTeamInTeamList(sc, teams);
        if (team != null) {
            System.out.println("Register player or coach: \n" +
                    "1. Player\n" +
                    "2. Coach\n" +
                    "3. Exit");
            do {
                int option = sc.nextInt();
                if (option == 1) {
                    team.addPlayerToTeam(sc, market);
                    exit = true;
                }
                if (option == 2) {
                    team.changeCoach(market, sc);
                    exit = true;
                } else if (option == 3) {
                    exit = true;
                } else if (option < 1 || option > 3) {
                    System.out.println("Invalid option. Try again.");
                }
            } while (!exit);
        } else {
            System.out.println("Invalid team. Try again.");
        }

    }

    public static void dismissCoach (Scanner sc, ArrayList<Person> market, ArrayList<Team> teams) {
        Team team = Team.searchTeamInTeamList(sc, teams);
        if (team != null) {
            Person.addPersonToMarket(market, team.getCoach());
            team.setCoach(null);
        } else {
            System.out.println("Invalid team. Try again.");
        }
    }

    public static void deregisterTeam(Scanner sc, ArrayList<Team> teams) {
        System.out.println("Input the team's name you want to deregister: ");
        Team team = Team.searchTeamInTeamList(sc, teams);
        teams.remove(team);
    }

    public static void changePresident(Scanner sc, ArrayList<Person> market, ArrayList<Team> teams) {
        Team team = Team.searchTeamInTeamList(sc, teams);
        if (team != null) {
            team.changePresident(market, sc);
        } else {
            System.out.println("Invalid team. Try again.");
        }
    }

    public static void registerTeam(Scanner sc, ArrayList<Person> market){
        Team.createTeam(sc, market);
    }

    public static void performTrainingSession(Scanner sc, ArrayList<Person> market) {
        System.out.println("Input the person's name that you want to train: ");
        String personName = sc.nextLine();
        System.out.println("Input the person's surname that you want to train: ");
        String personSurname = sc.nextLine();
        boolean find = Person.searchPersonInMarket(market, personName, personSurname);
        if (find) {
            Person p =Person.loadSinglePersonData(market, personName, personSurname);
            p.training();
        } else {
            System.out.println("Invalid person. Try again.");
        }

    }

    public static void checkTeamData(Scanner sc, ArrayList<Team> team) {
        System.out.println("Input the team's name that you want to check: ");
        String teamName = sc.nextLine();
        Team t = Team.searchTeamInTeamList(sc, team);
        System.out.println(t);
    }

    public static void checkTeamPlayerData(Scanner sc, ArrayList<Team> team) {
        System.out.println("Input the team's name that you want to check: ");
        String teamName = sc.nextLine();
        Team t = Team.searchTeamInTeamList(sc, team);
        System.out.println("Input the player name that you want to check: ");
        String playerName = sc.nextLine();
        System.out.println("Input the player surname that you want to check: ");
        String playerSurname = sc.nextLine();
        boolean found = false;
        for (Person p : t.getPlayers()) {
            if (p instanceof Player player &&
                    player.getName().equalsIgnoreCase(playerName) &&
                    player.getSurname().equalsIgnoreCase(playerSurname)) {
                System.out.println("Player found!\n" + player);
                found = true;
                break;
            }
        }if (!found) {
            System.out.println("Player not found in this team!");
        }
    }

    public static void transferPlayer(Scanner sc, ArrayList<Person> market, ArrayList<Team> teams) {

    }
}