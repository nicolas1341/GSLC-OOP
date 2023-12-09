package main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {

    public static void main(String[] args) {
        try {
            String file = System.getProperty("user.dir").replace("\\", "\\\\").concat("\\\\JavaProjectHackathon\\\\src\\\\user.csv");
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            CSVFacade csvFacade = new CSVFacade(Connection.getInstance(file));
            UserRepository userRepository = new UserRepository();
            TeamRepository teamRepository = new TeamRepository();

            while (true) {
                printMainMenu();
                int choice = Integer.parseInt(reader.readLine());

                switch (choice) {
                    case 1:
                        handleInsertMenu(reader, csvFacade, userRepository, teamRepository);
                        break;
                    case 2:
                        handleShowMenu(reader, csvFacade, userRepository, teamRepository);
                        break;
                    case 3:
                        System.out.println("Exiting program. Goodbye!");
                        System.exit(0);
                        break;
                    default:
                        System.out.println("Invalid choice. Please enter a valid option.");
                }
            }
        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
        }
    }

    private static void printMainMenu() {
        System.out.println("\n=== Main Menu ===");
        System.out.println("1. Insert Data");
        System.out.println("2. Show Data");
        System.out.println("3. Exit");
        System.out.print("Enter your choice: ");
    }

    private static void handleInsertMenu(BufferedReader reader, CSVFacade csvFacade,
                                         UserRepository userRepository, TeamRepository teamRepository) throws IOException {
        System.out.println("\n=== Insert Data Menu ===");
        System.out.println("1. Insert User");
        System.out.println("2. Insert Team");
        System.out.print("Enter your choice: ");
        int choice = Integer.parseInt(reader.readLine());

        switch (choice) {
            case 1:
                handleUserInsert(reader, csvFacade, userRepository, teamRepository);
                break;
            case 2:
                handleTeamInsert(reader, csvFacade, teamRepository);
                break;
            default:
                System.out.println("Invalid choice. Returning to main menu.");
        }
    }

    private static void handleUserInsert(BufferedReader reader, CSVFacade csvFacade,UserRepository userRepository, TeamRepository teamRepository) throws IOException {
    	System.out.print("Enter User Name: ");
    	String name = reader.readLine();
    	System.out.print("Enter User NIM: ");
    	String nim = reader.readLine();
    	System.out.print("Enter User Team Name: ");
    	String teamName = reader.readLine();

    	String[] userData = {nim, name, teamName};
    	csvFacade.insertData(userData, "user", userRepository, teamRepository);
}

    private static void handleTeamInsert(BufferedReader reader, CSVFacade csvFacade,TeamRepository teamRepository) throws IOException {
    	System.out.print("Enter Team Name: ");
        String teamName = reader.readLine();

        String[] teamData = {"0", teamName}; // Assuming ID is auto-incremented
        csvFacade.insertData(teamData, "team", null, teamRepository);
    }

    private static void handleShowMenu(BufferedReader reader, CSVFacade csvFacade,
                                       UserRepository userRepository, TeamRepository teamRepository) throws IOException {
        System.out.println("\n=== Show Data Menu ===");
        System.out.println("1. Show User Data");
        System.out.println("2. Show Team Data");
        System.out.print("Enter your choice: ");
        int choice = Integer.parseInt(reader.readLine());

        switch (choice) {
            case 1:
                handleUserShow(reader, csvFacade, userRepository, teamRepository);
                break;
            case 2:
                handleTeamShow(reader, csvFacade, teamRepository);
                break;
            default:
                System.out.println("Invalid choice. Returning to main menu.");
        }
    }

    private static void handleUserShow(BufferedReader reader, CSVFacade csvFacade,UserRepository userRepository, TeamRepository teamRepository) throws IOException {
    	System.out.print("Enter conditions (separate with semicolon and press Enter): ");
        String conditionsInput = reader.readLine();
        String[] conditions = conditionsInput.isEmpty() ? null : conditionsInput.split(";");

        csvFacade.showData("user", conditions, false, null, userRepository, teamRepository);
    }

    private static void handleTeamShow(BufferedReader reader, CSVFacade csvFacade,TeamRepository teamRepository) throws IOException {
    	System.out.print("Enter conditions (separate with semicolon and press Enter): ");
        String conditionsInput = reader.readLine();
        String[] conditions = conditionsInput.isEmpty() ? null : conditionsInput.split(";");

        csvFacade.showData("team", conditions, false, null, null, teamRepository);
    }
}
