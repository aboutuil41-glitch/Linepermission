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
}
