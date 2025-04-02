import java.util.*;
import datoPersona.Player;

public class League {
    private ArrayList<Team> teams; // 所有参赛队伍
    private ArrayList<Match> matches; // 所有比赛记录
    private Map<Team, Integer> points; // 每支队的积分
    private Map<Player, Integer> goalScorers; // 每位球员的进球数

    public League(ArrayList<Team> teams) {
        this.teams = teams;
        this.matches = new ArrayList<>();
        this.points = new HashMap<>();
        this.goalScorers = new HashMap<>();

        for (Team team : teams) {
            points.put(team, 0); // 初始积分为 0
        }
    }

    public void addTeam(Team team) {
        this.teams.add(team);
    }

    public void createAllMatches() {
        for (int i = 0; i < teams.size(); i++) {
            for (int j = i + 1; j < teams.size(); j++) {
                Match m = new Match(teams.get(i), teams.get(j));
                matches.add(m);
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
        System.out.println("\nTeam: ");
        for (Team t : this.teams) {
            System.out.println(t);
        }
    }

    public ArrayList<Match> getMatches() {
        return matches;
    }

    public Map<Team, Integer> getPointsTable() {
        return points;
    }

    public Map<Player, Integer> getGoalScorers() {
        return goalScorers;
    }
}
