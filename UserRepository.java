package main;

import java.io.*;
import java.util.ArrayList;

public class UserRepository implements Repository<User> {

    @Override
    public ArrayList<User> find(String column, String[] conditions, boolean joinTable, String joinTableName, Connection connection) {
        try {
            BufferedReader reader = connection.openFile();
            ArrayList<User> userList = new ArrayList<>();

            String line;
            while ((line = reader.readLine()) != null) {
                String[] userData = line.split(",");
                User user = createUserFromCSV(userData);

                // Filtering logic
                if (matchesConditions(user, column, conditions)) {
                    userList.add(user);
                }
            }

            connection.closeFile(reader);
            return userList;
        } catch (IOException e) {
            handleIOException("Error while reading data", e);
        }
        return null;
    }

    @Override
    public User findOne(String column, String[] conditions, boolean joinTable, String joinTableName, Connection connection) {
        // Similar logic to find, return only one user or null
        try {
            BufferedReader reader = connection.openFile();

            String line;
            while ((line = reader.readLine()) != null) {
                String[] userData = line.split(",");
                User user = createUserFromCSV(userData);

                // Filtering logic for findOne
                if (matchesConditions(user, column, conditions)) {
                    connection.closeFile(reader);
                    return user;
                }
            }

            connection.closeFile(reader);
        } catch (IOException e) {
            handleIOException("Error while reading data", e);
        }
        return null;
    }

    @Override
    public User insert(String[] data, Connection connection) {
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

    private User createUserFromCSV(String[] userData) {
        User user = new User();
        user.setNim(userData[0]);
        user.setName(userData[1]);
        user.setTeamName(userData[2]);
        return user;
    }

    private boolean matchesConditions(User user, String column, String[] conditions) {
        // Check if the user matches the specified conditions
        if (column != null && conditions != null) {
            for (int i = 0; i < conditions.length; i += 2) {
                String filterColumn = conditions[i];
                String filterValue = conditions[i + 1];

                switch (filterColumn) {
                    case "name":
                        if (!user.getName().equals(filterValue)) {
                            return false;
                        }
                        break;
                    case "nim":
                        if (!user.getNim().equals(filterValue)) {
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
