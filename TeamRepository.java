package main;

import java.io.*;
import java.util.ArrayList;

public class TeamRepository implements Repository<Team> {

    @Override
    public ArrayList<Team> find(String column, String[] conditions, boolean joinTable, String joinTableName, Connection connection) {
        try {
            BufferedReader reader = connection.openFile();
            ArrayList<Team> teamList = new ArrayList<>();

            String line;
            while ((line = reader.readLine()) != null) {
                String[] teamData = line.split(",");
                Team team = createTeamFromCSV(teamData);

                // Filtering logic
                if (matchesConditions(team, column, conditions)) {
                    teamList.add(team);
                }
            }

            connection.closeFile(reader);
            return teamList;
        } catch (IOException e) {
            handleIOException("Error while reading data", e);
        }
        return null;
    }

    @Override
    public Team findOne(String column, String[] conditions, boolean joinTable, String joinTableName, Connection connection) {
        // Similar logic to find, return only one team or null
        try {
            BufferedReader reader = connection.openFile();

            String line;
            while ((line = reader.readLine()) != null) {
                String[] teamData = line.split(",");
                Team team = createTeamFromCSV(teamData);

                // Filtering logic for findOne
                if (matchesConditions(team, column, conditions)) {
                    connection.closeFile(reader);
                    return team;
                }
            }

            connection.closeFile(reader);
        } catch (IOException e) {
            handleIOException("Error while reading data", e);
        }
        return null;
    }

    @Override
    public Team insert(String[] data, Connection connection) {
        try {
            BufferedWriter writer = connection.writeFile();

            // Write data to file
            writer.write(String.join(",", data));
            writer.newLine();

            connection.closeFile(writer);
        } catch (IOException e) {
            handleIOException("Error while inserting data", e);
        }
        return null;
    }

    private Team createTeamFromCSV(String[] teamData) {
        Team team = new Team();
        team.setID(Integer.parseInt(teamData[0]));
        team.setTeamName(teamData[1]);
        return team;
    }

    private boolean matchesConditions(Team team, String column, String[] conditions) {
        // Check if the team matches the specified conditions
        if (column != null && conditions != null) {
            for (int i = 0; i < conditions.length; i += 2) {
                String filterColumn = conditions[i];
                String filterValue = conditions[i + 1];

                switch (filterColumn) {
                    case "id":
                        if (team.getID() != Integer.parseInt(filterValue)) {
                            return false;
                        }
                        break;
                    case "name":
                        if (!team.getTeamName().equals(filterValue)) {
                            return false;
                        }
                        break;
                    // Add more columns as needed
                }
            }
        }
        return true;
    }

    private void handleIOException(String message, IOException e) {
        System.err.println(message + ": " + e.getMessage());
    }
}
