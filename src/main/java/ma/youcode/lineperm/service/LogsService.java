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

    public void loadLogs() {
        logs.clear();

        try (FileReader fr = new FileReader(LOG_FILE);
            BufferedReader br = new BufferedReader(fr)) {

            String line;
            while ((line = br.readLine()) != null) {
                String[] splits = line.split(";");

                Logs log = new Logs();
                log.setDate(LocalDate.parse(splits[0]));
                log.setTime(LocalTime.parse(splits[1]));
                log.setUser(splits[2]);
                log.setAction(Logs.LogsType.valueOf(splits[3].toUpperCase()));
                splits[4].replace(".txt", "").trim();
                log.setFile(splits[4].replace(".txt", "").trim());
                log.setResult(Logs.Status.valueOf(splits[5].toUpperCase()));
    
                logs.add(log);

            }

    } catch (IOException e) {
        System.err.println("An error occurred while reading the file: " + e.getMessage());
    }
}


    public void logAction(User currentUser, Logs.LogsType action, String filename, Logs.Status status){
        loadLogs();

        Logs log = new Logs();
        log.setDate(LocalDate.now());
        log.setTime(LocalTime.now());
        log.setUser(currentUser.getName());
        log.setAction(action);
        log.setFile(filename);
        log.setResult(status);

        logs.add(log);
        saveAllLogs();
    }

    

}