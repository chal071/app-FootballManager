import datoPersona.Player;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Random;

public class Match {
    private Team teamHome;
    private Team teamAway;
    private int goalsHome;
    private int goalsAway;
    private Player goalAwayPlayer;
    private Player goalHomePlayer;
    private boolean isPlayed;

    public Match(Team home, Team away) {
        this.teamHome = home;
        this.teamAway = away;
    }


    public void playMatch() {
        double homeQuality = teamHome.calcularTeamQuality();
        double awayQuality = teamAway.calcularTeamQuality();
        double homeMotivation = teamHome.getAverageMotivation();
        double awayMotivation = teamAway.getAverageMotivation();
        double homeScoreFactor = homeQuality * 0.6 + homeMotivation * 0.4 + 5;
        double awayScoreFactor = awayQuality * 0.6 + awayMotivation * 0.4;
        Random rand = new Random();
        this.goalsHome = rand.nextInt((int) (homeScoreFactor / 20) + 1);
        this.goalsAway = rand.nextInt((int) (awayScoreFactor / 20) + 1);
        if (goalsHome > 0) {
            ArrayList<Player> homePlayers = teamHome.getOnlyPlayers();
            this.goalHomePlayer = homePlayers.get(rand.nextInt(homePlayers.size()));
        }
        if (goalsAway > 0) {
            ArrayList<Player> awayPlayers = teamAway.getOnlyPlayers();
            this.goalAwayPlayer = awayPlayers.get(rand.nextInt(awayPlayers.size()));
        }
        this.isPlayed = true;
    }

    //Getter


    public Team getTeamHome() {
        return teamHome;
    }

    public Team getTeamAway() {
        return teamAway;
    }

    public int getGoalsHome() {
        return goalsHome;
    }

    public int getGoalsAway() {
        return goalsAway;
    }

    public Player getGoalAwayPlayer() {
        return goalAwayPlayer;
    }

    public Player getGoalHomePlayer() {
        return goalHomePlayer;
    }

    public boolean isPlayed() {
        return isPlayed;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("🏟️  ")
                .append(teamHome.getTeamName())
                .append(" ")
                .append(goalsHome)
                .append(" - ")
                .append(goalsAway)
                .append(" ")
                .append(teamAway.getTeamName())
                .append("\n");

        if (goalHomePlayer != null) {
            sb.append("   ⚽ Goal (Home): ")
                    .append(goalHomePlayer.getName()).append(" ")
                    .append(goalHomePlayer.getSurname()).append("\n");
        }

        if (goalAwayPlayer != null) {
            sb.append("   ⚽ Goal (Away): ")
                    .append(goalAwayPlayer.getName()).append(" ")
                    .append(goalAwayPlayer.getSurname()).append("\n");
        }

        return sb.toString();
    }

}
