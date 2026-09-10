package ma.youcode.lineperm.ui;
import ma.youcode.lineperm.service.UserService;
import ma.youcode.lineperm.service.FileService;
import ma.youcode.lineperm.models.User;
import ma.youcode.lineperm.models.FileRecord;
import java.util.*;

public class ConsoleApp {

    UserService service = new UserService();
    FileService fileService = new FileService();
    Scanner scanner = new Scanner(System.in);
    User user = null;

    public void startConsole(){
        System.out.println("==============================================");
        System.out.println("         Welcome To LinePermission!!          ");
        System.out.println("==============================================");
        System.out.println("\nWhat You Gonna Do?? | Login | Sign Up | Exit");

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
                nano(words[1], user);
                break;
            case "cat":
                cat(words[1]);
                break;
            case "help":
                System.out.println("Commands: nano <file>.txt | ls | cat <file>.txt | logout | help");
                break;
            default:
                System.out.println("Doesn't Exist Type 'help' for help");
                break;
        }
    }

    private boolean needsLogin(String command){
        return !(command.equals("login") || command.equals("signup") || command.equals("exit"));
    }

    private void login(){
        System.out.print("Login: ");
        String Login = scanner.nextLine();
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
        System.out.print("Enter Your Password: ");
        String NewPassword = scanner.nextLine();
        service.createUser(Name, NewPassword);
    }

    private void logout(){
        System.out.println("LoggingOut");
        user = null;
    }

    private void listFiles(){
        Map<String, FileRecord> allFiles = fileService.loadFiles();
        for (FileRecord f : allFiles.values()) {
            System.out.println("rwd|" + f.getShare() + " " + f.getName() + " Owner : " + f.getBelongsTo());
        }
    }

    private void nano(String fileName, User user){
        fileName = fileName.replace(".txt", "").trim();
        fileService.createFile(fileName, user);

        List<String> lines = new ArrayList<>();
        while (true) {
            String inputLine = scanner.nextLine();
            if (inputLine.equals("EOF")) break;
            lines.add(inputLine);
        }
        fileService.writeToFile(fileName, lines);
    }

    private void cat(String fileName){
        fileName = fileName.replace(".txt", "").trim();
        fileService.readOutFile(fileName);
    }

}