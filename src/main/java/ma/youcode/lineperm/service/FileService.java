package ma.youcode.lineperm.service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ma.youcode.lineperm.models.FileRecord;
import ma.youcode.lineperm.models.User;

public class FileService {
            public void saveFile(FileRecord files) {
        try (FileWriter writer = new FileWriter("C:\\Users\\pc\\Documents\\java-bootcamp\\LinePermission\\src\\main\\resources\\files.txt", true)) {
            writer.write(files.getName() + "," + files.getBelongsTo() + "," + files.getShare());
            writer.write(System.lineSeparator());
            System.out.println("Successfully written to the file.");
        Map<String, FileRecord> filesMap = new HashMap<>();

        } catch (IOException e) {
            System.out.println("An error occurred while writing to the file.");
            e.printStackTrace();
        }
    }

    public Map<String, FileRecord> loadFiles() {
        Map<String, FileRecord> filesMap = new HashMap<>();
        // Specify the path to your file
        String filePath = "C:\\Users\\pc\\Documents\\java-bootcamp\\LinePermission\\src\\main\\resources\\files.txt";

        // Try-with-resources automatically closes the resources
        try (FileReader fr = new FileReader(filePath);
             BufferedReader br = new BufferedReader(fr)) {

            String line;
            // Read the file line by line until the end (null)
            while ((line = br.readLine()) != null) {
                String[] splits = line.split(",");
                FileRecord files = new FileRecord().setName(splits[0]).setBelongsTo(splits[1]);
                filesMap.put(splits[0].trim().toLowerCase(), files);
            }

        } catch (IOException e) {
            System.err.println("An error occurred while reading the file: " + e.getMessage());
        }
        return filesMap;
    }

    public FileRecord findFile(String Name){
        Map<String, FileRecord> files = loadFiles();
        return files.get(Name.trim().toLowerCase());
    }

    public void createFile(String name, User user){
        FileRecord file = new FileRecord().setName(name).setBelongsTo(user.getName());
        Path path = Paths.get("C:\\Users\\pc\\Documents\\java-bootcamp\\LinePermission\\src\\main\\resources\\FilesStorage\\" + name + ".txt");
        try {
            Path newFile = Files.createFile(path);
            System.out.println("File created successfully at: " + newFile.toAbsolutePath());
            saveFile(file);
        } catch (IOException e) {
            System.err.println("Failed to create file: " + e.getMessage());
        }

    }

    public void writeToFile(String fileName, List<String> lines){
        Path path = Paths.get("C:\\Users\\pc\\Documents\\java-bootcamp\\LinePermission\\src\\main\\resources\\FilesStorage\\" + fileName + ".txt");
        try(FileWriter writer = new FileWriter(path.toFile())) {
            for(String line: lines){
                writer.write(line);
                writer.write(System.lineSeparator());
            }
            System.out.println("Content saved to " + fileName + ".txt");
        } catch (Exception e) {
            System.err.println("Failed to write please try again later :D.");
        }
    }

    public void readOutFile(String fileName){
                Path path = Paths.get("C:\\Users\\pc\\Documents\\java-bootcamp\\LinePermission\\src\\main\\resources\\FilesStorage\\" + fileName + ".txt");

        try {

            List<String> lines = Files.readAllLines(path);
            
            for (String line : lines) {
                System.out.println(line);
            }
        } catch (IOException e) {
            System.err.println("Error reading the file: " + e.getMessage());
        }
    }

    }



