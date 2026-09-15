package ma.youcode.lineperm.service;
public class LogsService {
    List<Logs> logs = new ArrayList<>();


    private static final String LOG_FILE = "C:\\Users\\pc\\Documents\\java-bootcamp\\LinePermission\\src\\main\\resources\\access.log";

    public void saveAllLogs() {
        try (FileWriter writer = new FileWriter(LOG_FILE, false)) { // false = overwrite, not append (same style as FileService.saveAllFiles())
            for (Logs l : logs) {
                String time = l.getTime().format(DateTimeFormatter.ofPattern("HH:mm"));
                writer.write(l.getDate() + ";" + time + ";" + l.getUser() + ";" + l.getAction() + ";" + l.getFile() + ".txt;" + l.getResult());
                writer.write(System.lineSeparator());
            }
            System.out.println("Logs saved.");
        } catch (IOException e) {
            System.out.println("An error occurred while writing to the file.");
            e.printStackTrace();
        }
    }

}