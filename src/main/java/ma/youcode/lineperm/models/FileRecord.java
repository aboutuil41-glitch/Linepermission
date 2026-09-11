package ma.youcode.lineperm.models;

import java.nio.file.Files;

public class FileRecord {
    String name;
    String belongsTo;
    String[] Share = {"-", "-", "-"};

    public FileRecord setBelongsTo(String belongsTo) {
        this.belongsTo = belongsTo;
        return this;
    }

    public FileRecord setName(String name) {
        this.name = name;
        return this;
    }

    public void setShare(String[] share) {
        Share = share;
    }

    public String getName() {
        return name;
    }

    public String getBelongsTo() {
        return belongsTo;
    }

    public String getShare() {
        return Share;
    }
}
