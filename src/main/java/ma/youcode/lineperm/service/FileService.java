package ma.youcode.lineperm.service;
import java.io.FileWriter;
import java.io.IOException;
public class FileService {
            public void saveFile(FileRecord files) {
        try (FileWriter writer = new FileWriter("C:\\Users\\pc\\Documents\\java-bootcamp\\LinePermission\\src\\main\\resources\\files.txt", true)) {
            writer.write(files.getName() + "," + files.getBelongsTo() + "," + files.getShare());
            writer.write(System.lineSeparator());
            System.out.println("Successfully written to the file.");
        } catch (IOException e) {
            System.out.println("An error occurred while writing to the file.");
            e.printStackTrace();
        }
    }

    public Map<String, FileRecord> loadFiles() {
        Map<String, FileRecord> filesMap = new HashMap<>();
        // Specify the path to your file
        String filePath = "C:\\Users\\pc\\Documents\\java-bootcamp\\LinePermission\\src\\main\\resources\\files.txt";

        try (FileReader fr = new FileReader(filePath);
             BufferedReader br = new BufferedReader(fr)) {

            String line;
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
}
