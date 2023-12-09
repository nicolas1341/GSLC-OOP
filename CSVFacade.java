package main;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;

import java.io.InputStreamReader;

import java.util.ArrayList;

public class CSVFacade {
    private Connection connection;

    public CSVFacade(Connection connection) {
        this.connection = connection;
    }

    public void insertData(String[] data, String type, UserRepository userRepository, TeamRepository teamRepository) {
    	if (type.equalsIgnoreCase("user")) {
		    userRepository.insert(data, connection);
		} else if (type.equalsIgnoreCase("team")) {
		    teamRepository.insert(data, connection);
		} else {
		    System.out.println("Invalid type specified for data insertion.");
		}
    }

    public void showData(String table, String[] conditions, boolean joinTable, String joinTableName,
                         UserRepository userRepository, TeamRepository teamRepository) {
        if (table.equalsIgnoreCase("user")) {
            ArrayList<User> userList = userRepository.find(null, conditions, joinTable, joinTableName, connection);
            displayUserList(userList);
        } else if (table.equalsIgnoreCase("team")) {
            ArrayList<Team> teamList = teamRepository.find(null, conditions, joinTable, joinTableName, connection);
            displayTeamList(teamList);
        } else {
            System.out.println("Invalid table specified for data retrieval.");
        }
    }

    private void displayUserList(ArrayList<User> userList) {
        // Implement logic to display user data
        if (userList != null && !userList.isEmpty()) {
            for (User user : userList) {
                System.out.println("NIM: " + user.getNim() + ", Name: " + user.getName() + ", Team: " + user.getTeamName());
            }
        } else {
            System.out.println("No user data found.");
        }
    }

    private void displayTeamList(ArrayList<Team> teamList) {
        // Implement logic to display team data
        if (teamList != null && !teamList.isEmpty()) {
            for (Team team : teamList) {
                System.out.println("ID: " + team.getID() + ", Team Name: " + team.getTeamName());
            }
        } else {
            System.out.println("No team data found.");
        }
    }

    private void handleIOException(String message, IOException e) {
        System.err.println(message + ": " + e.getMessage());
    }
}
