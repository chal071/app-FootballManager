import java.util.*;
import datoPersona.Player;

public class League {
    private Set<Team> teams;
    private ArrayList<Match> matches;
    private Map<Team, Integer> points;
    private Map<Player, Integer> goalScorers;

    public League() {
        this.teams = new HashSet<>();
        this.matches = new ArrayList<>();
        this.points = new HashMap<>();
        this.goalScorers = new HashMap<>();
    }

    public void addTeam(Team team) {
        if (team == null) {
            System.out.println("❌ Invalid team (null). Cannot add to league.");
            return;
        }
        if (teams.add(team)) {
            points.put(team, 0);
            System.out.println("✅ Team added: " + team.getTeamName());
        } else {
            System.out.println("⚠️ Team already in the league: " + team.getTeamName());
        }
    }

    public void createAllMatches() {
        List<Team> teamList = new ArrayList<>(teams);
        for (int i = 0; i < teamList.size(); i++) {
            for (int j = 0; j < teamList.size(); j++) {
                if (i != j) {
                    matches.add(new Match(teamList.get(i), teamList.get(j)));
                }
            }
        }
    }

    public void playAllMatches() {
        for (Match m : matches) {
            m.playMatch();
            updatePoints(m);
            registerGoals(m);
        }
    }

    private void updatePoints(Match m) {
        if (m.getGoalsHome() > m.getGoalsAway()) {
            points.put(m.getTeamHome(), points.get(m.getTeamHome()) + 3);
        } else if (m.getGoalsHome() < m.getGoalsAway()) {
            points.put(m.getTeamAway(), points.get(m.getTeamAway()) + 3);
        } else {
            points.put(m.getTeamHome(), points.get(m.getTeamHome()) + 1);
            points.put(m.getTeamAway(), points.get(m.getTeamAway()) + 1);
        }
    }

    private void registerGoals(Match m) {
        if (m.getGoalHomePlayer() != null) {
            goalScorers.put(m.getGoalHomePlayer(), goalScorers.getOrDefault(m.getGoalHomePlayer(), 0) + 1);
        }
        if (m.getGoalAwayPlayer() != null) {
            goalScorers.put(m.getGoalAwayPlayer(), goalScorers.getOrDefault(m.getGoalAwayPlayer(), 0) + 1);
        }
    }

    public void showRanking() {
        System.out.println("\nLiga Ranking: ");
        List<Map.Entry<Team, Integer>> sorted = new ArrayList<>(points.entrySet());
        sorted.sort((a, b) -> b.getValue().compareTo(a.getValue()));

        for (int i = 0; i < sorted.size(); i++) {
            Team t = sorted.get(i).getKey();
            int pts = sorted.get(i).getValue();
            System.out.println((i + 1) + ". " + t.getTeamName() + " - " + pts + " pts");
        }
    }


    public void showTopScorers() {
        System.out.println("\nTop Scorers:");
        List<Map.Entry<Player, Integer>> sorted = new ArrayList<>(goalScorers.entrySet());
        sorted.sort((a, b) -> b.getValue().compareTo(a.getValue()));

        for (Map.Entry<Player, Integer> entry : sorted) {
            Player p = entry.getKey();
            int goals = entry.getValue();
            System.out.println(p.getName() + " " + p.getSurname() + " - " + goals + " goals");
        }
    }

    public void showTeam() {
        System.out.println("\n📋 Teams in league:");
        List<Team> sortedTeams = new ArrayList<>(teams);
        sortedTeams.sort(Comparator.comparing(Team::getTeamName));
        for (Team t : sortedTeams) {
            System.out.println("- " + t.getTeamName());
        }
    }


    public void reset() {
        teams.clear();
        matches.clear();
        points.clear();
        goalScorers.clear();
    }

    public ArrayList<Match> getMatches() {
        return matches;
    }

    public Map<Team, Integer> getPointsTable() {
        return points;
    }

    public Set<Team> getTeams() {
        return teams;
    }
}