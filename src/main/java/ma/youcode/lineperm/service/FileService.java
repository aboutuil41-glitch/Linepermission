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
        Map<String, FileRecord> filesMap = new HashMap<>();


    public void saveAllFiles() {
        try (FileWriter writer = new FileWriter(
                "C:\\Users\\pc\\Documents\\java-bootcamp\\LinePermission\\src\\main\\resources\\files.txt", false)) { // false = overwrite, not append
            for (FileRecord files : filesMap.values()) {
                String[] share = files.getShare();
                writer.write(files.getName() + "," + files.getBelongsTo() + "," + share[0] + share[1] + share[2]);
                writer.write(System.lineSeparator());
            }
            System.out.println("Files saved.");
        } catch (IOException e) {
            System.out.println("An error occurred while writing to the file.");
            e.printStackTrace();
        }
    }

public void loadFiles() {
    String filePath = "C:\\Users\\pc\\Documents\\java-bootcamp\\LinePermission\\src\\main\\resources\\files.txt";

    try (FileReader fr = new FileReader(filePath);
         BufferedReader br = new BufferedReader(fr)) {

        String line;
        while ((line = br.readLine()) != null) {
            String[] splits = line.split(",");
            FileRecord files = new FileRecord()
                    .setName(splits[0])
                    .setBelongsTo(splits[1]);

            if (splits.length > 2) {
                String shareStr = splits[2].trim();
                String[] share = new String[3];
                for (int i = 0; i < 3; i++) {
                    share[i] = String.valueOf(shareStr.charAt(i));
                }
                files.setShare(share);
            }

            filesMap.put(splits[0].trim(), files);
        }

    } catch (IOException e) {
        System.err.println("An error occurred while reading the file: " + e.getMessage());
    }
}

    public Map<String, FileRecord> getAll(){
        return filesMap;
    }

    public FileRecord findFile(String Name){
        return filesMap.get(Name.trim());
    }

    public void createFile(String name, User user){
        FileRecord file = new FileRecord().setName(name).setBelongsTo(user.getName());
        Path path = Paths.get("C:\\Users\\pc\\Documents\\java-bootcamp\\LinePermission\\src\\main\\resources\\FilesStorage\\" + name + ".txt");
        try {
            Path newFile = Files.createFile(path);
            System.out.println("File created successfully at: " + newFile.toAbsolutePath());
            filesMap.put(name.trim(), file); 
            saveAllFiles();
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

    // public void shareFile(FileRecord file, String character){
    //   String[] current = file.getShare().split("|");

    // }


    public boolean CheckR(String fileName, User user){
        FileRecord file = findFile(fileName);
        String[] share = file.getShare();
        if(file.getBelongsTo().equals(user.getName())){
            return true;
        }
        else if(share[0].equals("r")){
            return true;
        }
        else{
            return false;
        }
    }
    public boolean CheckW(String fileName, User user){
        FileRecord file = findFile(fileName);
        String[] share = file.getShare();
        if(file.getBelongsTo().equals(user.getName())){
            return true;
        }
        else if(share[1].equals("w")){
            return true;
        }
        else{
            return false;
        }
    }

    public void setPermission(String filename, String character, User user){
        FileRecord file = findFile(filename);
        if(file.getBelongsTo().equals(user.getName())){
        String[] share = file.getShare();
        if(character.equals("r")){
            share[0] = "r";
            saveAllFiles();
            return ;
        }
        if(character.equals("w")){
            share[1] = "w";
            saveAllFiles();
            return ;
        }
        if(character.equals("-r")){
            share[0] = "-";
            saveAllFiles();
            return ;
        }
        if(character.equals("-w")){
            share[1] = "-";
            saveAllFiles();
            return ;
        }
        System.out.println("Character Not Found");
        return ;
        }
        System.out.println("Not Owner.");
        return ;
    }
    }



