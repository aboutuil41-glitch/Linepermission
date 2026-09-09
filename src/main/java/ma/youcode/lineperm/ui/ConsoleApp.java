package ma.youcode.lineperm.ui;
import ma.youcode.lineperm.service.UserService;
import ma.youcode.lineperm.models.User;
import java.util.*;
public class ConsoleApp {

    UserService service = new UserService();
    Scanner scanner = new Scanner(System.in);
    User user = null;

    public void startConsole(){

        System.out.println("==============================================");
        System.out.println("         Welcome To LinePermission!!          ");
        System.out.println("==============================================");
        System.out.println("\nWhat You Gonna Do?? | Login | Sign Up | Exit");


    while(true){
        System.out.print("lineperm> ");
        String option = scanner.nextLine();
        switch (option) {
            case "login":
                if(user != null){
                    System.err.println("Already Logged In.");
                    break;
                }
                System.out.print("Login: ");
                String Login = scanner.nextLine();
                System.out.print("Password: ");
                String Password = scanner.nextLine();
                user = service.login(Login, Password);
                if(user == null){
                    System.out.println("Wrong Password");
                }
                if(user != null){
                    System.out.println("Welcome " + user.getName() + " To LinePerm");
                    LoggedIn(user);
                }
                break;
            case "signup":
                if(user != null){
                    System.err.println("Already Logged In.");
                    break;
                }
                System.out.print("Enter Login Name: ");
                String Name = scanner.nextLine();
                System.out.print("Enter Your Password: ");
                String NewPassword = scanner.nextLine();
                service.createUser(Name, NewPassword);
                break;
            case "exit":
                System.exit(0);
                break;
            case "logout":
                if(user == null){
                    System.out.println("You Don't Even Logged In.");
                }
                user = null;
            break;
        
            default:
                System.out.println("Invalid Option.");
                break;
        }


        }
    }

    public void LoggedIn(User user){
        while (true) {
            String name = user.getName();
            System.out.print(name +"@lineperma>" );
            String choice = scanner.nextLine();

            switch (choice.toLowerCase().trim()) {
                case "login":
                if(user != null){
                    System.err.println("Already Logged In.");
                    break;
                }
                case "signup":
                if(user != null){
                    System.err.println("Already Logged In.");
                    break;
                }
                case "help":
                    System.out.println("Not available");
                    break;
                case "logout":
                        System.out.println("LoggingOut");
                        return;
                default:
                    System.out.println("Doesn't Exist Type 'help' for help");
                    break;
            }
        }
    }
}
