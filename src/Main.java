import datoPersona.Coach;
import datoPersona.Person;
import datoPersona.Player;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        String filePath = "src/resource/mercat_fitxatges.txt";
        String filePathTeam = "src/resource/dato_equip.txt";
        Scanner sc = new Scanner(System.in);
        ArrayList<Person> market = new ArrayList<>();
        ArrayList<Team> teams = loadTeamData(filePathTeam);
        League league = new League();
        loadPersonData(filePath, market);
        inputMenuOptions(filePath, filePathTeam, sc, market, teams, league);
    }

    //MAIN MENU
    public static void showMenu() {
        System.out.println("\n👑 Welcome to HerGame Manager 👑");
        System.out.println("=============================");
        System.out.println("1️⃣  View current league standings 🏆");
        System.out.println("2️⃣  Manage a team ⚙️");
        System.out.println("3️⃣  Register a new team 📝");
        System.out.println("4️⃣  Register new person (player/coach/president) 👤");
        System.out.println("5️⃣  Check team data 📊");
        System.out.println("6️⃣  Check team player data 🔍");
        System.out.println("7️⃣  Start a new league ⚽");
        System.out.println("8️⃣  Perform training session 🏋️‍♀️");
        System.out.println("9️⃣  Transfer player 🔁");
        System.out.println("🔟  Save data 💾");
        System.out.println("0️⃣  Exit 🛑");
        System.out.print("\n👉 Choose your option: ");
    }

    public static void inputMenuOptions(String filePath, String filePathTeam, Scanner sc, ArrayList<Person> market, ArrayList<Team> teams, League league) {
        int option = 1;
        do {
            showMenu();
            try {
                option = sc.nextInt();
                sc.nextLine();
                if (option < 0 || option > 10) {
                    System.out.println("Invalid option. Try again.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid option!");
            }
            menuOptionsSwitch(filePath, filePathTeam, option, sc, market, teams, league);
        } while (option != 0);
    }

    public static void menuOptionsSwitch(String filePath, String filePathTeam, int option, Scanner sc, ArrayList<Person> market, ArrayList<Team> teams, League league) {
        switch (option) {
            case 1:
                inputClassificationMenu(sc, league);
                System.out.println("-------------------------------");
                break;
            case 2:
                inputTeamMenu(sc, market, teams);
                System.out.println("-------------------------------");
                break;
            case 3:
                registerTeam(sc, market, teams);
                System.out.println("-------------------------------");
                break;
            case 4:
                addPersonToMarket(sc, market);
                System.out.println("-------------------------------");
                break;
            case 5:
                checkTeamData(sc, teams);
                System.out.println("-------------------------------");
                break;
            case 6:
                checkTeamPlayerData(sc, teams);
                System.out.println("-------------------------------");
                break;
            case 7:
                inputLeagueMenu(sc, league, teams);
                System.out.println("-------------------------------");
                break;
            case 8:
                performTrainingSession(sc, market);
                System.out.println("-------------------------------");
                break;
            case 9:
                MarketManager.transferPlayer(sc, teams);
                System.out.println("-------------------------------");
                break;
            case 10:
                askForWhatTypeOfDataWantToSave(sc, filePath, filePathTeam, market, teams);
                System.out.println("-------------------------------");
                break;
            case 0:
                System.out.println("Exiting.....");
                break;
            default:
                System.out.println("Invalid option. Try again.");
                break;
        }
    }

    //TEAM MENU
    public static void showTeamMenu(ArrayList<Team> teams) {
        System.out.println("📋 Available Teams:");
        for (Team t : teams) {
            System.out.println("🔹 " + t.getTeamName());
        }
        System.out.println("------------------------------------------------");
        System.out.println("\n🛠️ Team Manager Menu:");
        System.out.println("1️⃣  Deregister team ❌");
        System.out.println("2️⃣  Modify president 👑");
        System.out.println("3️⃣  Dismiss coach 📤");
        System.out.println("4️⃣  Add player or coach ➕");
        System.out.println("0️⃣  Return to main menu 🔙");
        System.out.print("Select option: ");
    }

    public static void inputTeamMenu(Scanner sc, ArrayList<Person> market, ArrayList<Team> teams) {
        int option = 1;
        do {
            showTeamMenu(teams);
            try {
                option = sc.nextInt();
                sc.nextLine();
                if (option < 0 || option > 4) {
                    System.out.println("Invalid option. Try again.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid option!");
            }
            teamMenuOptionSwitch(option, sc, market, teams);
        } while (option != 0);
    }

    public static void teamMenuOptionSwitch(int option, Scanner sc, ArrayList<Person> market, ArrayList<Team> teams) {
        switch (option) {
            case 1:
                deregisterTeam(sc, teams);
                System.out.println("-------------------------------");
                break;
            case 2:
                changePresident(sc, market, teams);
                System.out.println("-------------------------------");
                break;
            case 3:
                dismissCoach(sc, market, teams);
                System.out.println("-------------------------------");
                break;
            case 4:
                addPlayerOrCoachToTeam(sc, market, teams);
                System.out.println("-------------------------------");
                break;
            default:
                System.out.println("Invalid option. Try again.");
                break;
        }
    }

    //SAVE DATA
    public static void askForWhatTypeOfDataWantToSave(Scanner sc, String filePath, String filePathTeam, ArrayList<Person> market, ArrayList<Team> teams) {
        int option;
        boolean exit = false;
        System.out.println("Save Data: \n" +
                "1. Team data \n" +
                "2. Market data\n" +
                "3. Exit\n" +
                "Enter option: ");
        do {
            option = sc.nextInt();
            sc.nextLine();
            if (option < 1 || option > 3) {
                System.out.println("Invalid option. Try again.");
            } else if (option == 1) {
                saveTeamData(filePathTeam, teams);
                exit = true;
            } else if (option == 2) {
                savePersonData(filePath, market);
                exit = true;
            } else {
                exit = true;
            }
        } while (!exit);

    }

    public static void savePersonData(String filePath, ArrayList<Person> market) {
        FileManager.savePersonOfMarketToFile(market, filePath);
    }

    public static void saveTeamData(String filePath, ArrayList<Team> teams) {
        System.out.println("📋 Teams to save: " + teams.size());
        FileManager.saveTeamToFile(filePath, teams);
    }

    //LOAD DATA
    public static void loadPersonData(String filePath, ArrayList<Person> market) {
        FileManager.loadMarket(filePath, market);
    }

    public static ArrayList<Team> loadTeamData(String filePath) {
        return FileManager.loadTeamFromFile(filePath);
    }

    //TEAM MANAGER
    public static void addPersonToMarket(Scanner sc, ArrayList<Person> market) {
        boolean exit = false;
        System.out.println("Register player or coach: \n" +
                "1. Player\n" +
                "2. Coach\n" +
                "3. President\n" +
                "4. Exit");
        do {
            int option = sc.nextInt();
            sc.nextLine();
            if (option == 1) {
                MarketManager.addPersonToMarket(market, Player.createPlayer(sc));
                System.out.println("Player added successfully!");
                exit = true;
            }
            if (option == 2) {
                MarketManager.addPersonToMarket(market, Coach.createCoach(sc));
                System.out.println("Coach added successfully!");
                exit = true;
            } else if (option == 3) {
                MarketManager.addPersonToMarket(market, Person.createPerson(sc));
                System.out.println("President added successfully!");
                exit = true;
            } else if (option == 4) {
                exit = true;
            } else if (option < 0 || option > 4) {
                System.out.println("Invalid option. Try again.");
            }
        } while (!exit);
    }

    public static void addPlayerOrCoachToTeam(Scanner sc, ArrayList<Person> market, ArrayList<Team> teams) {
        boolean exit = false;
        Team team = Team.searchTeamInTeamList(sc, teams);
        if (team != null) {
            System.out.println("Register player or coach: \n" +
                    "1. Player\n" +
                    "2. Coach\n" +
                    "3. Exit");
            do {
                int option = sc.nextInt();
                sc.nextLine();
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

    public static void dismissCoach(Scanner sc, ArrayList<Person> market, ArrayList<Team> teams) {
        Team team = Team.searchTeamInTeamList(sc, teams);
        if (team != null) {
            MarketManager.addPersonToMarket(market, team.getCoach());
            team.setCoach(null);
            System.out.println("🔁 Coach dismissed.");
        } else {
            System.out.println("Invalid team. Try again.");
        }
    }

    public static void deregisterTeam(Scanner sc, ArrayList<Team> teams) {
        Team team = Team.searchTeamInTeamList(sc, teams);
        teams.remove(team);
        System.out.println("Team deregister successful!");
    }

    public static void changePresident(Scanner sc, ArrayList<Person> market, ArrayList<Team> teams) {
        Team team = Team.searchTeamInTeamList(sc, teams);
        if (team != null) {
            team.changePresident(market, sc);
            System.out.println("✅ President updated successfully!");
        } else {
            System.out.println("Invalid team. Try again.");
        }
    }

    public static void registerTeam(Scanner sc, ArrayList<Person> market, ArrayList<Team> teams) {
        Team t = Team.createTeam(sc, market);
        teams.add(t);
    }

    //Training
    public static void performTrainingSession(Scanner sc, ArrayList<Person> market) {
        MarketManager.showMarketSummary(market);
        System.out.println("Input the person's name that you want to train: ");
        String personName = sc.nextLine();
        System.out.println("Input the person's surname that you want to train: ");
        String personSurname = sc.nextLine();
        boolean find = MarketManager.searchPersonInMarket(market, personName, personSurname);
        if (find) {
            Person p = MarketManager.loadSinglePersonData(market, personName, personSurname);
            assert p != null;
            p.training();
        } else {
            System.out.println("Invalid person. Try again.");
        }

    }

    //CHECK DATA
    public static void checkTeamData(Scanner sc, ArrayList<Team> teams) {
        System.out.println("📝 Available teams:");
        for (Team team : teams) {
            System.out.println("- " + team.getTeamName());
        }
        Team t = Team.searchTeamInTeamList(sc, teams);
        if (t != null) {
            System.out.println("Team found!\n" + t);
        } else {
            System.out.println("Team not found. Please try again.");
        }
    }

    public static void checkTeamPlayerData(Scanner sc, ArrayList<Team> teams) {
        System.out.println("📝 Available teams:");
        for (Team team : teams) {
            System.out.println("- " + team.getTeamName());
        }
        Team t = Team.searchTeamInTeamList(sc, teams);
        if (t == null) {
            System.out.println("❌ Team not found.");
            return;
        }
        System.out.println("👥 Players in " + t.getTeamName() + ":");
        for (Person p : t.getPlayers()) {
            if (p instanceof Player player) {
                System.out.println("- " + player.getName() + " " + player.getSurname() + " (Dorsal: " + player.getPlayerNumber() + ")");
            }
        }
        System.out.println("Input the player name you want to check: ");
        String playerName = sc.nextLine();
        System.out.println("Input the player surname: ");
        String playerSurname = sc.nextLine();
        System.out.println("Input the player number: ");
        int number = sc.nextInt();
        sc.nextLine();
        Player searchPlayer = new Player(playerName, playerSurname, null, 6, 0, number, "POR", 30);
        for (Person p : t.getPlayers()) {
            if (p instanceof Player player && player.equals(searchPlayer)) {
                System.out.println("✅ Player found!\n" + player);
                return;
            }
        }

        System.out.println("❌ Player not found in this team!");
    }

    //LEAGUE MENU
    public static void showLeagueMenu() {
        System.out.println("\n🎮 League Menu:");
        System.out.println("1️⃣  Start new league");
        System.out.println("2️⃣  View current league teams");
        System.out.println("3️⃣  Add team to league");
        System.out.println("4️⃣  Reset league (clear all teams)");
        System.out.println("0️⃣  Return to main menu");
        System.out.print("Select option: ");
    }

    public static void inputLeagueMenu(Scanner sc, League league, ArrayList<Team> teams) {
        int option = 1;
        do {
            showLeagueMenu();
            try {
                option = sc.nextInt();
                sc.nextLine();
                if (option < 0 || option > 4) {
                    System.out.println("❌ Invalid option. Try again.");
                }
            } catch (InputMismatchException e) {
                System.out.println("❌ Invalid input!");
                sc.nextLine();
            }
            leagueMenuSwitch(option, sc, league, teams);
        } while (option != 0);
    }

    public static void leagueMenuSwitch(int option, Scanner sc, League league, ArrayList<Team> teams) {
        switch (option) {
            case 1:
                startLeague(league);
                System.out.println("✅ League finished!");
                System.out.println("-------------------------------");
                break;
            case 2:
                league.showTeam();
                break;
            case 3:
                createTeamListToLeague(sc, league, teams);
                System.out.println("-------------------------------");
                break;
            case 4:
                league.reset();
                System.out.println("🔄 League has been reset.");
                System.out.println("-------------------------------");
                break;
            case 0:
                System.out.println("🔙 Returning to main menu...");
                break;
            default:
                System.out.println("❌ Unknown option.");
        }
    }

    //LEAGUE
    public static boolean addTeamToLeague(Scanner sc, ArrayList<Team> teams, League league) {
        Team t = Team.searchTeamInTeamList(sc, teams);
        if (t == null) {
            System.out.println("⏹️ Cancelled or team not found.");
            return false;
        }
        league.addTeam(t);
        return true;
    }


    public static void createTeamListToLeague(Scanner sc, League league, ArrayList<Team> teams) {
        System.out.println("How many do you want to add to the league: ");
        int teamNumber = sc.nextInt();
        sc.nextLine();

        System.out.println("\n📋 Available Teams:");
        for (Team t : teams) {
            if (!league.getTeams().contains(t)) {
                System.out.println("- " + t.getTeamName());
            }
        }


        for (int i = 0; i < teamNumber; i++) {
            boolean success = addTeamToLeague(sc, teams, league);
            if (!success) {
                System.out.println("❗Team not added.");
                System.out.print("Do you want to continue? (y/n): ");
                String ans = sc.nextLine();
                if (ans.equalsIgnoreCase("n")) {
                    System.out.println("🚪 Team input cancelled.");
                    break;
                }
                i--;
            }
        }
    }


    public static void startLeague(League league) {
        if (league.getTeams().size() < 2) {
            System.out.println("❌ You need at least 2 teams to start the league!");
            return;
        }
        league.createAllMatches();
        league.playAllMatches();
    }

    //Classification MENU
    public static void showClassificationMenu() {
        System.out.println("League classification:\n" +
                "1️⃣  Classification\n" +
                "2️⃣  Top Scorers\n" +
                "3️⃣  Show all matches\n" +
                "0️⃣  Back to main menu"
        );
        System.out.print("Select option: ");

    }

    public static void inputClassificationMenu(Scanner sc, League league) {
        int option = 1;
        do {
            showClassificationMenu();
            try {
                option = sc.nextInt();
                sc.nextLine();
                if (option < 0 || option > 3) {
                    System.out.println("❌ Invalid option. Try again.");
                    continue;
                }
                classificationMenuSwitch(option, league);
            } catch (InputMismatchException e) {
                System.out.println("❌ Invalid input!");
                sc.nextLine();
            }
        } while (option != 0);
    }

    public static void classificationMenuSwitch(int option, League league) {
        switch (option) {
            case 1:
                league.showRanking();
                System.out.println("-------------------------------");
                break;
            case 2:
                league.showTopScorers();
                System.out.println("-------------------------------");
                break;
            case 3:
                for (Match match : league.getMatches()) {
                    System.out.println(match);
                }
                System.out.println("-------------------------------");
                break;
            case 0:
                System.out.println("🔙 Returning to main menu...");
                break;
            default:
                System.out.println("❌ Unknown option.");
        }
    }

}