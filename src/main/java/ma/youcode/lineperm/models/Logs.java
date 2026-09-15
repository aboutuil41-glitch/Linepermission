package ma.youcode.lineperm.models;

import java.time.*;

import ma.youcode.lineperm.models.Logs.LogsType;

public class Logs {
    LocalDate date;
    LocalTime time;
    String user;
    String file;
    private LogsType action;
    private Status result;   

    public enum Status {
    OK,
    REFUSE, 
    }

    public enum LogsType {
    READ,
    WRITE,
    DELETE
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalTime getTime() {
        return time;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public String getFile() {
        return file;
    }

    public LogsType getAction() {
        return action;
    }

    public void setAction(LogsType action) {
        this.action = action;
    }

    public Status getResult() {
        return result;
    }

    public void setResult(Status result) {
        this.result = result;
    }



}