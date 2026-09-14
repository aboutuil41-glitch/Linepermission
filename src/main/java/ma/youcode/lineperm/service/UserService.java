package ma.youcode.lineperm.service;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import ma.youcode.lineperm.models.User;
import org.mindrot.jbcrypt.BCrypt;


public class UserService {
        Map<String, User> users = new HashMap<>();

        public void saveUser(User user) {
        try (FileWriter writer = new FileWriter("C:\\Users\\pc\\Documents\\java-bootcamp\\LinePermission\\src\\main\\resources\\user.txt", true)) {
            writer.write(user.getName() + "," + user.getPassword());
            writer.write(System.lineSeparator());
            System.out.println("Successfully written to the file.");
        } catch (IOException e) {
            System.out.println("An error occurred while writing to the file.");
            e.printStackTrace();
        }
    }

    public void loadUser() {
        // Specify the path to your file
        String filePath = "C:\\Users\\pc\\Documents\\java-bootcamp\\LinePermission\\src\\main\\resources\\user.txt";

        // Try-with-resources automatically closes the resources
        try (FileReader fr = new FileReader(filePath);
             BufferedReader br = new BufferedReader(fr)) {

            String line;
            // Read the file line by line until the end (null)
            while ((line = br.readLine()) != null) {
                String[] splits = line.split(",");
                User user = new User().setName(splits[0]).setPassword(splits[1]);
                users.put(user.getName(), user);
            }

        } catch (IOException e) {
            System.err.println("An error occurred while reading the file: " + e.getMessage());
        }
    }


    public void createUser(String Name, String Password){
        
        String Hash = BCrypt.hashpw(Password, BCrypt.gensalt());
        User user = new User().setName(Name).setPassword(Hash);
        User Check = findByName(Name);
        if (Check == null) {
            saveUser(user);
            return;
        }
        System.out.println("User Already exists!");
    }


    public User findByName(String Name){
        return users.get(Name.trim());
    }

    public boolean checkPassword(String Password, String OgPassword){
        return BCrypt.checkpw(Password, OgPassword);
    }

    public User login(String Name, String Password){
        User user = findByName(Name);
        System.out.println(user.getName());
        if(user == null){
            System.out.println("User Doesn't Exist!!");
            return null;
        }
        boolean checkPassword = checkPassword(Password, user.getPassword());

        if (checkPassword) {
            return user;
        }
        return null;
    }
}