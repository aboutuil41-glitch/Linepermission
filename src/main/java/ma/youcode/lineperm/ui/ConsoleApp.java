package ma.youcode.lineperm.ui;
import ma.youcode.lineperm.service.UserService;
import ma.youcode.lineperm.service.FileService;
import ma.youcode.lineperm.service.LogsAnalyzer;
import ma.youcode.lineperm.service.LogsService;
import ma.youcode.lineperm.models.User;
import ma.youcode.lineperm.models.FileRecord;
import ma.youcode.lineperm.models.Logs;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

public class ConsoleApp {

    UserService service = new UserService();
    LogsService logsService = new LogsService();
    FileService fileService = new FileService(logsService);
    Scanner scanner = new Scanner(System.in);
    User user = null;
    LogsAnalyzer analyzer;


    public void startConsole(){
        System.out.println("==============================================");
        System.out.println("         Welcome To LinePermission!!          ");
        System.out.println("==============================================");
        System.out.println("\nWhat You Gonna Do?? | Login | Sign Up | Exit");

        service.loadUser();
        fileService.loadFiles();
        logsService.loadLogs();

        while(true){
            System.out.print(prompt());
            String line = scanner.nextLine();
            process(line);
        }
    }

    private String prompt(){
        return user == null ? "lineperm> " : user.getName() + "@lineperma>";
    }

    private void process(String line){
        line = line.trim();
        if (line.isEmpty()) return;

        String[] words = line.split("\\s+");
        String command = words[0].toLowerCase();

        if (user == null && needsLogin(command)) {
            System.out.println("You must be logged in...");
            return;
        }

        if (user != null && (command.equals("login") || command.equals("signup"))) {
            System.out.println("You're already logged in...");
            return;
        }
        if (user != null && (command.equals("stats"))) {
            System.out.println("Access Denied.");
            return;
        }

        switch (command) {
            case "login":
                login();
                break;
            case "signup":
                signup();
                break;
            case "exit":
                System.exit(0);
                break;
            case "logout":
                logout();
                break;
            case "ls":
                listFiles();
                break;
            case "nano":
                nano(words[1]);
                break;
            case "cat":
                cat(words[1]);
                break;
            case "touch":
                touch(words[1]);
                break;
            case "chmod":
                chmod(words[2], words[1]);
            break;
            case "help":
                System.out.println("Commands: nano <file>.txt | ls | cat <file>.txt | logout | stats | help");
                break;
            case "stats":
                showStatsMenu();
                break;
            default:
                System.out.println("Doesn't Exist Type 'help' for help");
                break;
        }
    }

    private boolean needsLogin(String command){
        return !(command.equals("login") || command.equals("signup") || command.equals("exit") || command.equals("stats"));
    }

    private void login(){
        System.out.print("Login: ");
        String Login = scanner.nextLine();
        if(Login == ""){
            System.out.println("empty.");
            return;
        }
        System.out.print("Password: ");
        String Password = scanner.nextLine();
        user = service.login(Login, Password);
        if(user == null){
            System.out.println("Wrong Password");
        } else {
            System.out.println("Welcome " + user.getName() + " To LinePerm");
        }
    }

    private void signup(){
        System.out.print("Enter Login Name: ");
        String Name = scanner.nextLine();
        if(Name == ""){
            System.out.println("empty.");
            return;
        }
        System.out.print("Enter Your Password: ");
        String NewPassword = scanner.nextLine();
        if(Name == ""){
            System.out.println("empty.");
            return;
        }
        service.createUser(Name, NewPassword);
    }

    private void logout(){
        System.out.println("LoggingOut");
        user = null;
    }

    private void listFiles(){
        Map<String, FileRecord> allFiles = fileService.getAll();
        for (FileRecord f : allFiles.values()) {
            String[] share = f.getShare();
            System.out.println("rwd|" + share[0] + share[1] + share[2] + " " + f.getName() + " Owner : " + f.getBelongsTo());
        }
    }

    private void nano(String fileName){
        fileName = fileName.replace(".txt", "").trim();
        if(fileService.CheckW(fileName, user))
        {List<String> lines = new ArrayList<>();
        while (true) {
            String inputLine = scanner.nextLine();
            if (inputLine.equals("EOF")) break;
            lines.add(inputLine);
        }
        fileService.writeToFile(fileName, lines);
        return ;
    }
        System.out.println("No Permission Granted");
    }

    private void cat(String fileName){
        fileName = fileName.replace(".txt", "").trim();
        if(fileService.CheckR(fileName, user)){
            fileService.readOutFile(fileName);
            return ;
        }
        System.out.println("No Permission Granted");
    }

    private void touch(String fileName){
        fileName = fileName.replace(".txt", "").trim();
        fileService.createFile(fileName, user);
    }

    private void chmod(String filename, String character){
        filename = filename.replace(".txt", "").trim();
        fileService.setPermission(filename, character, user);
    }


    private void showStatsMenu(){
        analyzer = new LogsAnalyzer(logsService.AllLogs());
    while(true){
        System.out.println("=== LogAnalyzer ===");
        System.out.println("1) Total number of actions");
        System.out.println("2) Number of refused accesses");
        System.out.println("3) Distinct users");
        System.out.println("4) Actions per user");
        System.out.println("5) Top 3 accessed files");
        System.out.println("6) Refused accesses for a user");
        System.out.println("7) Most active user");
        System.out.println("8) Action breakdown by type");
        System.out.println("0) Quit");
        System.out.print("Choice: ");
        String choice = scanner.nextLine().trim();

        switch(choice){
            case "1":
                System.out.println("Total: " + analyzer.totalAction());
                break;
            case "2":
                System.out.println("Refused accesses: " + analyzer.totalRefuse());
                break;
            case "3":
                System.out.println("Distinct users: " + analyzer.distinct());
                break;
            case "4":
                System.out.println("Actions per user: " + analyzer.userAction());
                break;
            case "5":
                System.out.println("Top 3 files: " + analyzer.Top3Files());
                break;
            case "6":
                System.out.print("Username: ");
                String name = scanner.nextLine().trim();
                System.out.println("Refused accesses for " + name + ": " + analyzer.RefusedByUser(name));
                break;
            case "7":
                System.out.println("Most active user: " + analyzer.MostActive());
                break;
            case "8":
                System.out.println("Breakdown: " + analyzer.ActionCount());
                break;
            case "0":
                return;
            default:
                System.out.println("Invalid choice");
        }
    }
}



}