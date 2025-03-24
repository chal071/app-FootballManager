import java.io.*;
import java.util.*;

import datoPersona.Coach;
import datoPersona.Player;

import java.util.ArrayList;

import datoPersona.Person;

public class FileManager {
    public static void loadMarket(String filePath, ArrayList<Person> market) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.startsWith("#") || line.trim().isEmpty()) continue;
                String[] parts = line.split(";");
                String type = parts[0].trim();
                String name = parts[1].trim();
                String surname = parts[2].trim();
                String birthday = parts[3].trim();
                double motivationLevel = Double.parseDouble(parts[4].trim());
                double annualSalary = Double.parseDouble(parts[5].trim());

                if (type.equalsIgnoreCase("J")) {
                    int playerNumber = Integer.parseInt(parts[6].trim());
                    String position = parts[7].trim();
                    double quality = Double.parseDouble(parts[8].trim());

                    market.add(new Player(name, surname, birthday, motivationLevel, annualSalary, playerNumber, position, quality));

                } else if (type.equalsIgnoreCase("E")) {
                    int numberOfWon = Integer.parseInt(parts[6].trim());
                    boolean isNationalTeamCoach = Boolean.parseBoolean(parts[7].trim());

                    market.add(new Coach(name, surname, birthday, motivationLevel, annualSalary, numberOfWon, isNationalTeamCoach));
                } else if (type.equalsIgnoreCase("P")) {
                    market.add(new Person(name, surname, birthday, motivationLevel, annualSalary));
                }
            }
        } catch (IOException e) {
            System.out.println("Fail to load file: " + filePath);
        }
    }

    public static ArrayList<Team> loadTeamFromFile(String filePath) {
        ArrayList<Team> teams = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            Team currentTeam = null;
            Coach currentCoach = null;
            Person currentPresident = null;
            ArrayList<Person> currentPlayers = new ArrayList<>();

            while ((line = reader.readLine()) != null) {
                if (line.equals("#Team")) {
                    String[] teamData = reader.readLine().split(";");
                    String teamName = teamData[0];
                    int foundationYear = Integer.parseInt(teamData[1]);
                    String city = teamData[2];
                    String stadium = teamData[3];
                    currentTeam = new Team(teamName, foundationYear, city, stadium);
                }

                else if (line.equals("#President")) {
                    String[] presData = reader.readLine().split(";");
                    String name = presData[1];
                    String surname = presData[2];
                    String birth = presData[3];
                    int motivation = Integer.parseInt(presData[4]);
                    double salary = Double.parseDouble(presData[5]);
                    currentPresident = new Person(name, surname, birth, motivation, salary) {};
                }

                else if (line.equals("#Coach")) {
                    String[] coachData = reader.readLine().split(";");
                    String name = coachData[1];
                    String surname = coachData[2];
                    String birth = coachData[3];
                    int motivation = Integer.parseInt(coachData[4]);
                    double salary = Double.parseDouble(coachData[5]);
                    int wins = Integer.parseInt(coachData[6]);
                    currentCoach = new Coach(name, surname, birth, motivation, salary, wins, false);
                }

                else if (line.equals("#Players")) {
                    currentPlayers = new ArrayList<>();
                    while ((line = reader.readLine()) != null && !line.equals("---")) {
                        String[] playerData = line.split(";");
                        if (playerData[0].equals("J")) {
                            String name = playerData[1];
                            String surname = playerData[2];
                            String birth = playerData[3];
                            int motivation = Integer.parseInt(playerData[4]);
                            double salary = Double.parseDouble(playerData[5]);
                            int number = Integer.parseInt(playerData[6]);
                            String position = playerData[7];
                            int quality = Integer.parseInt(playerData[8]);
                            Player p = new Player(name, surname, birth, motivation, salary, number, position, quality);
                            currentPlayers.add(p);
                        }
                    }
                    if (currentTeam != null) {
                        currentTeam.setCoach(currentCoach);
                        currentTeam.setPresident(currentPresident);
                        currentTeam.setPlayers(currentPlayers);
                        teams.add(currentTeam);
                    }
                }
            }

            System.out.println("Loaded " + teams.size() + " teams");
        } catch (IOException e) {
            System.out.println("Fail to load file: " + filePath);
        }

        return teams;
    }

    public static void savePersonOfMarketToFile(ArrayList<Person> market, String filePath) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (Person p : market) {
                if (p instanceof Player player) {
                    writer.write("J;" + player.getName() + ";" + player.getSurname() + ";" + player.getBirthDate() + ";" +
                            player.getMotivationLevel() + ";" + player.getAnnualSalary() + ";" + player.getPlayerNumber() + ";" +
                            player.getPosition() + ";" + player.getQuality());
                } else if (p instanceof Coach coach) {
                    writer.write("E;" + coach.getName() + ";" + coach.getSurname() + ";" + coach.getBirthDate() + ";" +
                            coach.getMotivationLevel() + ";" + coach.getAnnualSalary() + ";" + coach.getNumberOfWon() + ";"
                            + coach.getIsNationalTeamCoach());
                } else {
                    writer.write("P;" + p.getName() + ";" + p.getSurname() + ";" + p.getBirthDate() + ";" +
                            p.getMotivationLevel() + ";" + p.getAnnualSalary());
                }
                writer.newLine();
            }
            System.out.println("Save data to " + filePath);
        } catch (IOException e) {
            System.out.println("Fail to save date to：" + e.getMessage());
        }
    }

    public static void saveTeamToFile(String filePath, ArrayList<Team> teams) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(filePath));
            for (Team t : teams) {
                writer.write("#Team");
                writer.newLine();
                writer.write(t.getTeamName() + ";" + t.getFoundationYear() + ";" + t.getCity() + ";" + t.getStadiumName());
                writer.newLine();

                if (t.getPresident() != null) {
                    writer.write("#President");
                    writer.newLine();
                    Person p = t.getPresident();
                    writer.write("P;" + p.getName() + ";" + p.getSurname() + ";" + p.getBirthDate() +
                            ";" + p.getMotivationLevel() + ";" + p.getAnnualSalary());
                    writer.newLine();
                }

                if (t.getCoach() != null) {
                    writer.write("#Coach");
                    writer.newLine();
                    Coach c = t.getCoach();
                    writer.write("E;" + c.getName() + ";" + c.getSurname() + ";" + c.getBirthDate() +
                            ";" + c.getMotivationLevel() + ";" + c.getAnnualSalary() + ";" + c.getNumberOfWon());
                    writer.newLine();
                }

                if (t.getPlayers() != null) {
                    writer.write("#Players");
                    writer.newLine();
                    for (Person player : t.getPlayers()) {
                        if (player instanceof Player p) {
                            writer.write("J;" + p.getName() + ";" + p.getSurname() + ";" + p.getBirthDate() + ";" +
                                    p.getMotivationLevel() + ";" + p.getAnnualSalary() + ";" + p.getPlayerNumber() +
                                    ";" + p.getPosition() + ";" + p.getQuality());
                            writer.newLine();
                        }
                    }
                }
                writer.write("---");
                writer.newLine();
            }
            System.out.println("Save data to " + filePath);
        } catch (IOException e) {
            System.out.println("Fail to save data: " + filePath);
        }
    }
}

