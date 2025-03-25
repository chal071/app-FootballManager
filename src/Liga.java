import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class Liga {
    public static void startNewLiga(Scanner sc, ArrayList<Team> teams) {
        System.out.println("How many teams are registered to participate: ");
        int NumberOfTeams = sc.nextInt();
        sc.nextLine();
        Set<Team> teamLiga = new HashSet<>();
        for (int i = 0; i < NumberOfTeams; i++) {
            Team t = Team.searchTeamInTeamList(sc, teams);
            if (t == null) {
                System.out.println("Team not found! Try again.");
                i--;
                continue;
            }
            if (teamLiga.contains(t)) {
                System.out.println("Team already added! Choose another.");
                i--;
                continue;
            }
            teamLiga.add(t);
            System.out.println("Added: " + t.getTeamName());
        }
        ArrayList<Team> teamList = new ArrayList<>(teamLiga);
        ArrayList<Match> matches = new ArrayList<>();
        for (int i = 0; i < teamList.size(); i++) {
            for (int j = i + 1; j < teamList.size(); j++) {
                Match m = new Match(teamList.get(i), teamList.get(j));
                matches.add(m);
            }
        }
        for (Match m : matches) {
            m.playMatch();
            FileManager.saveMatchToFile("src/resource/dato_partido", m);
        }

        System.out.println("🏁 Liga finished! All matches saved.");

    }
}
